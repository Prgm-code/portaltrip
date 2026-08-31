package cl.prgm.portaltrip.domain.exception;

import cl.prgm.portaltrip.domain.model.ReservationStatus;

public class InvalidReservationStateException extends RuntimeException {

	public InvalidReservationStateException(String number, ReservationStatus current, ReservationStatus target) {
		super("Reservation '" + number + "' cannot transition from " + current + " to " + target);
	}

}
