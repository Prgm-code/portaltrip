package cl.prgm.portaltrip.infrastructure.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record QuoteRequestDto(
		@NotNull(message = "Destination is required")
		@Min(value = 1, message = "Destination must be a positive ID")
		Integer destinationId,
		@Min(value = 1, message = "At least one passenger is required")
		@Max(value = 8, message = "A quote supports at most eight passengers")
		int passengers,
		@NotBlank(message = "Trip type is required")
		@Pattern(
				regexp = "express|exploration|premium",
				message = "Trip type must be express, exploration, or premium")
		String tripType,
		boolean insurance) {
}
