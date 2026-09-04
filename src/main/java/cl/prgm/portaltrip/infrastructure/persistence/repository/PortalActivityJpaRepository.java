package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.PortalActivityEntity;

public interface PortalActivityJpaRepository extends JpaRepository<PortalActivityEntity, UUID> {}
