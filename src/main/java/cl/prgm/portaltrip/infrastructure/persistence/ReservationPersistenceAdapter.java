package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import cl.prgm.portaltrip.application.port.out.ReservationRepository;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.ReservationJpaRepository;

@Component
public class ReservationPersistenceAdapter implements ReservationRepository {

	private final ReservationJpaRepository reservationJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final CharacterJpaRepository characterJpaRepository;

	public ReservationPersistenceAdapter(
			ReservationJpaRepository reservationJpaRepository,
			LocationJpaRepository locationJpaRepository,
			CharacterJpaRepository characterJpaRepository) {
		this.reservationJpaRepository = reservationJpaRepository;
		this.locationJpaRepository = locationJpaRepository;
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public Reservation save(Reservation reservation) {
		LocationEntity destination = locationJpaRepository.getReferenceById(reservation.destinationId());
		Set<CharacterEntity> companions = new LinkedHashSet<>(
				characterJpaRepository.findAllById(reservation.companionIds()));
		ReservationEntity entity = ReservationEntity.fromDomain(reservation, destination, companions);
		return reservationJpaRepository.save(entity).toDomain();
	}

	@Override
	public Optional<Reservation> findById(UUID id) {
		return reservationJpaRepository.findDetailedById(id)
				.map(ReservationEntity::toDomain);
	}

	@Override
	public List<Reservation> findAll() {
		return reservationJpaRepository.findAllByOrderByCreatedAtDesc().stream()
				.map(ReservationEntity::toDomain)
				.toList();
	}

	@Override
	public boolean existsByNumber(String number) {
		return reservationJpaRepository.existsByNumber(number);
	}

}
