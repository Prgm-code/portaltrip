package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.List;

import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.TripType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Input used to validate, quote, and create a reservation")
public record ReservationRequestDto(
		@Schema(description = "Passenger full name", example = "Morty Smith", minLength = 3, requiredMode = Schema.RequiredMode.REQUIRED)
		@NotBlank(message = "Passenger name is required")
		@Size(min = 3, message = "Passenger name must have at least three characters")
		String passengerName,
		@Schema(description = "Destination location ID", example = "3", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Destination is required")
		@Min(value = 1, message = "Destination must be a positive ID")
		Integer destinationId,
		@Schema(description = "Future travel date", example = "2030-12-20", format = "date", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Travel date is required")
		@Future(message = "Travel date must be in the future")
		LocalDate travelDate,
		@Schema(description = "Number of passengers", example = "2", minimum = "1", maximum = "8", requiredMode = Schema.RequiredMode.REQUIRED)
		@Min(value = 1, message = "At least one passenger is required")
		@Max(value = 8, message = "A reservation supports at most eight passengers")
		int passengers,
		@ArraySchema(
				arraySchema = @Schema(description = "Up to three alive character IDs", example = "[1, 2]"),
				schema = @Schema(implementation = Integer.class))
		@Size(max = 3, message = "A reservation supports at most three companions")
		List<Integer> companionIds,
		@Schema(description = "Trip service level", example = "exploration", allowableValues = {"express", "exploration", "premium"}, requiredMode = Schema.RequiredMode.REQUIRED)
		@NotBlank(message = "Trip type is required")
		@Pattern(
				regexp = "express|exploration|premium",
				message = "Trip type must be express, exploration, or premium")
		String tripType,
		@Schema(description = "Whether interdimensional insurance is accepted", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
		boolean insurance,
		@Schema(description = "Optional passenger comments", example = "Window seat")
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
				destinationId,
				travelDate,
				passengers,
				companionIds,
				TripType.fromCode(tripType),
				insurance,
				comments);
	}

}
