package cl.prgm.portaltrip.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.prgm.portaltrip.infrastructure.persistence.ReservationEntity;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {

	@Query("""
			select distinct r from ReservationEntity r
			join fetch r.destination
			left join fetch r.companions
			where r.id = :id and r.user.id = :userId
			""")
	Optional<ReservationEntity> findDetailedByIdAndUserId(
			@Param("id") UUID id,
			@Param("userId") UUID userId);

	@Query("""
			select distinct r from ReservationEntity r
			join fetch r.destination
			left join fetch r.companions
			where r.user.id = :userId
			order by r.createdAt desc
			""")
	List<ReservationEntity> findAllDetailedByUserId(@Param("userId") UUID userId);

	@Query("""
			select distinct r from ReservationEntity r
			join fetch r.destination
			left join fetch r.companions
			where r.user.id = :userId and r.idempotencyKey = :idempotencyKey
			""")
	Optional<ReservationEntity> findByUserIdAndIdempotencyKey(
			@Param("userId") UUID userId,
			@Param("idempotencyKey") UUID idempotencyKey);

	boolean existsByNumber(String number);

}
