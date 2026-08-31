package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.List;

import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.TripType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReservationRequestDto(
		@NotBlank(message = "Passenger name is required")
		@Size(min = 3, message = "Passenger name must have at least three characters")
		String passengerName,
		@NotBlank(message = "Email is required")
		@Email(message = "Email must have a valid format")
		String email,
		@NotNull(message = "Destination is required")
		@Min(value = 1, message = "Destination must be a positive ID")
		Integer destinationId,
		@NotNull(message = "Travel date is required")
		@Future(message = "Travel date must be in the future")
		LocalDate travelDate,
		@Min(value = 1, message = "At least one passenger is required")
		@Max(value = 8, message = "A reservation supports at most eight passengers")
		int passengers,
		@Size(max = 3, message = "A reservation supports at most three companions")
		List<Integer> companionIds,
		@NotBlank(message = "Trip type is required")
		@Pattern(
				regexp = "express|exploration|premium",
				message = "Trip type must be express, exploration, or premium")
		String tripType,
		boolean insurance,
		String comments) {

	public ReservationRequestDto {
		if (companionIds == null) {
			companionIds = List.of();
		}
		if (comments == null) {
			comments = "";
		}
	}

	public ReservationDraft toDraft() {
		return new ReservationDraft(
				passengerName,
				email,
				destinationId,
				travelDate,
				passengers,
				companionIds,
				TripType.fromCode(tripType),
				insurance,
				comments);
	}

}
