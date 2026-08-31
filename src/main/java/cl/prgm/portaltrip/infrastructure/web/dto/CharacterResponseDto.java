package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Character;

public record CharacterResponseDto(
		Integer id,
		String name,
		String status,
		String species,
		String type,
		String gender,
		NamedRefDto origin,
		NamedRefDto location,
		String image,
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
