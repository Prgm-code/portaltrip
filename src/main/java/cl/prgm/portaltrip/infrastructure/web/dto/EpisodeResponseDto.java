package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Episode;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rick and Morty episode returned by catalog endpoints")
public record EpisodeResponseDto(
		@Schema(description = "Episode ID", example = "1")
		Integer id,
		@Schema(description = "Episode title", example = "Pilot")
		String name,
		@Schema(description = "Original air date", example = "December 2, 2013")
		String airDate,
		@Schema(description = "Season and episode code", example = "S01E01")
		String code,
		@ArraySchema(
				arraySchema = @Schema(description = "Character IDs appearing in the episode", example = "[1, 2]"),
				schema = @Schema(implementation = Integer.class))
		List<Integer> characterIds) {

	public static EpisodeResponseDto from(Episode episode) {
		return new EpisodeResponseDto(
				episode.id(),
				episode.name(),
				episode.airDate(),
				episode.code(),
				episode.characterIds());
	}

}
