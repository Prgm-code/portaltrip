package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import jakarta.persistence.LockModeType;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select u from UserEntity u where u.id = :id")
	Optional<UserEntity> findByIdForUpdate(@Param("id") UUID id);

}
