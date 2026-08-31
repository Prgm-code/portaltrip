package cl.prgm.portaltrip.domain.model;

import java.util.List;

public record Episode(
		Integer id,
		String name,
		String airDate,
		String code,
		List<Integer> characterIds) {
}
