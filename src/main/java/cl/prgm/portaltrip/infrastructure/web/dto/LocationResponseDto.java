package cl.prgm.portaltrip.infrastructure.web.dto;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Location;

public record LocationResponseDto(
		Integer id,
		String name,
		String type,
		String dimension,
		String url,
		List<Integer> residentIds) {

	public static LocationResponseDto from(Location location) {
		return new LocationResponseDto(
				location.id(),
				location.name(),
				location.type(),
				location.dimension(),
				location.url(),
				location.residentIds());
	}

}
