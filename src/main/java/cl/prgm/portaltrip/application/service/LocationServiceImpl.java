package cl.prgm.portaltrip.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;

@Service
public class LocationServiceImpl implements LocationService {

	private final LocationJpaRepository locationJpaRepository;

	public LocationServiceImpl(LocationJpaRepository locationJpaRepository) {
		this.locationJpaRepository = locationJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Location> findAll() {
		return locationJpaRepository.findAllSummaries().stream()
				.map(LocationEntity::toDomain)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Location findById(Integer id) {
		return locationJpaRepository.findDetailedById(id)
				.map(LocationEntity::toDomain)
				.orElseThrow(() -> new ResourceNotFoundException("Location", id));
	}

}
