package cl.prgm.portaltrip.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.ReservationDraft;

/**
 * Reglas de validación de reservas portadas de validateReservation en travelRules.ts.
 */
public class ReservationValidator {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\S+@\\S+\\.\\S+$");
	private static final String ALIVE = "Alive";

	public List<String> validate(ReservationDraft draft, Location destination, List<Character> companions) {
		List<String> errors = new ArrayList<>();

		if (draft.passengerName() == null || draft.passengerName().trim().length() < 3) {
			errors.add("Ingresa el nombre completo del pasajero.");
		}
		if (draft.email() == null || !EMAIL_PATTERN.matcher(draft.email()).matches()) {
			errors.add("Ingresa un correo electrónico válido.");
		}
		if (draft.destinationId() == null) {
			errors.add("Selecciona un destino.");
		}
		if (draft.travelDate() == null || !draft.travelDate().isAfter(LocalDate.now())) {
			errors.add("La fecha del viaje debe ser futura.");
		}
		if (draft.passengers() < 1 || draft.passengers() > 8) {
			errors.add("La reserva admite entre 1 y 8 pasajeros.");
		}
		if (draft.companionIds().size() > 3) {
			errors.add("Puedes viajar con un máximo de tres personajes.");
		}
		if (companions.stream().anyMatch(companion -> !ALIVE.equals(companion.status()))) {
			errors.add("Todos los personajes seleccionados deben estar vivos.");
		}
		if (requiresInsurance(destination) && !draft.insurance()) {
			errors.add("Los destinos de dimensión desconocida exigen seguro interdimensional.");
		}

		return errors;
	}

	private boolean requiresInsurance(Location destination) {
		return destination != null && "unknown".equalsIgnoreCase(destination.dimension());
	}

}
