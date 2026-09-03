package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.IdempotencyConflictException;
import cl.prgm.portaltrip.domain.exception.InsufficientBalanceException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationBalanceResult;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.ReservationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.ReservationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

	private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
	private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
	private static final UUID KEY = UUID.fromString("00000000-0000-0000-0000-000000000003");

	@Mock
	private ReservationJpaRepository reservationJpaRepository;
	@Mock
	private LocationJpaRepository locationJpaRepository;
	@Mock
	private CharacterJpaRepository characterJpaRepository;
	@Mock
	private UserJpaRepository userJpaRepository;

	private ReservationServiceImpl reservationService;

	@BeforeEach
	void setUp() {
		reservationService = new ReservationServiceImpl(
				reservationJpaRepository,
				locationJpaRepository,
				characterJpaRepository,
				userJpaRepository);
	}

	@Test
	void createDebitsBalanceAndPersistsConfirmedReservation() {
		stubCatalogAndSave();
		when(reservationJpaRepository.existsByNumber(anyString())).thenReturn(false);

		ReservationBalanceResult result = reservationService.create(USER_ID, KEY, draft());

		assertThat(result.reservation().id()).isNotNull();
		assertThat(result.reservation().userId()).isEqualTo(USER_ID);
		assertThat(result.reservation().idempotencyKey()).isEqualTo(KEY);
		assertThat(result.reservation().status()).isEqualTo(ReservationStatus.CONFIRMED);
		assertThat(result.reservation().email()).isEqualTo("rick@sanchez.dev");
		assertThat(result.reservation().quote().total()).isEqualByComparingTo("1416.00");
		assertThat(result.remainingBalance()).isEqualByComparingTo("3584.00");
	}

	@Test
	void createRetriesWhenNumberCollides() {
		stubCatalogAndSave();
		when(reservationJpaRepository.existsByNumber(anyString())).thenReturn(true, false);

		assertThat(reservationService.create(USER_ID, KEY, draft()).reservation().number())
				.matches("^PT-\\d{4}-\\d{6}$");
		verify(reservationJpaRepository, times(2)).existsByNumber(anyString());
	}

	@Test
	void createReturnsSameReservationForRepeatedEquivalentRequest() {
		ReservationEntity entity = entity(ReservationStatus.CONFIRMED);
		when(reservationJpaRepository.findByUserIdAndIdempotencyKey(USER_ID, KEY))
				.thenReturn(Optional.of(entity));
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("3584.00")));

		ReservationBalanceResult result = reservationService.create(USER_ID, KEY, draft());

		assertThat(result.reservation()).isEqualTo(entity.toDomain());
		assertThat(result.remainingBalance()).isEqualByComparingTo("3584.00");
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void createRejectsRepeatedKeyWithDifferentRequest() {
		ReservationEntity entity = entity(ReservationStatus.CONFIRMED);
		when(reservationJpaRepository.findByUserIdAndIdempotencyKey(USER_ID, KEY))
				.thenReturn(Optional.of(entity));
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("3584.00")));
		ReservationDraft different = new ReservationDraft(
				"Morty Smith", 1, LocalDate.now().plusDays(1), 2,
				List.of(2), TripType.EXPRESS, false, "");

		assertThatThrownBy(() -> reservationService.create(USER_ID, KEY, different))
				.isInstanceOf(IdempotencyConflictException.class);
	}

	@Test
	void createThrowsWhenDestinationNotFound() {
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("5000.00")));
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.create(USER_ID, KEY, draft()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Location with id '1' not found");
	}

	@Test
	void createThrowsWhenCompanionNotFound() {
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("5000.00")));
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.of(destinationEntity()));
		when(characterJpaRepository.findAllById(List.of(2))).thenReturn(List.of());

		assertThatThrownBy(() -> reservationService.create(USER_ID, KEY, draft()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Character with id '2' not found");
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void createRejectsInsufficientBalance() {
		stubCatalog();
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("100.00")));

		assertThatThrownBy(() -> reservationService.create(USER_ID, KEY, draft()))
				.isInstanceOf(InsufficientBalanceException.class);
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void createThrowsWhenUserIsMissing() {
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.create(USER_ID, KEY, draft()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("User with id '" + USER_ID + "' not found");
	}

	@Test
	void findAllMapsOwnedJpaEntities() {
		ReservationEntity entity = entity(ReservationStatus.CONFIRMED);
		when(reservationJpaRepository.findAllDetailedByUserId(USER_ID)).thenReturn(List.of(entity));

		assertThat(reservationService.findAll(USER_ID)).containsExactly(entity.toDomain());
	}

	@Test
	void findByIdReturnsOwnedReservation() {
		ReservationEntity entity = entity(ReservationStatus.CONFIRMED);
		when(reservationJpaRepository.findDetailedByIdAndUserId(ID, USER_ID)).thenReturn(Optional.of(entity));

		assertThat(reservationService.findById(USER_ID, ID)).isEqualTo(entity.toDomain());
	}

	@Test
	void findByIdThrowsWhenMissingOrOwnedBySomeoneElse() {
		when(reservationJpaRepository.findDetailedByIdAndUserId(ID, USER_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.findById(USER_ID, ID))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Reservation with id '" + ID + "' not found");
	}

	@Test
	void startMovesReservationToInProgress() {
		stubExistingAndSave(ReservationStatus.CONFIRMED);

		Reservation result = reservationService.start(USER_ID, ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.IN_PROGRESS);
		assertThat(result.startedAt()).isNotNull();
	}

	@Test
	void completeMovesReservationToCompleted() {
		stubExistingAndSave(ReservationStatus.IN_PROGRESS);

		Reservation result = reservationService.complete(USER_ID, ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.COMPLETED);
		assertThat(result.completedAt()).isNotNull();
	}

	@Test
	void cancelRefundsConfirmedReservation() {
		stubExisting(ReservationStatus.CONFIRMED);
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("3584.00")));
		stubSave();

		ReservationBalanceResult result = reservationService.cancel(USER_ID, ID);

		assertThat(result.reservation().status()).isEqualTo(ReservationStatus.CANCELLED);
		assertThat(result.remainingBalance()).isEqualByComparingTo("5000.00");
	}

	@Test
	void cancelThrowsWhenUserDisappeared() {
		stubExisting(ReservationStatus.CONFIRMED);
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.cancel(USER_ID, ID))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("User with id '" + USER_ID + "' not found");
	}

	@Test
	void lifecycleRejectsIllegalTransitionsBeforeWriting() {
		stubExisting(ReservationStatus.IN_PROGRESS);

		assertThatThrownBy(() -> reservationService.start(USER_ID, ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void startThrowsWhenUserDisappeared() {
		stubExisting(ReservationStatus.CONFIRMED);
		when(userJpaRepository.findById(USER_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.start(USER_ID, ID))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	private void stubCatalog() {
		LocationEntity destination = destinationEntity();
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.of(destination));
		when(characterJpaRepository.findAllById(List.of(2))).thenReturn(List.of(mortyEntity(destination)));
	}

	private void stubCatalogAndSave() {
		stubCatalog();
		when(userJpaRepository.findByIdForUpdate(USER_ID)).thenReturn(Optional.of(userEntity("5000.00")));
		stubSave();
	}

	private void stubExistingAndSave(ReservationStatus status) {
		stubExisting(status);
		when(userJpaRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity("3584.00")));
		stubSave();
	}

	private void stubExisting(ReservationStatus status) {
		when(reservationJpaRepository.findDetailedByIdAndUserId(ID, USER_ID))
				.thenReturn(Optional.of(entity(status)));
	}

	private void stubSave() {
		LocationEntity destination = destinationEntity();
		when(locationJpaRepository.getReferenceById(1)).thenReturn(destination);
		when(characterJpaRepository.findAllById(List.of(2))).thenReturn(List.of(mortyEntity(destination)));
		when(reservationJpaRepository.save(any(ReservationEntity.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
	}

	private static ReservationEntity entity(ReservationStatus status) {
		LocationEntity destination = destinationEntity();
		return ReservationEntity.fromDomain(
				reservation(status),
				userEntity("3584.00"),
				destination,
				Set.of(mortyEntity(destination)));
	}

	private static UserEntity userEntity(String balance) {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		return UserEntity.fromDomain(new UserAccount(
				USER_ID,
				"rick@sanchez.dev",
				"{bcrypt}hash",
				"Rick Sanchez",
				"ROLE_USER",
				new BigDecimal(balance),
				now,
				now));
	}

	private static LocationEntity destinationEntity() {
		LocationEntity destination = new LocationEntity(
				1, "Earth (C-137)", "Planet", "Dimension C-137");
		for (int id = 1; id <= 5; id++) {
			destination.getResidents().add(new CharacterEntity(
					id, "Resident " + id, "Alive", "Human", "", "Unknown",
					destination, destination, "img"));
		}
		return destination;
	}

	private static CharacterEntity mortyEntity(LocationEntity destination) {
		return new CharacterEntity(
				2, "Morty Smith", "Alive", "Human", "", "Male",
				destination, destination, "img");
	}

	private static ReservationDraft draft() {
		return new ReservationDraft(
				"Rick Sanchez", 1, LocalDate.now().plusDays(1),
				2, List.of(2), TripType.EXPRESS, false, "");
	}

	private static Reservation reservation(ReservationStatus status) {
		Quote quote = new Quote(
				new BigDecimal("1200"),
				BigDecimal.ZERO,
				new BigDecimal("216.00"),
				BigDecimal.ZERO,
				BigDecimal.ZERO,
				new BigDecimal("1416.00"),
				RiskLevel.LOW);
		return new Reservation(
				ID,
				USER_ID,
				KEY,
				"PT-2026-000001",
				status,
				"Rick Sanchez",
				"rick@sanchez.dev",
				1,
				LocalDate.now().plusDays(1),
				2,
				List.of(2),
				TripType.EXPRESS,
				false,
				"",
				quote,
				OffsetDateTime.now(),
				null,
				null);
	}

}
