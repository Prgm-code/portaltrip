package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.EpisodeEntity;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

	@Mock
	private CharacterJpaRepository characterJpaRepository;

	@InjectMocks
	private CharacterServiceImpl characterService;

	@Test
	void findAllReturnsSummariesWithoutEpisodes() {
		CharacterEntity rick = rick();
		rick.getEpisodes().add(pilot());
		when(characterJpaRepository.findAllSummaries()).thenReturn(List.of(rick));

		List<Character> result = characterService.findAll();

		assertThat(result).hasSize(1);
		assertThat(result.getFirst().name()).isEqualTo("Rick Sanchez");
		assertThat(result.getFirst().episodeIds()).isEmpty();
		assertThat(result.getFirst().originName()).isEqualTo("Earth (C-137)");
	}

	@Test
	void findByIdReturnsDomainWithEpisodes() {
		CharacterEntity rick = rick();
		rick.getEpisodes().add(pilot());
		when(characterJpaRepository.findDetailedById(1)).thenReturn(Optional.of(rick));

		Character result = characterService.findById(1);

		assertThat(result.episodeIds()).containsExactly(1);
		assertThat(result.locationId()).isEqualTo(3);
	}

	@Test
	void findByIdMapsNullPlaces() {
		CharacterEntity unknown = new CharacterEntity(
				2, "Morty Smith", "Alive", "Human", "", "Male", null, null, "img", "url");
		when(characterJpaRepository.findDetailedById(2)).thenReturn(Optional.of(unknown));

		Character result = characterService.findById(2);

		assertThat(result.originId()).isNull();
		assertThat(result.locationId()).isNull();
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(characterJpaRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> characterService.findById(99))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Character with id '99' not found");
	}

	private static CharacterEntity rick() {
		LocationEntity earth = new LocationEntity(1, "Earth (C-137)", "Planet", "Dimension C-137", "http://loc/1");
		LocationEntity citadel = new LocationEntity(3, "Citadel of Ricks", "Space station", "unknown", "http://loc/3");
		return new CharacterEntity(1, "Rick Sanchez", "Alive", "Human", "", "Male", earth, citadel, "img", "url");
	}

	private static EpisodeEntity pilot() {
		return new EpisodeEntity(1, "Pilot", "December 2, 2013", "S01E01", "http://ep/1");
	}

}
