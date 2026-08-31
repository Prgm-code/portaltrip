package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

	@Mock
	private LocationRepository locationRepository;

	@InjectMocks
	private LocationServiceImpl locationService;

	@Test
	void findAllReturnsSummaries() {
		Location earth = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of());
		when(locationRepository.findAllSummaries()).thenReturn(List.of(earth));

		List<Location> result = locationService.findAll();

		assertThat(result).containsExactly(earth);
	}

	@Test
	void findByIdReturnsDomain() {
		Location earth = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(38));
		when(locationRepository.findDetailedById(1)).thenReturn(Optional.of(earth));

		Location result = locationService.findById(1);

		assertThat(result).isEqualTo(earth);
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(locationRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> locationService.findById(99))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Location with id '99' not found");
	}

}
