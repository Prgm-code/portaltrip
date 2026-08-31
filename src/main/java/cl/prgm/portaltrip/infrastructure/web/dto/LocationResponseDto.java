package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Location;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rick and Morty location returned by catalog endpoints")
public record LocationResponseDto(
		@Schema(description = "Location ID", example = "3")
		Integer id,
		@Schema(description = "Location name", example = "Citadel of Ricks")
		String name,
		@Schema(description = "Location type", example = "Space station")
		String type,
		@Schema(description = "Dimension", example = "unknown")
		String dimension,
		@ArraySchema(
				arraySchema = @Schema(description = "Resident character IDs", example = "[8, 14, 15]"),
				schema = @Schema(implementation = Integer.class))
		List<Integer> residentIds) {

	public static LocationResponseDto from(Location location) {
		return new LocationResponseDto(
				location.id(),
				location.name(),
				location.type(),
				location.dimension(),
				location.residentIds());
	}

}
