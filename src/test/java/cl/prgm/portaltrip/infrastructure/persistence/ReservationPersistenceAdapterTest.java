package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import cl.prgm.portaltrip.application.port.out.ReservationRepository;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(ReservationPersistenceAdapter.class)
class ReservationPersistenceAdapterTest {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void savesAndFindsReservationWithCompanions() {
		persistCatalog();
		Reservation reservation = reservation(
				UUID.randomUUID(), "PT-2026-000001", ReservationStatus.CONFIRMED, OffsetDateTime.parse("2026-01-01T10:00:00Z"));

		Reservation saved = reservationRepository.save(reservation);
		entityManager.flush();
		entityManager.clear();

		assertThat(saved.number()).isEqualTo("PT-2026-000001");

		Optional<Reservation> found = reservationRepository.findById(reservation.id());
		assertThat(found).isPresent();
		assertThat(found.get().status()).isEqualTo(ReservationStatus.CONFIRMED);
		assertThat(found.get().passengerName()).isEqualTo("Rick Sanchez");
		assertThat(found.get().email()).isEqualTo("rick@sanchez.dev");
		assertThat(found.get().destinationId()).isEqualTo(1);
		assertThat(found.get().travelDate()).isEqualTo(LocalDate.of(2026, 2, 1));
		assertThat(found.get().passengers()).isEqualTo(2);
		assertThat(found.get().companionIds()).containsExactly(1);
		assertThat(found.get().tripType()).isEqualTo(TripType.EXPLORATION);
		assertThat(found.get().insurance()).isTrue();
		assertThat(found.get().comments()).isEqualTo("portal estable");
		assertThat(found.get().quote().basePrice()).isEqualByComparingTo(new BigDecimal("1200"));
		assertThat(found.get().quote().locationSurcharge()).isEqualByComparingTo(new BigDecimal("300.00"));
		assertThat(found.get().quote().passengerSurcharge()).isEqualByComparingTo(new BigDecimal("216.00"));
		assertThat(found.get().quote().tripSurcharge()).isEqualByComparingTo(new BigDecimal("360.00"));
		assertThat(found.get().quote().insuranceCost()).isEqualByComparingTo(new BigDecimal("380"));
		assertThat(found.get().quote().total()).isEqualByComparingTo(new BigDecimal("2456.00"));
		assertThat(found.get().quote().risk()).isEqualTo(RiskLevel.MEDIUM);
		assertThat(found.get().createdAt()).isEqualTo(OffsetDateTime.parse("2026-01-01T10:00:00Z"));
		assertThat(found.get().startedAt()).isNull();
		assertThat(found.get().completedAt()).isNull();

		assertThat(reservationRepository.findById(UUID.randomUUID())).isEmpty();
	}

	@Test
	void findsAllOrderedByCreatedAtDesc() {
		persistCatalog();
		reservationRepository.save(reservation(
				UUID.randomUUID(), "PT-2026-000001", ReservationStatus.CONFIRMED, OffsetDateTime.parse("2026-01-01T10:00:00Z")));
		reservationRepository.save(reservation(
				UUID.randomUUID(), "PT-2026-000002", ReservationStatus.CONFIRMED, OffsetDateTime.parse("2026-01-02T10:00:00Z")));
		entityManager.flush();
		entityManager.clear();

		List<Reservation> all = reservationRepository.findAll();

		assertThat(all).extracting(Reservation::number)
				.containsExactly("PT-2026-000002", "PT-2026-000001");
	}

	@Test
	void checksNumberExistence() {
		persistCatalog();
		reservationRepository.save(reservation(
				UUID.randomUUID(), "PT-2026-000001", ReservationStatus.CONFIRMED, OffsetDateTime.parse("2026-01-01T10:00:00Z")));
		entityManager.flush();

		assertThat(reservationRepository.existsByNumber("PT-2026-000001")).isTrue();
		assertThat(reservationRepository.existsByNumber("PT-2026-999999")).isFalse();
	}

	@Test
	void updatesStatusAndTimestamps() {
		persistCatalog();
		OffsetDateTime now = OffsetDateTime.parse("2026-01-03T10:00:00Z");
		Reservation reservation = reservation(
				UUID.randomUUID(), "PT-2026-000001", ReservationStatus.CONFIRMED, OffsetDateTime.parse("2026-01-01T10:00:00Z"));
		reservationRepository.save(reservation);
		reservationRepository.save(reservation.start(now));
		entityManager.flush();
		entityManager.clear();

		Reservation started = reservationRepository.findById(reservation.id()).orElseThrow();
		assertThat(started.status()).isEqualTo(ReservationStatus.IN_PROGRESS);
		assertThat(started.startedAt()).isEqualTo(now);

		reservationRepository.save(started.complete(now.plusHours(2)));
		entityManager.flush();
		entityManager.clear();

		Reservation completed = reservationRepository.findById(reservation.id()).orElseThrow();
		assertThat(completed.status()).isEqualTo(ReservationStatus.COMPLETED);
		assertThat(completed.startedAt()).isEqualTo(now);
		assertThat(completed.completedAt()).isEqualTo(now.plusHours(2));
	}

	private void persistCatalog() {
		LocationEntity earth = new LocationEntity(1, "Earth (C-137)", "Planet", "Dimension C-137");
		entityManager.persist(earth);
		CharacterEntity rick = new CharacterEntity(
				1, "Rick Sanchez", "Alive", "Human", "", "Male", earth, earth, "img");
		entityManager.persist(rick);
		entityManager.flush();
		entityManager.clear();
	}

	private static Reservation reservation(UUID id, String number, ReservationStatus status, OffsetDateTime createdAt) {
		Quote quote = new Quote(
				new BigDecimal("1200"),
				new BigDecimal("300.00"),
				new BigDecimal("216.00"),
				new BigDecimal("360.00"),
				new BigDecimal("380"),
				new BigDecimal("2456.00"),
				RiskLevel.MEDIUM);
		return new Reservation(
				id,
				number,
				status,
				"Rick Sanchez",
				"rick@sanchez.dev",
				1,
				LocalDate.of(2026, 2, 1),
				2,
				List.of(1),
				TripType.EXPLORATION,
				true,
				"portal estable",
				quote,
				createdAt,
				null,
				null);
	}

}
