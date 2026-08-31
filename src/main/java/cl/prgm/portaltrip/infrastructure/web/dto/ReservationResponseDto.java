package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationStatus;

public record ReservationResponseDto(
		UUID id,
		String number,
		ReservationStatus status,
		String passengerName,
		String email,
		NamedRefDto destination,
		LocalDate travelDate,
		int passengers,
		List<CompanionDto> companions,
		String tripType,
		boolean insurance,
		String comments,
		QuoteDto quote,
		OffsetDateTime createdAt,
		OffsetDateTime startedAt,
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
