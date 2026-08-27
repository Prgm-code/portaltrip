package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;

public interface CharacterJpaRepository extends JpaRepository<CharacterEntity, Integer> {

	@Query("""
			select distinct c from CharacterEntity c
			left join fetch c.origin
			left join fetch c.location
			left join fetch c.episodes
			where c.id = :id
			""")
	Optional<CharacterEntity> findDetailedById(@Param("id") Integer id);

	@Query("""
			select c from CharacterEntity c
			left join fetch c.origin
			left join fetch c.location
			order by c.id
			""")
	List<CharacterEntity> findAllSummaries();

}
