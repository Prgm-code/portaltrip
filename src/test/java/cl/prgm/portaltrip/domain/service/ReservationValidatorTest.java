package cl.prgm.portaltrip.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.TripType;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationValidatorTest {

	private final ReservationValidator validator = new ReservationValidator();

	@Test
	void acceptsValidDraft() {
		List<String> errors = validator.validate(validDraft(), destination(), List.of(alive(2)));

		assertThat(errors).isEmpty();
	}

	@Test
	void rejectsShortOrMissingPassengerName() {
		assertThat(validator.validate(validDraft(null), destination(), List.of()))
				.contains("Ingresa el nombre completo del pasajero.");
		assertThat(validator.validate(validDraft(" Al "), destination(), List.of()))
				.contains("Ingresa el nombre completo del pasajero.");
	}

	@Test
	void rejectsInvalidEmail() {
		assertThat(validator.validate(validDraftWithEmail(null), destination(), List.of()))
				.contains("Ingresa un correo electrónico válido.");
		assertThat(validator.validate(validDraftWithEmail("rick.sanchez"), destination(), List.of()))
				.contains("Ingresa un correo electrónico válido.");
	}

	@Test
	void rejectsMissingDestination() {
		ReservationDraft draft = new ReservationDraft(
				"Rick Sanchez", "rick@sanchez.dev", null, LocalDate.now().plusDays(1),
				2, List.of(), TripType.EXPRESS, false, "");

		assertThat(validator.validate(draft, null, List.of()))
				.contains("Selecciona un destino.");
	}

	@Test
	void rejectsMissingOrPastTravelDate() {
		assertThat(validator.validate(validDraftWithDate(null), destination(), List.of()))
				.contains("La fecha del viaje debe ser futura.");
		assertThat(validator.validate(validDraftWithDate(LocalDate.now()), destination(), List.of()))
				.contains("La fecha del viaje debe ser futura.");
	}

	@Test
	void rejectsOutOfRangePassengers() {
		assertThat(validator.validate(validDraftWithPassengers(0), destination(), List.of()))
				.contains("La reserva admite entre 1 y 8 pasajeros.");
		assertThat(validator.validate(validDraftWithPassengers(9), destination(), List.of()))
				.contains("La reserva admite entre 1 y 8 pasajeros.");
		assertThat(validator.validate(validDraftWithPassengers(8), destination(), List.of()))
				.isEmpty();
	}

	@Test
	void rejectsMoreThanThreeCompanions() {
		ReservationDraft draft = new ReservationDraft(
				"Rick Sanchez", "rick@sanchez.dev", 1, LocalDate.now().plusDays(1),
				2, List.of(2, 3, 4, 5), TripType.EXPRESS, false, "");

		assertThat(validator.validate(draft, destination(), List.of()))
				.contains("Puedes viajar con un máximo de tres personajes.");
	}

	@Test
	void rejectsDeadCompanions() {
		assertThat(validator.validate(validDraft(), destination(), List.of(alive(2), dead(3))))
				.contains("Todos los personajes seleccionados deben estar vivos.");
	}

	@Test
	void requiresInsuranceForUnknownDimension() {
		Location unknown = new Location(1, "Citadel of Ricks", "Space station", "unknown", List.of(1, 2, 3, 4, 5));

		assertThat(validator.validate(validDraft(), unknown, List.of()))
				.contains("Los destinos de dimensión desconocida exigen seguro interdimensional.");
		assertThat(validator.validate(validDraftWithInsurance(true), unknown, List.of()))
				.isEmpty();
	}

	@Test
	void skipsInsuranceRuleWithoutDestination() {
		assertThat(validator.validate(validDraft(), null, List.of()))
				.doesNotContain("Los destinos de dimensión desconocida exigen seguro interdimensional.");
	}

	private static ReservationDraft validDraft() {
		return validDraft("Rick Sanchez");
	}

	private static ReservationDraft validDraft(String passengerName) {
		return new ReservationDraft(
				passengerName, "rick@sanchez.dev", 1, LocalDate.now().plusDays(1),
				2, List.of(2), TripType.EXPRESS, false, "");
	}

	private static ReservationDraft validDraftWithEmail(String email) {
		return new ReservationDraft(
				"Rick Sanchez", email, 1, LocalDate.now().plusDays(1),
				2, List.of(), TripType.EXPRESS, false, "");
	}

	private static ReservationDraft validDraftWithDate(LocalDate travelDate) {
		return new ReservationDraft(
				"Rick Sanchez", "rick@sanchez.dev", 1, travelDate,
				2, List.of(), TripType.EXPRESS, false, "");
	}

	private static ReservationDraft validDraftWithPassengers(int passengers) {
		return new ReservationDraft(
				"Rick Sanchez", "rick@sanchez.dev", 1, LocalDate.now().plusDays(1),
				passengers, List.of(), TripType.EXPRESS, false, "");
	}

	private static ReservationDraft validDraftWithInsurance(boolean insurance) {
		return new ReservationDraft(
				"Rick Sanchez", "rick@sanchez.dev", 1, LocalDate.now().plusDays(1),
				2, List.of(), TripType.EXPRESS, insurance, "");
	}

	private static Location destination() {
		return new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(1, 2, 3, 4, 5));
	}

	private static Character alive(Integer id) {
		return character(id, "Alive");
	}

	private static Character dead(Integer id) {
		return character(id, "Dead");
	}

	private static Character character(Integer id, String status) {
		return new Character(
				id, "Morty Smith", status, "Human", "", "Male",
				1, "Earth (C-137)", 1, "Earth (C-137)", "img", List.of());
	}

}
