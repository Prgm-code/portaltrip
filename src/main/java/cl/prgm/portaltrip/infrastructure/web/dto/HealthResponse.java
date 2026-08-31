package cl.prgm.portaltrip.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Application health information")
public record HealthResponse(
		@Schema(description = "Health state", example = "UP")
		String status,
		@Schema(description = "Application name", example = "portaltrip")
		String application,
		@Schema(description = "UTC timestamp", example = "2026-08-31T22:30:00Z")
		String timestamp) {
}
