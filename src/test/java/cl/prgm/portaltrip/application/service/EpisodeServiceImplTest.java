package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Episode;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.EpisodeEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.EpisodeJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceImplTest {

	@Mock
	private EpisodeJpaRepository episodeJpaRepository;

	@InjectMocks
	private EpisodeServiceImpl episodeService;

	@Test
	void findAllReturnsSummaries() {
		EpisodeEntity pilot = pilotEntity();
		when(episodeJpaRepository.findAllSummaries()).thenReturn(List.of(pilot));

		List<Episode> result = episodeService.findAll();

		assertThat(result).containsExactly(pilot.toDomain());
	}

	@Test
	void findByIdReturnsDomain() {
		EpisodeEntity pilot = pilotEntity();
		when(episodeJpaRepository.findDetailedById(1)).thenReturn(Optional.of(pilot));

		Episode result = episodeService.findById(1);

		assertThat(result).isEqualTo(pilot.toDomain());
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(episodeJpaRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> episodeService.findById(99))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Episode with id '99' not found");
	}

	private static EpisodeEntity pilotEntity() {
		EpisodeEntity pilot = new EpisodeEntity(1, "Pilot", "December 2, 2013", "S01E01");
		pilot.getCharacters().add(new CharacterEntity(
				1, "Rick Sanchez", "Alive", "Human", "", "Male", null, null, "img"));
		return pilot;
	}

}
