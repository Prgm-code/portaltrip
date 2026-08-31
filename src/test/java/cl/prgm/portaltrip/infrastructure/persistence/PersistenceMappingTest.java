package cl.prgm.portaltrip.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceMappingTest {

	@Test
	void locationGetters() {
		LocationEntity earth = new LocationEntity(1, "Earth (C-137)", "Planet", "Dimension C-137");

		assertThat(earth.getId()).isEqualTo(1);
		assertThat(earth.getName()).isEqualTo("Earth (C-137)");
		assertThat(earth.getType()).isEqualTo("Planet");
		assertThat(earth.getDimension()).isEqualTo("Dimension C-137");
		assertThat(earth.getResidents()).isEmpty();
		assertThat(earth.toDomain().residentIds()).isEmpty();
	}

	@Test
	void characterGettersCoverNullAndPresentPlaces() {
		LocationEntity earth = new LocationEntity(1, "Earth (C-137)", "Planet", "Dimension C-137");
		LocationEntity citadel = new LocationEntity(3, "Citadel of Ricks", "Space station", "unknown");
		CharacterEntity rick = new CharacterEntity(
				1, "Rick Sanchez", "Alive", "Human", "", "Male", earth, citadel, "img");

		assertThat(rick.getId()).isEqualTo(1);
		assertThat(rick.getName()).isEqualTo("Rick Sanchez");
		assertThat(rick.getStatus()).isEqualTo("Alive");
		assertThat(rick.getSpecies()).isEqualTo("Human");
		assertThat(rick.getType()).isEmpty();
		assertThat(rick.getGender()).isEqualTo("Male");
		assertThat(rick.getOrigin().getName()).isEqualTo("Earth (C-137)");
		assertThat(rick.getLocation().getName()).isEqualTo("Citadel of Ricks");
		assertThat(rick.getImage()).isEqualTo("img");
		assertThat(rick.getEpisodes()).isEmpty();
		assertThat(rick.toSummary().episodeIds()).isEmpty();
	}

	@Test
	void characterMappingHandlesNullPlaces() {
		CharacterEntity morty = new CharacterEntity(
				2, "Morty Smith", "Alive", "Human", "", "Male", null, null, "img");

		assertThat(morty.toSummary().originId()).isNull();
		assertThat(morty.toSummary().originName()).isNull();
		assertThat(morty.toSummary().locationId()).isNull();
		assertThat(morty.toDomain().locationName()).isNull();
	}

	@Test
	void episodeGetters() {
		EpisodeEntity pilot = new EpisodeEntity(1, "Pilot", "December 2, 2013", "S01E01");

		assertThat(pilot.getId()).isEqualTo(1);
		assertThat(pilot.getName()).isEqualTo("Pilot");
		assertThat(pilot.getAirDate()).isEqualTo("December 2, 2013");
		assertThat(pilot.getCode()).isEqualTo("S01E01");
		assertThat(pilot.getCharacters()).isEmpty();
		assertThat(pilot.toDomain().characterIds()).isEmpty();
	}

}
