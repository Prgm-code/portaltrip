package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.application.port.out.CharacterRepository;
import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.application.port.out.ReservationRepository;
import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

	private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private LocationRepository locationRepository;

	@Mock
	private CharacterRepository characterRepository;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@Test
	void createPersistsConfirmedReservation() {
		stubCatalog();
		when(reservationRepository.existsByNumber(anyString())).thenReturn(false);
		when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

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
		stubCatalog();
		when(reservationRepository.existsByNumber(anyString())).thenReturn(true, false);
		when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Reservation result = reservationService.create(draft());

		assertThat(result.status()).isEqualTo(ReservationStatus.CONFIRMED);
		verify(reservationRepository, times(2)).existsByNumber(anyString());
	}

	@Test
	void createRejectsInvalidDraft() {
		stubCatalog();
		ReservationDraft invalid = new ReservationDraft(
				"Rick Sanchez", "not-an-email", 1, LocalDate.now().plusDays(1),
				2, List.of(2), TripType.EXPRESS, false, "");

		assertThatThrownBy(() -> reservationService.create(invalid))
				.isInstanceOf(DomainValidationException.class)
				.hasMessageContaining("Ingresa un correo electrónico válido.");
		verify(reservationRepository, never()).save(any());
	}

	@Test
	void createRejectsMissingDestinationId() {
		ReservationDraft invalid = new ReservationDraft(
				"Rick Sanchez", "rick@sanchez.dev", null, LocalDate.now().plusDays(1),
				2, List.of(), TripType.EXPRESS, false, "");
		when(characterRepository.findAllByIds(List.of())).thenReturn(List.of());

		assertThatThrownBy(() -> reservationService.create(invalid))
				.isInstanceOf(DomainValidationException.class)
				.hasMessageContaining("Selecciona un destino.");
		verifyNoInteractions(locationRepository);
	}

	@Test
	void createThrowsWhenDestinationNotFound() {
		when(locationRepository.findDetailedById(1)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.create(draft()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Location with id '1' not found");
	}

	@Test
	void findAllDelegatesToRepository() {
		Reservation reservation = reservation(ReservationStatus.CONFIRMED);
		when(reservationRepository.findAll()).thenReturn(List.of(reservation));

		assertThat(reservationService.findAll()).containsExactly(reservation);
	}

	@Test
	void findByIdReturnsReservation() {
		Reservation reservation = reservation(ReservationStatus.CONFIRMED);
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation));

		assertThat(reservationService.findById(ID)).isEqualTo(reservation);
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reservationService.findById(ID))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Reservation with id '" + ID + "' not found");
	}

	@Test
	void startMovesReservationToInProgress() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation(ReservationStatus.CONFIRMED)));
		when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Reservation result = reservationService.start(ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.IN_PROGRESS);
		assertThat(result.startedAt()).isNotNull();
	}

	@Test
	void completeMovesReservationToCompleted() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation(ReservationStatus.IN_PROGRESS)));
		when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Reservation result = reservationService.complete(ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.COMPLETED);
		assertThat(result.completedAt()).isNotNull();
	}

	@Test
	void cancelMovesReservationToCancelled() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation(ReservationStatus.CONFIRMED)));
		when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Reservation result = reservationService.cancel(ID);

		assertThat(result.status()).isEqualTo(ReservationStatus.CANCELLED);
	}

	@Test
	void startRejectsIllegalTransition() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation(ReservationStatus.IN_PROGRESS)));

		assertThatThrownBy(() -> reservationService.start(ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationRepository, never()).save(any());
	}

	@Test
	void completeRejectsIllegalTransition() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation(ReservationStatus.CONFIRMED)));

		assertThatThrownBy(() -> reservationService.complete(ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationRepository, never()).save(any());
	}

	@Test
	void cancelRejectsIllegalTransition() {
		when(reservationRepository.findById(ID)).thenReturn(Optional.of(reservation(ReservationStatus.COMPLETED)));

		assertThatThrownBy(() -> reservationService.cancel(ID))
				.isInstanceOf(InvalidReservationStateException.class);
		verify(reservationRepository, never()).save(any());
	}

	private void stubCatalog() {
		Location destination = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(1, 2, 3, 4, 5));
		Character morty = new Character(
				2, "Morty Smith", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 1, "Earth (C-137)", "img", List.of());
		when(locationRepository.findDetailedById(1)).thenReturn(Optional.of(destination));
		when(characterRepository.findAllByIds(List.of(2))).thenReturn(List.of(morty));
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
