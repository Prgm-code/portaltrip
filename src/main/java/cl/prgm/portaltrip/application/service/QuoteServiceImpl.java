package cl.prgm.portaltrip.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.service.QuoteCalculator;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;

@Service
public class QuoteServiceImpl implements QuoteService {

	private final LocationJpaRepository locationJpaRepository;
	private final QuoteCalculator quoteCalculator = new QuoteCalculator();

	public QuoteServiceImpl(LocationJpaRepository locationJpaRepository) {
		this.locationJpaRepository = locationJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Quote quote(QuoteQuery query) {
		Location destination = locationJpaRepository.findDetailedById(query.destinationId())
				.map(LocationEntity::toDomain)
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
