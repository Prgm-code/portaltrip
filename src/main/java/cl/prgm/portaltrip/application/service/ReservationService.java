package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationBalanceResult;
import cl.prgm.portaltrip.domain.model.ReservationDraft;

public interface ReservationService {

	ReservationBalanceResult create(UUID userId, UUID idempotencyKey, ReservationDraft draft);

	List<Reservation> findAll(UUID userId);

	Reservation findById(UUID userId, UUID id);

	ReservationBalanceResult cancel(UUID userId, UUID id);

	Reservation start(UUID userId, UUID id);

	Reservation complete(UUID userId, UUID id);

}
