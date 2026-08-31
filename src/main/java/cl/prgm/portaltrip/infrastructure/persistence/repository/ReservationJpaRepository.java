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
			where r.id = :id
			""")
	Optional<ReservationEntity> findDetailedById(@Param("id") UUID id);

	@Query("""
			select distinct r from ReservationEntity r
			join fetch r.destination
			left join fetch r.companions
			order by r.createdAt desc
			""")
	List<ReservationEntity> findAllByOrderByCreatedAtDesc();

	boolean existsByNumber(String number);

}
