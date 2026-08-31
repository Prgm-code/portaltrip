package cl.prgm.portaltrip.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Input used to calculate a quote without creating a reservation")
public record QuoteRequestDto(
		@Schema(description = "Destination location ID", example = "3", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
		@NotNull(message = "Destination is required")
		@Min(value = 1, message = "Destination must be a positive ID")
		Integer destinationId,
		@Schema(description = "Number of passengers", example = "2", minimum = "1", maximum = "8", requiredMode = Schema.RequiredMode.REQUIRED)
		@Min(value = 1, message = "At least one passenger is required")
		@Max(value = 8, message = "A quote supports at most eight passengers")
		int passengers,
		@Schema(description = "Trip service level", example = "exploration", allowableValues = {"express", "exploration", "premium"}, requiredMode = Schema.RequiredMode.REQUIRED)
		@NotBlank(message = "Trip type is required")
		@Pattern(
				regexp = "express|exploration|premium",
				message = "Trip type must be express, exploration, or premium")
		String tripType,
		@Schema(description = "Whether interdimensional insurance is requested", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
		boolean insurance) {
}
