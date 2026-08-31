package cl.prgm.portaltrip.application.port.in;

import cl.prgm.portaltrip.domain.model.TripType;

public record QuoteQuery(
		Integer destinationId,
		int passengers,
		TripType tripType,
		boolean insurance) {
}
