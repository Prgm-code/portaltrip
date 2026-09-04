package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.prgm.portaltrip.infrastructure.persistence.PortalStipendEntity;

public interface PortalStipendJpaRepository extends JpaRepository<PortalStipendEntity, UUID> {

	List<PortalStipendEntity> findByUser_IdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
			UUID userId,
			OffsetDateTime since);

}
