package cl.prgm.portaltrip.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.application.port.in.QuoteQuery;
import cl.prgm.portaltrip.application.port.in.QuoteService;
import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.service.QuoteCalculator;

@Service
@Transactional(readOnly = true)
public class QuoteServiceImpl implements QuoteService {

	private final LocationRepository locationRepository;
	private final QuoteCalculator quoteCalculator = new QuoteCalculator();

	public QuoteServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public Quote quote(QuoteQuery query) {
		Location destination = locationRepository.findDetailedById(query.destinationId())
				.orElseThrow(() -> new ResourceNotFoundException("Location", query.destinationId()));
		boolean insurance = quoteCalculator.requiresInsurance(destination.dimension()) || query.insurance();
		return quoteCalculator.calculate(
				query.passengers(),
				query.tripType(),
				insurance,
				destination.type(),
				destination.dimension(),
				destination.residentIds().size());
	}

}
