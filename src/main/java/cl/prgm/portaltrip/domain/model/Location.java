package cl.prgm.portaltrip.domain.model;

import java.util.List;

public record Location(
		Integer id,
		String name,
		String type,
		String dimension,
		String url,
		List<Integer> residentIds) {
}
