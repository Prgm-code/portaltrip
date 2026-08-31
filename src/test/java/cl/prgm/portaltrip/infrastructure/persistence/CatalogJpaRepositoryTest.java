package cl.prgm.portaltrip.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.EpisodeJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CatalogJpaRepositoryTest {

	@Autowired
	private LocationJpaRepository locationJpaRepository;

	@Autowired
	private CharacterJpaRepository characterJpaRepository;

	@Autowired
	private EpisodeJpaRepository episodeJpaRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void findsLocationsWithAndWithoutResidents() {
		LocationEntity earth = persistCatalog();

		assertThat(locationJpaRepository.findAllSummaries()).hasSize(2);
		assertThat(locationJpaRepository.findDetailedById(earth.getId()))
				.get()
				.extracting(entity -> entity.toDomain().residentIds())
				.isEqualTo(java.util.List.of(1));
		assertThat(locationJpaRepository.findDetailedById(99)).isEmpty();
	}

	@Test
	void findsCharactersWithPlacesAndEpisodes() {
		persistCatalog();

		assertThat(characterJpaRepository.findAllSummaries()).hasSize(1);
		assertThat(characterJpaRepository.findDetailedById(1))
				.get()
				.extracting(entity -> entity.toDomain().episodeIds())
				.isEqualTo(java.util.List.of(1));
		assertThat(characterJpaRepository.findDetailedById(99)).isEmpty();
	}

	@Test
	void findsEpisodesWithCharacters() {
		persistCatalog();

		assertThat(episodeJpaRepository.findAllSummaries()).hasSize(1);
		assertThat(episodeJpaRepository.findDetailedById(1))
				.get()
				.extracting(entity -> entity.toDomain().characterIds())
				.isEqualTo(java.util.List.of(1));
		assertThat(episodeJpaRepository.findDetailedById(99)).isEmpty();
	}

	private LocationEntity persistCatalog() {
		LocationEntity earth = new LocationEntity(1, "Earth (C-137)", "Planet", "Dimension C-137");
		LocationEntity citadel = new LocationEntity(3, "Citadel of Ricks", "Space station", "unknown");
		entityManager.persist(earth);
		entityManager.persist(citadel);

		CharacterEntity rick = new CharacterEntity(
				1, "Rick Sanchez", "Alive", "Human", "", "Male", earth, citadel, "img");
		entityManager.persist(rick);

		EpisodeEntity pilot = new EpisodeEntity(1, "Pilot", "December 2, 2013", "S01E01");
		entityManager.persist(pilot);

		rick.getEpisodes().add(pilot);
		earth.getResidents().add(rick);
		entityManager.flush();
		entityManager.clear();
		return earth;
	}

}
