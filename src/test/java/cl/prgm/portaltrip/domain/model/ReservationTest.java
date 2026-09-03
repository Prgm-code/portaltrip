package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

	private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
	private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
	private static final UUID IDEMPOTENCY_KEY = UUID.fromString("00000000-0000-0000-0000-000000000003");
	private static final OffsetDateTime CREATED = OffsetDateTime.parse("2026-01-01T10:00:00Z");
	private static final OffsetDateTime NOW = OffsetDateTime.parse("2026-01-02T10:00:00Z");

	@Test
	void holdsValues() {
		Reservation reservation = reservation(ReservationStatus.CONFIRMED);

		assertThat(reservation.id()).isEqualTo(ID);
		assertThat(reservation.number()).isEqualTo("PT-2026-000001");
		assertThat(reservation.status()).isEqualTo(ReservationStatus.CONFIRMED);
		assertThat(reservation.passengerName()).isEqualTo("Rick Sanchez");
		assertThat(reservation.email()).isEqualTo("rick@sanchez.dev");
		assertThat(reservation.destinationId()).isEqualTo(1);
		assertThat(reservation.travelDate()).isEqualTo(LocalDate.of(2099, 2, 1));
		assertThat(reservation.passengers()).isEqualTo(2);
		assertThat(reservation.companionIds()).containsExactly(2);
		assertThat(reservation.tripType()).isEqualTo(TripType.EXPLORATION);
		assertThat(reservation.insurance()).isTrue();
		assertThat(reservation.comments()).isEqualTo("stable portal");
		assertThat(reservation.quote().total()).isEqualByComparingTo(new BigDecimal("2456.00"));
		assertThat(reservation.createdAt()).isEqualTo(CREATED);
		assertThat(reservation.startedAt()).isNull();
		assertThat(reservation.completedAt()).isNull();
	}

	@Test
	void confirmCreatesAConfirmedReservation() {
		ReservationDraft draft = draft(false);
		Location destination = destination("Dimension C-137");
		List<Character> companions = List.of(companion("Alive"));

		Reservation reservation = Reservation.confirm(draft, USER_ID, "rick@sanchez.dev", IDEMPOTENCY_KEY,
				destination, companions, quote(), ID,
				"PT-2026-000001", CREATED);

		assertThat(reservation.status()).isEqualTo(ReservationStatus.CONFIRMED);
		assertThat(reservation.destinationId()).isEqualTo(destination.id());
		assertThat(reservation.companionIds()).containsExactly(2);
		assertThat(reservation.createdAt()).isEqualTo(CREATED);
	}

	@Test
	void confirmRejectsDeadCompanions() {
		assertThatThrownBy(() -> Reservation.confirm(
				draft(false), USER_ID, "rick@sanchez.dev", IDEMPOTENCY_KEY,
				destination("Dimension C-137"), List.of(companion("Dead")), quote(), ID,
				"PT-2026-000001", CREATED))
				.isInstanceOf(DomainValidationException.class)
				.hasMessage("Every selected companion must be alive.");
	}

	@Test
	void confirmRequiresInsuranceForUnknownDimensions() {
		Location destination = destination("unknown");

		assertThatThrownBy(() -> Reservation.confirm(
				draft(false), USER_ID, "rick@sanchez.dev", IDEMPOTENCY_KEY,
				destination, List.of(), quote(), ID, "PT-2026-000001", CREATED))
				.isInstanceOf(DomainValidationException.class)
				.hasMessage("Destinations in an unknown dimension require interdimensional insurance.");
		assertThat(Reservation.confirm(
				draft(true), USER_ID, "rick@sanchez.dev", IDEMPOTENCY_KEY,
				destination, List.of(), quote(), ID, "PT-2026-000001", CREATED).insurance())
				.isTrue();
	}

	@Test
	void startMovesConfirmedToInProgress() {
		Reservation started = reservation(ReservationStatus.CONFIRMED).start(NOW);

		assertThat(started.status()).isEqualTo(ReservationStatus.IN_PROGRESS);
		assertThat(started.startedAt()).isEqualTo(NOW);
		assertThat(started.completedAt()).isNull();
	}

	@Test
	void startRejectsNonConfirmed() {
		Reservation inProgress = reservation(ReservationStatus.IN_PROGRESS);

		assertThatThrownBy(() -> inProgress.start(NOW))
				.isInstanceOf(InvalidReservationStateException.class)
				.hasMessage("Reservation 'PT-2026-000001' cannot transition from IN_PROGRESS to IN_PROGRESS");
	}

	@Test
	void completeMovesInProgressToCompleted() {
		Reservation completed = reservation(ReservationStatus.CONFIRMED).start(NOW).complete(NOW.plusHours(2));

		assertThat(completed.status()).isEqualTo(ReservationStatus.COMPLETED);
		assertThat(completed.startedAt()).isEqualTo(NOW);
		assertThat(completed.completedAt()).isEqualTo(NOW.plusHours(2));
	}

	@Test
	void completeRejectsNonInProgress() {
		Reservation confirmed = reservation(ReservationStatus.CONFIRMED);

		assertThatThrownBy(() -> confirmed.complete(NOW))
				.isInstanceOf(InvalidReservationStateException.class)
				.hasMessage("Reservation 'PT-2026-000001' cannot transition from CONFIRMED to COMPLETED");
	}

	@Test
	void cancelMovesConfirmedToCancelled() {
		Reservation cancelledFromConfirmed = reservation(ReservationStatus.CONFIRMED).cancel();

		assertThat(cancelledFromConfirmed.status()).isEqualTo(ReservationStatus.CANCELLED);
		assertThat(cancelledFromConfirmed.startedAt()).isNull();
	}

	@Test
	void cancelRejectsInProgress() {
		Reservation inProgress = reservation(ReservationStatus.CONFIRMED).start(NOW);

		assertThatThrownBy(inProgress::cancel)
				.isInstanceOf(InvalidReservationStateException.class);
	}

	@Test
	void cancelRejectsCompleted() {
		Reservation completed = reservation(ReservationStatus.COMPLETED);

		assertThatThrownBy(completed::cancel)
				.isInstanceOf(InvalidReservationStateException.class)
				.hasMessage("Reservation 'PT-2026-000001' cannot transition from COMPLETED to CANCELLED");
	}

	@Test
	void cancelRejectsAlreadyCancelled() {
		Reservation cancelled = reservation(ReservationStatus.CANCELLED);

		assertThatThrownBy(cancelled::cancel)
				.isInstanceOf(InvalidReservationStateException.class)
				.hasMessage("Reservation 'PT-2026-000001' cannot transition from CANCELLED to CANCELLED");
	}

	@Test
	void matchesOnlyAnEquivalentDraft() {
		Reservation reservation = reservation(ReservationStatus.CONFIRMED);
		assertThat(reservation.matches(draft(true))).isTrue();
		assertThat(reservation.matches(new ReservationDraft("Morty Smith", 1, futureDate(), 2,
				List.of(2), TripType.EXPLORATION, true, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 2, futureDate(), 2,
				List.of(2), TripType.EXPLORATION, true, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 1, futureDate().plusDays(1), 2,
				List.of(2), TripType.EXPLORATION, true, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 1, futureDate(), 3,
				List.of(2), TripType.EXPLORATION, true, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 1, futureDate(), 2,
				List.of(3), TripType.EXPLORATION, true, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 1, futureDate(), 2,
				List.of(2), TripType.PREMIUM, true, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 1, futureDate(), 2,
				List.of(2), TripType.EXPLORATION, false, "stable portal"))).isFalse();
		assertThat(reservation.matches(new ReservationDraft("Rick Sanchez", 1, futureDate(), 2,
				List.of(2), TripType.EXPLORATION, true, "different"))).isFalse();
	}

	private static Reservation reservation(ReservationStatus status) {
		return new Reservation(
				ID,
				USER_ID,
				IDEMPOTENCY_KEY,
				"PT-2026-000001",
				status,
				"Rick Sanchez",
				"rick@sanchez.dev",
				1,
				LocalDate.of(2099, 2, 1),
				2,
				List.of(2),
				TripType.EXPLORATION,
				true,
				"stable portal",
				quote(),
				CREATED,
				null,
				null);
	}

	private static ReservationDraft draft(boolean insurance) {
		return new ReservationDraft(
				"Rick Sanchez",
				1,
				futureDate(),
				2,
				List.of(2),
				TripType.EXPLORATION,
				insurance,
				"stable portal");
	}

	private static LocalDate futureDate() {
		return LocalDate.of(2099, 2, 1);
	}

	private static Quote quote() {
		return new Quote(
				new BigDecimal("1200"),
				new BigDecimal("300.00"),
				new BigDecimal("216.00"),
				new BigDecimal("360.00"),
				new BigDecimal("380"),
				new BigDecimal("2456.00"),
				RiskLevel.MEDIUM);
	}

	private static Location destination(String dimension) {
		return new Location(1, "Earth (C-137)", "Planet", dimension, List.of(1, 2, 3));
	}

	private static Character companion(String status) {
		return new Character(
				2, "Morty Smith", status, "Human", "", "Male",
				1, "Earth (C-137)", 1, "Earth (C-137)", "img", List.of());
	}

}
