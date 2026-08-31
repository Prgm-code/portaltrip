package cl.prgm.portaltrip.application.service;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.service.QuoteCalculator;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.ReservationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.ReservationJpaRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	private final ReservationJpaRepository reservationJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final CharacterJpaRepository characterJpaRepository;
	private final QuoteCalculator quoteCalculator = new QuoteCalculator();

	public ReservationServiceImpl(
			ReservationJpaRepository reservationJpaRepository,
			LocationJpaRepository locationJpaRepository,
			CharacterJpaRepository characterJpaRepository) {
		this.reservationJpaRepository = reservationJpaRepository;
		this.locationJpaRepository = locationJpaRepository;
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	@Transactional
	public Reservation create(ReservationDraft draft) {
		LocationEntity destinationEntity = locationJpaRepository.findDetailedById(draft.destinationId())
				.orElseThrow(() -> new ResourceNotFoundException("Location", draft.destinationId()));
		Location destination = destinationEntity.toDomain();
		List<Character> companions = characterJpaRepository.findAllById(draft.companionIds()).stream()
				.map(CharacterEntity::toSummary)
				.toList();
		Quote quote = quoteCalculator.calculate(
				draft.passengers(),
				draft.tripType(),
				draft.insurance(),
				destination.type(),
				destination.dimension(),
				destination.residentIds().size());
		Reservation reservation = Reservation.confirm(
				draft,
				destination,
				companions,
				quote,
				UUID.randomUUID(),
				generateNumber(),
				OffsetDateTime.now());
		return save(reservation);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reservation> findAll() {
		return reservationJpaRepository.findAllByOrderByCreatedAtDesc().stream()
				.map(ReservationEntity::toDomain)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation findById(UUID id) {
		return reservationJpaRepository.findDetailedById(id)
				.map(ReservationEntity::toDomain)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
	}

	@Override
	@Transactional
	public Reservation cancel(UUID id) {
		return save(findById(id).cancel());
	}

	@Override
	@Transactional
	public Reservation start(UUID id) {
		return save(findById(id).start(OffsetDateTime.now()));
	}

	@Override
	@Transactional
	public Reservation complete(UUID id) {
		return save(findById(id).complete(OffsetDateTime.now()));
	}

	private Reservation save(Reservation reservation) {
		LocationEntity destination = locationJpaRepository.getReferenceById(reservation.destinationId());
		Set<CharacterEntity> companions = new LinkedHashSet<>(
				characterJpaRepository.findAllById(reservation.companionIds()));
		ReservationEntity entity = ReservationEntity.fromDomain(reservation, destination, companions);
		return reservationJpaRepository.save(entity).toDomain();
	}

	private String generateNumber() {
		String number;
		do {
			number = "PT-%d-%06d".formatted(
					OffsetDateTime.now().getYear(),
					ThreadLocalRandom.current().nextInt(1_000_000));
		} while (reservationJpaRepository.existsByNumber(number));
		return number;
	}

}
