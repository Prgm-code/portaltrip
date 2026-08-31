package cl.prgm.portaltrip.infrastructure.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record QuoteRequestDto(
		@NotNull @Min(1) Integer destinationId,
		@Min(1) @Max(8) int passengers,
		@NotBlank @Pattern(regexp = "express|exploration|premium") String tripType,
		boolean insurance) {
}
