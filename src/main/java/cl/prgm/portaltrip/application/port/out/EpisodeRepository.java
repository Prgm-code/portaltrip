package cl.prgm.portaltrip.application.port.out;

import java.util.List;
import java.util.Optional;

import cl.prgm.portaltrip.domain.model.Episode;

public interface EpisodeRepository {

	List<Episode> findAllSummaries();

	Optional<Episode> findDetailedById(Integer id);

}
