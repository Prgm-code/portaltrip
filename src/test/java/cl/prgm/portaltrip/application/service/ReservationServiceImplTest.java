package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.ReservationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.ReservationJpaRepository;

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

	@Mock
	private ReservationJpaRepository reservationJpaRepository;

	@Mock
	private LocationJpaRepository locationJpaRepository;

	@Mock
	private CharacterJpaRepository characterJpaRepository;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@Test
	void createPersistsConfirmedReservation() {
		stubCatalogAndSave();
		when(reservationJpaRepository.existsByNumber(anyString())).thenReturn(false);

		Reservation result = reservationService.create(draft());

		assertThat(result.id()).isNotNull();
		assertThat(result.number()).matches("^PT-\\d{4}-\\d{6}$");
		assertThat(result.status()).isEqualTo(ReservationStatus.CONFIRMED);
		assertThat(result.destinationId()).isEqualTo(1);
		assertThat(result.companionIds()).containsExactly(2);
		assertThat(result.quote().total()).isEqualByComparingTo(new BigDecimal("1416.00"));
		assertThat(result.createdAt()).isNotNull();
		assertThat(result.startedAt()).isNull();
	}

	@Test
	void createRetriesWhenNumberCollides() {
		stubCatalogAndSave();
		when(reservationJpaRepository.existsByNumber(anyString())).thenReturn(true, false);

		Reservation result = reservationService.create(draft());

		assertThat(result.status()).isEqualTo(ReservationStatus.CONFIRMED);
		verify(reservationJpaRepository, times(2)).existsByNumber(anyString());
	}

	@Test
	void createThrowsWhenDestinationNotFound() {
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.create(draft()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Location with id '1' not found");
	}

	@Test
	void createThrowsWhenCompanionNotFound() {
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.of(destinationEntity()));
		when(characterJpaRepository.findAllById(List.of(2))).thenReturn(List.of());

		assertThatThrownBy(() -> reservationService.create(draft()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Character with id '2' not found");
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void findAllMapsJpaEntities() {
		ReservationEntity entity = entity(ReservationStatus.CONFIRMED);
		when(reservationJpaRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(entity));

		assertThat(reservationService.findAll()).containsExactly(entity.toDomain());
	}

	@Test
	void findByIdReturnsReservation() {
		ReservationEntity entity = entity(ReservationStatus.CONFIRMED);
		when(reservationJpaRepository.findDetailedById(ID)).thenReturn(Optional.of(entity));

		assertThat(reservationService.findById(ID)).isEqualTo(entity.toDomain());
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(reservationJpaRepository.findDetailedById(ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.findById(ID))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Reservation with id '" + ID + "' not found");
	}

	@Test
	void startMovesReservationToInProgress() {
		stubExistingAndSave(ReservationStatus.CONFIRMED);

		Reservation result = reservationService.start(ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.IN_PROGRESS);
		assertThat(result.startedAt()).isNotNull();
	}

	@Test
	void completeMovesReservationToCompleted() {
		stubExistingAndSave(ReservationStatus.IN_PROGRESS);

		Reservation result = reservationService.complete(ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.COMPLETED);
		assertThat(result.completedAt()).isNotNull();
	}

	@Test
	void cancelMovesReservationToCancelled() {
		stubExistingAndSave(ReservationStatus.CONFIRMED);

		Reservation result = reservationService.cancel(ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.CANCELLED);
	}

	@Test
	void startRejectsIllegalTransition() {
		when(reservationJpaRepository.findDetailedById(ID))
				.thenReturn(Optional.of(entity(ReservationStatus.IN_PROGRESS)));

		assertThatThrownBy(() -> reservationService.start(ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void completeRejectsIllegalTransition() {
		when(reservationJpaRepository.findDetailedById(ID))
				.thenReturn(Optional.of(entity(ReservationStatus.CONFIRMED)));

		assertThatThrownBy(() -> reservationService.complete(ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationJpaRepository, never()).save(any());
	}

	@Test
	void cancelRejectsIllegalTransition() {
		when(reservationJpaRepository.findDetailedById(ID))
				.thenReturn(Optional.of(entity(ReservationStatus.COMPLETED)));

		assertThatThrownBy(() -> reservationService.cancel(ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationJpaRepository, never()).save(any());
	}

	private void stubCatalogAndSave() {
		LocationEntity destination = destinationEntity();
		CharacterEntity morty = mortyEntity(destination);
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.of(destination));
		when(locationJpaRepository.getReferenceById(1)).thenReturn(destination);
		when(characterJpaRepository.findAllById(List.of(2))).thenReturn(List.of(morty));
		when(reservationJpaRepository.save(any(ReservationEntity.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
	}

	private void stubExistingAndSave(ReservationStatus status) {
		LocationEntity destination = destinationEntity();
		CharacterEntity morty = mortyEntity(destination);
		when(reservationJpaRepository.findDetailedById(ID))
				.thenReturn(Optional.of(ReservationEntity.fromDomain(
						reservation(status), destination, Set.of(morty))));
		when(locationJpaRepository.getReferenceById(1)).thenReturn(destination);
		when(characterJpaRepository.findAllById(List.of(2))).thenReturn(List.of(morty));
		when(reservationJpaRepository.save(any(ReservationEntity.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
	}

	private static ReservationEntity entity(ReservationStatus status) {
		LocationEntity destination = destinationEntity();
		return ReservationEntity.fromDomain(
				reservation(status), destination, Set.of(mortyEntity(destination)));
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
				"Rick Sanchez", "rick@sanchez.dev", 1, LocalDate.now().plusDays(1),
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
