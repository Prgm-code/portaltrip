package cl.prgm.portaltrip.application.service;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.IdempotencyConflictException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationBalanceResult;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.domain.service.QuoteCalculator;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.ReservationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.ReservationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	private final ReservationJpaRepository reservationJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final CharacterJpaRepository characterJpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final QuoteCalculator quoteCalculator = new QuoteCalculator();

	public ReservationServiceImpl(
			ReservationJpaRepository reservationJpaRepository,
			LocationJpaRepository locationJpaRepository,
			CharacterJpaRepository characterJpaRepository,
			UserJpaRepository userJpaRepository) {
		this.reservationJpaRepository = reservationJpaRepository;
		this.locationJpaRepository = locationJpaRepository;
		this.characterJpaRepository = characterJpaRepository;
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	@Transactional
	public ReservationBalanceResult create(UUID userId, UUID idempotencyKey, ReservationDraft draft) {
		UserEntity userEntity = getUserForUpdate(userId);
		ReservationEntity repeated = reservationJpaRepository
				.findByUserIdAndIdempotencyKey(userId, idempotencyKey)
				.orElse(null);
		if (repeated != null) {
			Reservation reservation = repeated.toDomain();
			if (!reservation.matches(draft)) {
				throw new IdempotencyConflictException();
			}
			return new ReservationBalanceResult(reservation, userEntity.toDomain().balance());
		}

		LocationEntity destinationEntity = locationJpaRepository.findDetailedById(draft.destinationId())
				.orElseThrow(() -> new ResourceNotFoundException("Location", draft.destinationId()));
		Location destination = destinationEntity.toDomain();
		List<CharacterEntity> companionEntities = characterJpaRepository.findAllById(draft.companionIds());
		List<Integer> foundCompanionIds = companionEntities.stream()
				.map(CharacterEntity::getId)
				.toList();
		draft.companionIds().stream()
				.filter(id -> !foundCompanionIds.contains(id))
				.findFirst()
				.ifPresent(id -> {
					throw new ResourceNotFoundException("Character", id);
				});
		List<Character> companions = companionEntities.stream()
				.map(CharacterEntity::toSummary)
				.toList();
		Quote quote = quoteCalculator.calculate(
				draft.passengers(),
				draft.tripType(),
				draft.insurance(),
				destination.type(),
				destination.dimension(),
				destination.residentIds().size());
		OffsetDateTime now = OffsetDateTime.now();
		UserAccount debitedUser = userEntity.toDomain().debit(quote.total(), now);
		userEntity.apply(debitedUser);
		Reservation reservation = Reservation.confirm(
				draft,
				userId,
				debitedUser.email(),
				idempotencyKey,
				destination,
				companions,
				quote,
				UUID.randomUUID(),
				generateNumber(),
				now);
		return new ReservationBalanceResult(save(reservation, userEntity), debitedUser.balance());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reservation> findAll(UUID userId) {
		return reservationJpaRepository.findAllDetailedByUserId(userId).stream()
				.map(ReservationEntity::toDomain)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation findById(UUID userId, UUID id) {
		return findEntity(userId, id).toDomain();
	}

	private ReservationEntity findEntity(UUID userId, UUID id) {
		return reservationJpaRepository.findDetailedByIdAndUserId(id, userId)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
	}

	@Override
	@Transactional
	public ReservationBalanceResult cancel(UUID userId, UUID id) {
		Reservation cancelled = findEntity(userId, id).toDomain().cancel();
		UserEntity userEntity = getUserForUpdate(userId);
		UserAccount creditedUser = userEntity.toDomain().credit(cancelled.quote().total(), OffsetDateTime.now());
		userEntity.apply(creditedUser);
		return new ReservationBalanceResult(save(cancelled, userEntity), creditedUser.balance());
	}

	@Override
	@Transactional
	public Reservation start(UUID userId, UUID id) {
		Reservation reservation = findEntity(userId, id).toDomain().start(OffsetDateTime.now());
		return save(reservation, getUser(userId));
	}

	@Override
	@Transactional
	public Reservation complete(UUID userId, UUID id) {
		Reservation reservation = findEntity(userId, id).toDomain().complete(OffsetDateTime.now());
		return save(reservation, getUser(userId));
	}

	private Reservation save(Reservation reservation, UserEntity user) {
		LocationEntity destination = locationJpaRepository.getReferenceById(reservation.destinationId());
		Set<CharacterEntity> companions = new LinkedHashSet<>(
				characterJpaRepository.findAllById(reservation.companionIds()));
		ReservationEntity entity = ReservationEntity.fromDomain(reservation, user, destination, companions);
		return reservationJpaRepository.save(entity).toDomain();
	}

	private UserEntity getUser(UUID userId) {
		return userJpaRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", userId));
	}

	private UserEntity getUserForUpdate(UUID userId) {
		return userJpaRepository.findByIdForUpdate(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", userId));
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
