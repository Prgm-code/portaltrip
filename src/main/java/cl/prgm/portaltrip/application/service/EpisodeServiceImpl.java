package cl.prgm.portaltrip.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Episode;
import cl.prgm.portaltrip.infrastructure.persistence.EpisodeEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.EpisodeJpaRepository;

@Service
public class EpisodeServiceImpl implements EpisodeService {

	private final EpisodeJpaRepository episodeJpaRepository;

	public EpisodeServiceImpl(EpisodeJpaRepository episodeJpaRepository) {
		this.episodeJpaRepository = episodeJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Episode> findAll() {
		return episodeJpaRepository.findAllSummaries().stream()
				.map(EpisodeEntity::toDomain)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Episode findById(Integer id) {
		return episodeJpaRepository.findDetailedById(id)
				.map(EpisodeEntity::toDomain)
				.orElseThrow(() -> new ResourceNotFoundException("Episode", id));
	}

}
