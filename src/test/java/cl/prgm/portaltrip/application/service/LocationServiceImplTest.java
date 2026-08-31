package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.LocationEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.LocationJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

	@Mock
	private LocationJpaRepository locationJpaRepository;

	@InjectMocks
	private LocationServiceImpl locationService;

	@Test
	void findAllReturnsSummaries() {
		LocationEntity earth = earthEntity();
		when(locationJpaRepository.findAllSummaries()).thenReturn(List.of(earth));

		List<Location> result = locationService.findAll();

		assertThat(result).containsExactly(earth.toDomain());
	}

	@Test
	void findByIdReturnsDomain() {
		LocationEntity earth = earthEntity();
		when(locationJpaRepository.findDetailedById(1)).thenReturn(Optional.of(earth));

		Location result = locationService.findById(1);

		assertThat(result).isEqualTo(earth.toDomain());
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(locationJpaRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> locationService.findById(99))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Location with id '99' not found");
	}

	private static LocationEntity earthEntity() {
		LocationEntity earth = new LocationEntity(1, "Earth (C-137)", "Planet", "Dimension C-137");
		earth.getResidents().add(new CharacterEntity(
				38, "Beth Smith", "Alive", "Human", "", "Female", earth, earth, "img"));
		return earth;
	}

}
