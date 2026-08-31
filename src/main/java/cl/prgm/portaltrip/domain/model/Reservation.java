package cl.prgm.portaltrip.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;

public record Reservation(
		UUID id,
		String number,
		ReservationStatus status,
		String passengerName,
		String email,
		Integer destinationId,
		LocalDate travelDate,
		int passengers,
		List<Integer> companionIds,
		TripType tripType,
		boolean insurance,
		String comments,
		Quote quote,
		OffsetDateTime createdAt,
		OffsetDateTime startedAt,
		OffsetDateTime completedAt) {

	public Reservation start(OffsetDateTime now) {
		if (status != ReservationStatus.CONFIRMED) {
			throw new InvalidReservationStateException(number, status, ReservationStatus.IN_PROGRESS);
		}
		return transitionTo(ReservationStatus.IN_PROGRESS, now, completedAt);
	}

	public Reservation complete(OffsetDateTime now) {
		if (status != ReservationStatus.IN_PROGRESS) {
			throw new InvalidReservationStateException(number, status, ReservationStatus.COMPLETED);
		}
		return transitionTo(ReservationStatus.COMPLETED, startedAt, now);
	}

	public Reservation cancel() {
		if (status == ReservationStatus.COMPLETED || status == ReservationStatus.CANCELLED) {
			throw new InvalidReservationStateException(number, status, ReservationStatus.CANCELLED);
		}
		return transitionTo(ReservationStatus.CANCELLED, startedAt, completedAt);
	}

	private Reservation transitionTo(
			ReservationStatus newStatus,
			OffsetDateTime newStartedAt,
			OffsetDateTime newCompletedAt) {
		return new Reservation(
				id,
				number,
				newStatus,
				passengerName,
				email,
				destinationId,
				travelDate,
				passengers,
				companionIds,
				tripType,
				insurance,
				comments,
				quote,
				createdAt,
				newStartedAt,
				newCompletedAt);
	}

}
