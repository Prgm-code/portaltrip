package cl.prgm.portaltrip.domain.model;

import java.time.LocalDate;
import java.util.List;

public record ReservationDraft(
		String passengerName,
		String email,
		Integer destinationId,
		LocalDate travelDate,
		int passengers,
		List<Integer> companionIds,
		TripType tripType,
		boolean insurance,
		String comments) {
}
