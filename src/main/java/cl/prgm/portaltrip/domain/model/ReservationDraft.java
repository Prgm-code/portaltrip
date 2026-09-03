package cl.prgm.portaltrip.domain.model;

import java.time.LocalDate;
import java.util.List;

import cl.prgm.portaltrip.domain.exception.DomainValidationException;

public record ReservationDraft(
		String passengerName,
		Integer destinationId,
		LocalDate travelDate,
		int passengers,
		List<Integer> companionIds,
		TripType tripType,
		boolean insurance,
		String comments) {

	public ReservationDraft {
		passengerName = passengerName == null ? null : passengerName.trim();

		if (passengerName == null || passengerName.length() < 3) {
			throw new DomainValidationException("Enter the passenger's full name.");
		}
		if (destinationId == null || destinationId < 1) {
			throw new DomainValidationException("Select a destination.");
		}
		if (travelDate == null || !travelDate.isAfter(LocalDate.now())) {
			throw new DomainValidationException("The travel date must be in the future.");
		}
		if (passengers < 1 || passengers > 8) {
			throw new DomainValidationException("A reservation must have between 1 and 8 passengers.");
		}
		if (companionIds == null) {
			companionIds = List.of();
		}
		if (companionIds.size() > 3) {
			throw new DomainValidationException("A reservation can have at most three companions.");
		}
		if (tripType == null) {
			throw new DomainValidationException("Select a valid trip type.");
		}

		companionIds = companionIds.stream().sorted().toList();
		comments = comments == null ? "" : comments.trim();
	}
}
