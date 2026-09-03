package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.repository.ReservationJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ReservationJpaRepositoryTest {

	@Autowired
	private ReservationJpaRepository reservationJpaRepository;
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private EntityManager entityManager;

	@Test
	void scopesReservationQueriesAndLocksUser() {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		UserEntity rick = UserEntity.fromDomain(user("rick@example.com", now));
		UserEntity morty = UserEntity.fromDomain(user("morty@example.com", now));
		entityManager.persist(rick);
		entityManager.persist(morty);
		LocationEntity earth = new LocationEntity(1, "Earth", "Planet", "Dimension C-137");
		entityManager.persist(earth);
		CharacterEntity companion = new CharacterEntity(
				1, "Birdperson", "Alive", "Alien", "", "Male", earth, earth, "img");
		entityManager.persist(companion);

		Reservation rickReservation = reservation(rick.getId(), UUID.randomUUID(), now);
		ReservationEntity entity = ReservationEntity.fromDomain(
				rickReservation, rick, earth, Set.of(companion));
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.clear();

		assertThat(reservationJpaRepository.findDetailedByIdAndUserId(
				rickReservation.id(), rick.getId())).get().extracting(ReservationEntity::toDomain)
				.isEqualTo(rickReservation);
		assertThat(reservationJpaRepository.findDetailedByIdAndUserId(
				rickReservation.id(), morty.getId())).isEmpty();
		assertThat(reservationJpaRepository.findAllDetailedByUserId(rick.getId())).hasSize(1);
		assertThat(reservationJpaRepository.findAllDetailedByUserId(morty.getId())).isEmpty();
		assertThat(reservationJpaRepository.findByUserIdAndIdempotencyKey(
				rick.getId(), rickReservation.idempotencyKey())).isPresent();
		assertThat(reservationJpaRepository.existsByNumber(rickReservation.number())).isTrue();
		assertThat(userJpaRepository.findByIdForUpdate(rick.getId())).isPresent();
	}

	private static UserAccount user(String email, OffsetDateTime now) {
		return new UserAccount(
				UUID.randomUUID(), email, "{bcrypt}hash", "Traveler", "ROLE_USER",
				new BigDecimal("5000.00"), now, now);
	}

	private static Reservation reservation(UUID userId, UUID key, OffsetDateTime now) {
		Quote quote = new Quote(
				new BigDecimal("1200.00"), new BigDecimal("0.00"), new BigDecimal("0.00"),
				new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("1200.00"), RiskLevel.LOW);
		return new Reservation(
				UUID.randomUUID(), userId, key, "PT-2026-000001", ReservationStatus.CONFIRMED,
				"Rick Sanchez", "rick@example.com", 1, LocalDate.of(2099, 1, 1), 1,
				List.of(1), TripType.EXPRESS, false, "", quote, now, null, null);
	}

}
