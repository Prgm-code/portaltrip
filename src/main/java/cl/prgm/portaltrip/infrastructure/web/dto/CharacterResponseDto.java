package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Character;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rick and Morty character returned by catalog endpoints")
public record CharacterResponseDto(
		@Schema(description = "Character ID", example = "1")
		Integer id,
		@Schema(description = "Character name", example = "Rick Sanchez")
		String name,
		@Schema(description = "Life status", example = "Alive")
		String status,
		@Schema(description = "Species", example = "Human")
		String species,
		@Schema(description = "Species subtype", example = "")
		String type,
		@Schema(description = "Gender", example = "Male")
		String gender,
		@Schema(description = "Origin location")
		NamedRefDto origin,
		@Schema(description = "Last known location")
		NamedRefDto location,
		@Schema(description = "Remote avatar URL", example = "https://rickandmortyapi.com/api/character/avatar/1.jpeg")
		String image,
		@ArraySchema(
				arraySchema = @Schema(description = "Episode IDs; omitted from list summaries", example = "[1, 2]"),
				schema = @Schema(implementation = Integer.class))
		List<Integer> episodeIds) {

	public static CharacterResponseDto from(Character character) {
		return new CharacterResponseDto(
				character.id(),
				character.name(),
				character.status(),
				character.species(),
				character.type(),
				character.gender(),
				toRef(character.originId(), character.originName()),
				toRef(character.locationId(), character.locationName()),
				character.image(),
				character.episodeIds());
	}

	private static NamedRefDto toRef(Integer id, String name) {
		if (id == null) {
			return null;
		}
		return new NamedRefDto(id, name);
	}

}
