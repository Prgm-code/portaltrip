package cl.prgm.portaltrip.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.exception.DomainValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationDraftTest {

	@Test
	void normalizesValuesAndProtectsCompanionIds() {
		List<Integer> companionIds = new ArrayList<>(List.of(3, 2));

		ReservationDraft draft = draft("  Rick Sanchez  ", 1,
				LocalDate.now().plusDays(1), 2, companionIds, TripType.EXPRESS, null);
		companionIds.add(4);

		assertThat(draft.passengerName()).isEqualTo("Rick Sanchez");
		assertThat(draft.companionIds()).containsExactly(2, 3);
		assertThat(draft.comments()).isEmpty();
	}

	@Test
	void defaultsMissingCompanionsAndTrimsComments() {
		ReservationDraft draft = draft("Rick Sanchez", 1,
				LocalDate.now().plusDays(1), 2, null, TripType.EXPRESS, "  stable portal  ");

		assertThat(draft.companionIds()).isEmpty();
		assertThat(draft.comments()).isEqualTo("stable portal");
	}

	@Test
	void rejectsInvalidPassengerName() {
		assertInvalid("Enter the passenger's full name.",
				() -> draft(" Al ", 1, futureDate(), 2, List.of(), TripType.EXPRESS, ""));
		assertInvalid("Enter the passenger's full name.",
				() -> draft(null, 1, futureDate(), 2, List.of(), TripType.EXPRESS, ""));
	}

	@Test
	void rejectsInvalidDestination() {
		assertInvalid("Select a destination.",
				() -> draft("Rick Sanchez", 0, futureDate(), 2, List.of(), TripType.EXPRESS, ""));
		assertInvalid("Select a destination.",
				() -> draft("Rick Sanchez", null, futureDate(), 2, List.of(), TripType.EXPRESS, ""));
	}

	@Test
	void rejectsInvalidTravelDate() {
		assertInvalid("The travel date must be in the future.",
				() -> draft("Rick Sanchez", 1, LocalDate.now(), 2, List.of(), TripType.EXPRESS, ""));
		assertInvalid("The travel date must be in the future.",
				() -> draft("Rick Sanchez", 1, null, 2, List.of(), TripType.EXPRESS, ""));
	}

	@Test
	void rejectsInvalidPassengerCount() {
		assertInvalid("A reservation must have between 1 and 8 passengers.",
				() -> draft("Rick Sanchez", 1, futureDate(), 0, List.of(), TripType.EXPRESS, ""));
		assertInvalid("A reservation must have between 1 and 8 passengers.",
				() -> draft("Rick Sanchez", 1, futureDate(), 9, List.of(), TripType.EXPRESS, ""));
	}

	@Test
	void rejectsTooManyCompanions() {
		assertInvalid("A reservation can have at most three companions.",
				() -> draft("Rick Sanchez", 1, futureDate(), 2,
						List.of(2, 3, 4, 5), TripType.EXPRESS, ""));
	}

	@Test
	void rejectsMissingTripType() {
		assertInvalid("Select a valid trip type.",
				() -> draft("Rick Sanchez", 1, futureDate(), 2, List.of(), null, ""));
	}

	private static ReservationDraft draft(
			String passengerName,
			Integer destinationId,
			LocalDate travelDate,
			int passengers,
			List<Integer> companionIds,
			TripType tripType,
			String comments) {
		return new ReservationDraft(
				passengerName,
				destinationId,
				travelDate,
				passengers,
				companionIds,
				tripType,
				false,
				comments);
	}

	private static LocalDate futureDate() {
		return LocalDate.now().plusDays(1);
	}

	private static void assertInvalid(String message, ThrowingCallable callable) {
		assertThatThrownBy(callable::call)
				.isInstanceOf(DomainValidationException.class)
				.hasMessage(message);
	}

	@FunctionalInterface
	private interface ThrowingCallable {
		void call();
	}
}
