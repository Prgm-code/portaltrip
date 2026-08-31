package cl.prgm.portaltrip.domain.model;

import java.util.List;

public record Location(
		Integer id,
		String name,
		String type,
		String dimension,
		List<Integer> residentIds) {

	public boolean requiresInterdimensionalInsurance() {
		return "unknown".equalsIgnoreCase(dimension);
	}
}
