package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

	private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
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
		assertThat(reservation.travelDate()).isEqualTo(LocalDate.of(2026, 2, 1));
		assertThat(reservation.passengers()).isEqualTo(2);
		assertThat(reservation.companionIds()).containsExactly(2);
		assertThat(reservation.tripType()).isEqualTo(TripType.EXPLORATION);
		assertThat(reservation.insurance()).isTrue();
		assertThat(reservation.comments()).isEqualTo("portal estable");
		assertThat(reservation.quote().total()).isEqualByComparingTo(new BigDecimal("2456.00"));
		assertThat(reservation.createdAt()).isEqualTo(CREATED);
		assertThat(reservation.startedAt()).isNull();
		assertThat(reservation.completedAt()).isNull();
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
	void cancelMovesConfirmedAndInProgressToCancelled() {
		Reservation cancelledFromConfirmed = reservation(ReservationStatus.CONFIRMED).cancel();
		Reservation cancelledFromInProgress = reservation(ReservationStatus.CONFIRMED).start(NOW).cancel();

		assertThat(cancelledFromConfirmed.status()).isEqualTo(ReservationStatus.CANCELLED);
		assertThat(cancelledFromConfirmed.startedAt()).isNull();
		assertThat(cancelledFromInProgress.status()).isEqualTo(ReservationStatus.CANCELLED);
		assertThat(cancelledFromInProgress.startedAt()).isEqualTo(NOW);
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

	private static Reservation reservation(ReservationStatus status) {
		Quote quote = new Quote(
				new BigDecimal("1200"),
				new BigDecimal("300.00"),
				new BigDecimal("216.00"),
				new BigDecimal("360.00"),
				new BigDecimal("380"),
				new BigDecimal("2456.00"),
				RiskLevel.MEDIUM);
		return new Reservation(
				ID,
				"PT-2026-000001",
				status,
				"Rick Sanchez",
				"rick@sanchez.dev",
				1,
				LocalDate.of(2026, 2, 1),
				2,
				List.of(2),
				TripType.EXPLORATION,
				true,
				"portal estable",
				quote,
				CREATED,
				null,
				null);
	}

}
