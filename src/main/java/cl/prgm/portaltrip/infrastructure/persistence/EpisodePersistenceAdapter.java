package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import cl.prgm.portaltrip.application.port.out.EpisodeRepository;
import cl.prgm.portaltrip.domain.model.Episode;
import cl.prgm.portaltrip.infrastructure.persistence.repository.EpisodeJpaRepository;

@Component
public class EpisodePersistenceAdapter implements EpisodeRepository {

	private final EpisodeJpaRepository episodeJpaRepository;

	public EpisodePersistenceAdapter(EpisodeJpaRepository episodeJpaRepository) {
		this.episodeJpaRepository = episodeJpaRepository;
	}

	@Override
	public List<Episode> findAllSummaries() {
		return episodeJpaRepository.findAllSummaries().stream()
				.map(EpisodeEntity::toDomain)
				.toList();
	}

	@Override
	public Optional<Episode> findDetailedById(Integer id) {
		return episodeJpaRepository.findDetailedById(id)
				.map(EpisodeEntity::toDomain);
	}

}
