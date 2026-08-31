package cl.prgm.portaltrip.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.application.port.in.EpisodeService;
import cl.prgm.portaltrip.application.port.out.EpisodeRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Episode;

@Service
@Transactional(readOnly = true)
public class EpisodeServiceImpl implements EpisodeService {

	private final EpisodeRepository episodeRepository;

	public EpisodeServiceImpl(EpisodeRepository episodeRepository) {
		this.episodeRepository = episodeRepository;
	}

	@Override
	public List<Episode> findAll() {
		return episodeRepository.findAllSummaries();
	}

	@Override
	public Episode findById(Integer id) {
		return episodeRepository.findDetailedById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Episode", id));
	}

}
