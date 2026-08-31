package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.application.port.out.EpisodeRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Episode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceImplTest {

	@Mock
	private EpisodeRepository episodeRepository;

	@InjectMocks
	private EpisodeServiceImpl episodeService;

	@Test
	void findAllReturnsSummaries() {
		Episode pilot = new Episode(1, "Pilot", "December 2, 2013", "S01E01", List.of());
		when(episodeRepository.findAllSummaries()).thenReturn(List.of(pilot));

		List<Episode> result = episodeService.findAll();

		assertThat(result).containsExactly(pilot);
	}

	@Test
	void findByIdReturnsDomain() {
		Episode pilot = new Episode(1, "Pilot", "December 2, 2013", "S01E01", List.of(1, 2));
		when(episodeRepository.findDetailedById(1)).thenReturn(Optional.of(pilot));

		Episode result = episodeService.findById(1);

		assertThat(result).isEqualTo(pilot);
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(episodeRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> episodeService.findById(99))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Episode with id '99' not found");
	}

}
