package cl.prgm.portaltrip.domain.model;

import java.util.List;

public record Character(
		Integer id,
		String name,
		String status,
		String species,
		String type,
		String gender,
		Integer originId,
		String originName,
		Integer locationId,
		String locationName,
		String image,
		List<Integer> episodeIds) {

	public boolean isAlive() {
		return "Alive".equals(status);
	}
}
