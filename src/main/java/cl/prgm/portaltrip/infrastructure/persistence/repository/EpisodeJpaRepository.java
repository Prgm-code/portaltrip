package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.prgm.portaltrip.infrastructure.persistence.EpisodeEntity;

public interface EpisodeJpaRepository extends JpaRepository<EpisodeEntity, Integer> {

	@Query("select distinct e from EpisodeEntity e left join fetch e.characters where e.id = :id")
	Optional<EpisodeEntity> findDetailedById(@Param("id") Integer id);

	@Query("select distinct e from EpisodeEntity e left join fetch e.characters order by e.id")
	List<EpisodeEntity> findAllSummaries();

}
