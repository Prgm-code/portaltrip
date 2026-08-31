package cl.prgm.portaltrip.application.port.in;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Location;

public interface LocationService {

	List<Location> findAll();

	Location findById(Integer id);

}
