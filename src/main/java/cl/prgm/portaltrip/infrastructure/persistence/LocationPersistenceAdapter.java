package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;

@Component
public class LocationPersistenceAdapter implements LocationRepository {

	private final LocationJpaRepository locationJpaRepository;

	public LocationPersistenceAdapter(LocationJpaRepository locationJpaRepository) {
		this.locationJpaRepository = locationJpaRepository;
	}

	@Override
	public List<Location> findAllSummaries() {
		return locationJpaRepository.findAllSummaries().stream()
				.map(LocationEntity::toDomain)
				.toList();
	}

	@Override
	public Optional<Location> findDetailedById(Integer id) {
		return locationJpaRepository.findDetailedById(id)
				.map(LocationEntity::toDomain);
	}

}
