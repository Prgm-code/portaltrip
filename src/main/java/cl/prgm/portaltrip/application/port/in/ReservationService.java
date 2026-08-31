package cl.prgm.portaltrip.application.port.in;

import java.util.List;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;

public interface ReservationService {

	Reservation create(ReservationDraft draft);

	List<Reservation> findAll();

	Reservation findById(UUID id);

	Reservation cancel(UUID id);

	Reservation start(UUID id);

	Reservation complete(UUID id);

}
