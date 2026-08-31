package cl.prgm.portaltrip.infrastructure.web.dto;

import cl.prgm.portaltrip.domain.model.Character;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Character selected as a reservation companion")
public record CompanionDto(
		@Schema(description = "Character ID", example = "1")
		Integer id,
		@Schema(description = "Character name", example = "Rick Sanchez")
		String name,
		@Schema(description = "Remote avatar URL", example = "https://rickandmortyapi.com/api/character/avatar/1.jpeg")
		String image) {

	public static CompanionDto from(Character character) {
		return new CompanionDto(character.id(), character.name(), character.image());
	}

}
