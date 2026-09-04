package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.UUID;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Deltas since the previous acknowledged sample; distance is measured in portal widths. */
public record PortalActivityRequestDto(
		@NotNull UUID cycleId,
		@Min(1) int sequence,
		@Min(0) @Max(5000) long activeMs,
		@DecimalMin("0") @DecimalMax("20") double distance) {
}
