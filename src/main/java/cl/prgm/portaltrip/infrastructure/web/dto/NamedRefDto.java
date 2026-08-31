package cl.prgm.portaltrip.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Compact reference to a named resource")
public record NamedRefDto(
		@Schema(description = "Resource ID", example = "3")
		Integer id,
		@Schema(description = "Resource name", example = "Citadel of Ricks")
		String name) {
}
