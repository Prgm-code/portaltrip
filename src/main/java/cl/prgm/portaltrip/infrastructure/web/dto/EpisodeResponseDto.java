package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Episode;

public record EpisodeResponseDto(
		Integer id,
		String name,
		String airDate,
		String code,
		String url,
		List<Integer> characterIds) {

	public static EpisodeResponseDto from(Episode episode) {
		return new EpisodeResponseDto(
				episode.id(),
				episode.name(),
				episode.airDate(),
				episode.code(),
				episode.url(),
				episode.characterIds());
	}

}
