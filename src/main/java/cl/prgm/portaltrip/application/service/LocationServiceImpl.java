package cl.prgm.portaltrip.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.application.port.in.LocationService;
import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;

@Service
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {

	private final LocationRepository locationRepository;

	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public List<Location> findAll() {
		return locationRepository.findAllSummaries();
	}

	@Override
	public Location findById(Integer id) {
		return locationRepository.findDetailedById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Location", id));
	}

}
