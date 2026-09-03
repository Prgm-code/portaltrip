package cl.prgm.portaltrip.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;

public record Reservation(
		UUID id,
		UUID userId,
		UUID idempotencyKey,
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

	public Reservation {
		companionIds = List.copyOf(companionIds);
	}

	public static Reservation confirm(
			ReservationDraft draft,
			UUID userId,
			String email,
			UUID idempotencyKey,
			Location destination,
			List<Character> companions,
			Quote quote,
			UUID id,
			String number,
			OffsetDateTime createdAt) {
		if (companions.stream().anyMatch(companion -> !companion.isAlive())) {
			throw new DomainValidationException("Every selected companion must be alive.");
		}
		if (destination.requiresInterdimensionalInsurance() && !draft.insurance()) {
			throw new DomainValidationException(
					"Destinations in an unknown dimension require interdimensional insurance.");
		}

		return new Reservation(
				id,
				userId,
				idempotencyKey,
				number,
				ReservationStatus.CONFIRMED,
				draft.passengerName(),
				email,
				destination.id(),
				draft.travelDate(),
				draft.passengers(),
				draft.companionIds(),
				draft.tripType(),
				draft.insurance(),
				draft.comments(),
				quote,
				createdAt,
				null,
				null);
	}

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
		if (status != ReservationStatus.CONFIRMED) {
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
				userId,
				idempotencyKey,
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

	public boolean matches(ReservationDraft draft) {
		return passengerName.equals(draft.passengerName())
				&& destinationId.equals(draft.destinationId())
				&& travelDate.equals(draft.travelDate())
				&& passengers == draft.passengers()
				&& companionIds.equals(draft.companionIds())
				&& tripType == draft.tripType()
				&& insurance == draft.insurance()
				&& comments.equals(draft.comments());
	}

}
