package cl.prgm.portaltrip.application.service;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Episode;

public interface EpisodeService {

	List<Episode> findAll();

	Episode findById(Integer id);

}
