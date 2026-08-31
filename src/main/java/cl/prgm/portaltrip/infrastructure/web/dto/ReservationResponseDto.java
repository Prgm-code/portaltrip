package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Persisted reservation with destination, companions, quote, and lifecycle timestamps")
public record ReservationResponseDto(
		@Schema(description = "Reservation UUID", example = "10d41b1e-74c4-46d8-9522-cbd399aa2543")
		UUID id,
		@Schema(description = "Human-readable reservation number", example = "PT-2026-238413")
		String number,
		@Schema(description = "Current lifecycle state", example = "CONFIRMED", allowableValues = {"CONFIRMED", "IN_PROGRESS", "COMPLETED", "CANCELLED"})
		ReservationStatus status,
		@Schema(description = "Passenger full name", example = "Morty Smith")
		String passengerName,
		@Schema(description = "Passenger email", example = "morty@example.com")
		String email,
		@Schema(description = "Selected destination")
		NamedRefDto destination,
		@Schema(description = "Travel date", example = "2030-12-20", format = "date")
		LocalDate travelDate,
		@Schema(description = "Number of passengers", example = "2")
		int passengers,
		@Schema(description = "Selected alive companions")
		List<CompanionDto> companions,
		@Schema(description = "Trip service level", example = "exploration", allowableValues = {"express", "exploration", "premium"})
		String tripType,
		@Schema(description = "Whether insurance is included", example = "true")
		boolean insurance,
		@Schema(description = "Passenger comments", example = "Window seat")
		String comments,
		@Schema(description = "Calculated quote")
		QuoteDto quote,
		@Schema(description = "Creation timestamp", example = "2026-08-31T22:30:00Z")
		OffsetDateTime createdAt,
		@Schema(description = "Start timestamp; null before the trip starts", example = "2026-09-01T10:00:00Z")
		OffsetDateTime startedAt,
		@Schema(description = "Completion timestamp; null before completion", example = "2026-09-01T12:00:00Z")
		OffsetDateTime completedAt) {

	public static ReservationResponseDto from(
			Reservation reservation,
			Location destination,
			List<Character> companions) {
		return new ReservationResponseDto(
				reservation.id(),
				reservation.number(),
				reservation.status(),
				reservation.passengerName(),
				reservation.email(),
				new NamedRefDto(destination.id(), destination.name()),
				reservation.travelDate(),
				reservation.passengers(),
				companions.stream().map(CompanionDto::from).toList(),
				reservation.tripType().code(),
				reservation.insurance(),
				reservation.comments(),
				QuoteDto.from(reservation.quote()),
				reservation.createdAt(),
				reservation.startedAt(),
				reservation.completedAt());
	}

}
