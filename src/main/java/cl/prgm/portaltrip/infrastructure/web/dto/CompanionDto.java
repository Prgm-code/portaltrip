package cl.prgm.portaltrip.infrastructure.web.dto;

import cl.prgm.portaltrip.domain.model.Character;

public record CompanionDto(Integer id, String name, String image) {

	public static CompanionDto from(Character character) {
		return new CompanionDto(character.id(), character.name(), character.image());
	}

}
