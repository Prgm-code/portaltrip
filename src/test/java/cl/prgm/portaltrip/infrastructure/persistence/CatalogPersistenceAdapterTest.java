package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import cl.prgm.portaltrip.application.port.out.CharacterRepository;
import cl.prgm.portaltrip.application.port.out.EpisodeRepository;
import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Episode;
import cl.prgm.portaltrip.domain.model.Location;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({LocationPersistenceAdapter.class, CharacterPersistenceAdapter.class, EpisodePersistenceAdapter.class})
class CatalogPersistenceAdapterTest {

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private EpisodeRepository episodeRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void mapsLocationsThroughPort() {
		persistCatalog();

		assertThat(locationRepository.findAllSummaries()).hasSize(2);
		assertThat(locationRepository.findAllSummaries().getFirst().residentIds()).containsExactly(1);

		Optional<Location> earth = locationRepository.findDetailedById(1);
		assertThat(earth).isPresent();
		assertThat(earth.get().name()).isEqualTo("Earth (C-137)");
		assertThat(earth.get().residentIds()).containsExactly(1);
		assertThat(locationRepository.findDetailedById(99)).isEmpty();
	}

	@Test
	void mapsCharactersThroughPort() {
		persistCatalog();

		assertThat(characterRepository.findAllSummaries()).hasSize(1);

		Optional<Character> rick = characterRepository.findDetailedById(1);
		assertThat(rick).isPresent();
		assertThat(rick.get().episodeIds()).containsExactly(1);
		assertThat(rick.get().originName()).isEqualTo("Earth (C-137)");
		assertThat(characterRepository.findDetailedById(99)).isEmpty();
	}

	@Test
	void findsCharactersByIds() {
		persistCatalog();

		List<Character> companions = characterRepository.findAllByIds(List.of(1, 99));

		assertThat(companions).hasSize(1);
		assertThat(companions.getFirst().status()).isEqualTo("Alive");
		assertThat(companions.getFirst().episodeIds()).isEmpty();
	}

	@Test
	void mapsEpisodesThroughPort() {
		persistCatalog();

		assertThat(episodeRepository.findAllSummaries()).hasSize(1);
		assertThat(episodeRepository.findAllSummaries().getFirst().characterIds()).containsExactly(1);

		Optional<Episode> pilot = episodeRepository.findDetailedById(1);
		assertThat(pilot).isPresent();
		assertThat(pilot.get().characterIds()).containsExactly(1);
		assertThat(episodeRepository.findDetailedById(99)).isEmpty();
	}

	private void persistCatalog() {
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
	}

}
