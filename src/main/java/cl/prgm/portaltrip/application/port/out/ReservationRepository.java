package cl.prgm.portaltrip.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.Reservation;

public interface ReservationRepository {

	Reservation save(Reservation reservation);

	Optional<Reservation> findById(UUID id);

	List<Reservation> findAll();

	boolean existsByNumber(String number);

}
