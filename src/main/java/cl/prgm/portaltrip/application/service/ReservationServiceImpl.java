package cl.prgm.portaltrip.application.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.application.port.in.ReservationService;
import cl.prgm.portaltrip.application.port.out.CharacterRepository;
import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.application.port.out.ReservationRepository;
import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.service.QuoteCalculator;
import cl.prgm.portaltrip.domain.service.ReservationValidator;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final LocationRepository locationRepository;
	private final CharacterRepository characterRepository;
	private final QuoteCalculator quoteCalculator = new QuoteCalculator();
	private final ReservationValidator reservationValidator = new ReservationValidator();

	public ReservationServiceImpl(
			ReservationRepository reservationRepository,
			LocationRepository locationRepository,
			CharacterRepository characterRepository) {
		this.reservationRepository = reservationRepository;
		this.locationRepository = locationRepository;
		this.characterRepository = characterRepository;
	}

	@Override
	@Transactional
	public Reservation create(ReservationDraft draft) {
		Location destination = draft.destinationId() == null
				? null
				: locationRepository.findDetailedById(draft.destinationId())
						.orElseThrow(() -> new ResourceNotFoundException("Location", draft.destinationId()));
		List<Character> companions = characterRepository.findAllByIds(draft.companionIds());
		List<String> errors = reservationValidator.validate(draft, destination, companions);
		if (!errors.isEmpty()) {
			throw new DomainValidationException(errors);
		}
		Quote quote = quoteCalculator.calculate(
				draft.passengers(),
				draft.tripType(),
				draft.insurance(),
				destination.type(),
				destination.dimension(),
				destination.residentIds().size());
		Reservation reservation = new Reservation(
				UUID.randomUUID(),
				generateNumber(),
				ReservationStatus.CONFIRMED,
				draft.passengerName(),
				draft.email(),
				destination.id(),
				draft.travelDate(),
				draft.passengers(),
				draft.companionIds(),
				draft.tripType(),
				draft.insurance(),
				draft.comments(),
				quote,
				OffsetDateTime.now(),
				null,
				null);
		return reservationRepository.save(reservation);
	}

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public Reservation findById(UUID id) {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
	}

	@Override
	@Transactional
	public Reservation cancel(UUID id) {
		return reservationRepository.save(findById(id).cancel());
	}

	@Override
	@Transactional
	public Reservation start(UUID id) {
		return reservationRepository.save(findById(id).start(OffsetDateTime.now()));
	}

	@Override
	@Transactional
	public Reservation complete(UUID id) {
		return reservationRepository.save(findById(id).complete(OffsetDateTime.now()));
	}

	private String generateNumber() {
		String number;
		do {
			number = "PT-%d-%06d".formatted(
					OffsetDateTime.now().getYear(),
					ThreadLocalRandom.current().nextInt(1_000_000));
		} while (reservationRepository.existsByNumber(number));
		return number;
	}

}
