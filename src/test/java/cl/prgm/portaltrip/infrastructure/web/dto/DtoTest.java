package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Episode;
import cl.prgm.portaltrip.domain.model.Location;

import static org.assertj.core.api.Assertions.assertThat;

class DtoTest {

	@Test
	void apiResponseSuccessSetsStatusDataAndTimestamp() {
		ApiResponseDto<String> success = ApiResponseDto.success(HttpStatus.CREATED, "created", "payload");

		assertThat(success.status()).isEqualTo(201);
		assertThat(success.message()).isEqualTo("created");
		assertThat(success.data()).isEqualTo("payload");
		assertThat(success.timestamp()).isBeforeOrEqualTo(Instant.now());
	}

	@Test
	void apiResponseErrorsSupportOptionalData() {
		ApiResponseDto<Void> error = ApiResponseDto.error(HttpStatus.NOT_FOUND, "missing");
		ApiResponseDto<List<String>> errorWithData = ApiResponseDto.error(
				HttpStatus.BAD_REQUEST, "invalid", List.of("first error"));

		assertThat(error.status()).isEqualTo(404);
		assertThat(error.data()).isNull();
		assertThat(error.timestamp()).isBeforeOrEqualTo(Instant.now());
		assertThat(errorWithData.status()).isEqualTo(400);
		assertThat(errorWithData.data()).containsExactly("first error");
	}

	@Test
	void locationResponseMapsAllFields() {
		Location location = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(38));
		LocationResponseDto dto = LocationResponseDto.from(location);

		assertThat(dto.id()).isEqualTo(1);
		assertThat(dto.name()).isEqualTo("Earth (C-137)");
		assertThat(dto.type()).isEqualTo("Planet");
		assertThat(dto.dimension()).isEqualTo("Dimension C-137");
		assertThat(dto.residentIds()).containsExactly(38);
	}

	@Test
	void characterResponseMapsNamedRefs() {
		Character character = new Character(
				1, "Rick Sanchez", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 3, "Citadel of Ricks",
				"img", List.of(1));

		CharacterResponseDto dto = CharacterResponseDto.from(character);

		assertThat(dto.origin()).isEqualTo(new NamedRefDto(1, "Earth (C-137)"));
		assertThat(dto.location()).isEqualTo(new NamedRefDto(3, "Citadel of Ricks"));
		assertThat(dto.episodeIds()).containsExactly(1);
	}

	@Test
	void characterResponseAllowsNullPlaces() {
		Character character = new Character(
				2, "Morty Smith", "Alive", "Human", "", "Male",
				null, null, null, null, "img", List.of());

		CharacterResponseDto dto = CharacterResponseDto.from(character);

		assertThat(dto.origin()).isNull();
		assertThat(dto.location()).isNull();
	}

	@Test
	void characterResponseMapsOriginOnly() {
		Character character = new Character(
				3, "Summer Smith", "Alive", "Human", "", "Female",
				20, "Earth (Replacement Dimension)", null, null, "img", List.of());

		CharacterResponseDto dto = CharacterResponseDto.from(character);

		assertThat(dto.origin().id()).isEqualTo(20);
		assertThat(dto.location()).isNull();
	}

	@Test
	void characterResponseMapsLocationOnly() {
		Character character = new Character(
				8, "Adjudicator Rick", "Dead", "Human", "", "Male",
				null, null, 3, "Citadel of Ricks", "img", List.of());

		CharacterResponseDto dto = CharacterResponseDto.from(character);

		assertThat(dto.origin()).isNull();
		assertThat(dto.location().name()).isEqualTo("Citadel of Ricks");
	}

	@Test
	void episodeResponseMapsAllFields() {
		Episode episode = new Episode(1, "Pilot", "December 2, 2013", "S01E01", List.of(1, 2));
		EpisodeResponseDto dto = EpisodeResponseDto.from(episode);

		assertThat(dto.id()).isEqualTo(1);
		assertThat(dto.name()).isEqualTo("Pilot");
		assertThat(dto.airDate()).isEqualTo("December 2, 2013");
		assertThat(dto.code()).isEqualTo("S01E01");
		assertThat(dto.characterIds()).containsExactly(1, 2);
	}

	@Test
	void healthResponseHoldsValues() {
		HealthResponse response = new HealthResponse("UP", "portaltrip", "now");

		assertThat(response.status()).isEqualTo("UP");
		assertThat(response.application()).isEqualTo("portaltrip");
		assertThat(response.timestamp()).isEqualTo("now");
	}

}
