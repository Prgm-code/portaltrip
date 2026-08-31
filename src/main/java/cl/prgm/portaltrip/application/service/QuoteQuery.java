package cl.prgm.portaltrip.application.service;

import cl.prgm.portaltrip.domain.model.TripType;

public record QuoteQuery(
		Integer destinationId,
		int passengers,
		TripType tripType,
		boolean insurance) {
}
