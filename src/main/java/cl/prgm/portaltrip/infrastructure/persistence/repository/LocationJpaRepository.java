package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, Integer> {

	@Query("select distinct l from LocationEntity l left join fetch l.residents where l.id = :id")
	Optional<LocationEntity> findDetailedById(@Param("id") Integer id);

	@Query("select l from LocationEntity l order by l.id")
	List<LocationEntity> findAllSummaries();

}
