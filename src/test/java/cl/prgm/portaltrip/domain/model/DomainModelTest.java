package cl.prgm.portaltrip.domain.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainModelTest {

	@Test
	void locationHoldsValues() {
		Location location = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", "http://loc/1", List.of(38, 45));

		assertThat(location.id()).isEqualTo(1);
		assertThat(location.name()).isEqualTo("Earth (C-137)");
		assertThat(location.type()).isEqualTo("Planet");
		assertThat(location.dimension()).isEqualTo("Dimension C-137");
		assertThat(location.url()).isEqualTo("http://loc/1");
		assertThat(location.residentIds()).containsExactly(38, 45);
	}

	@Test
	void characterHoldsValues() {
		Character character = new Character(
				1, "Rick Sanchez", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 3, "Citadel of Ricks",
				"http://img", "http://char/1", List.of(1, 2));

		assertThat(character.id()).isEqualTo(1);
		assertThat(character.name()).isEqualTo("Rick Sanchez");
		assertThat(character.status()).isEqualTo("Alive");
		assertThat(character.species()).isEqualTo("Human");
		assertThat(character.type()).isEmpty();
		assertThat(character.gender()).isEqualTo("Male");
		assertThat(character.originId()).isEqualTo(1);
		assertThat(character.originName()).isEqualTo("Earth (C-137)");
		assertThat(character.locationId()).isEqualTo(3);
		assertThat(character.locationName()).isEqualTo("Citadel of Ricks");
		assertThat(character.image()).isEqualTo("http://img");
		assertThat(character.url()).isEqualTo("http://char/1");
		assertThat(character.episodeIds()).containsExactly(1, 2);
	}

	@Test
	void episodeHoldsValues() {
		Episode episode = new Episode(1, "Pilot", "December 2, 2013", "S01E01", "http://ep/1", List.of(1, 2));

		assertThat(episode.id()).isEqualTo(1);
		assertThat(episode.name()).isEqualTo("Pilot");
		assertThat(episode.airDate()).isEqualTo("December 2, 2013");
		assertThat(episode.code()).isEqualTo("S01E01");
		assertThat(episode.url()).isEqualTo("http://ep/1");
		assertThat(episode.characterIds()).containsExactly(1, 2);
	}

}
