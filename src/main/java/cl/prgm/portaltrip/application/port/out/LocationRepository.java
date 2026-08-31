package cl.prgm.portaltrip.application.port.out;

import java.util.List;
import java.util.Optional;

import cl.prgm.portaltrip.domain.model.Location;

public interface LocationRepository {

	List<Location> findAllSummaries();

	Optional<Location> findDetailedById(Integer id);

}
