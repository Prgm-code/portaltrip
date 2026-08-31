package cl.prgm.portaltrip.application.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.application.port.out.CharacterRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

	@Mock
	private CharacterRepository characterRepository;

	@InjectMocks
	private CharacterServiceImpl characterService;

	@Test
	void findAllReturnsSummaries() {
		Character rick = rick();
		when(characterRepository.findAllSummaries()).thenReturn(List.of(rick));

		List<Character> result = characterService.findAll();

		assertThat(result).containsExactly(rick);
	}

	@Test
	void findByIdReturnsDomain() {
		Character rick = rick();
		when(characterRepository.findDetailedById(1)).thenReturn(Optional.of(rick));

		Character result = characterService.findById(1);

		assertThat(result).isEqualTo(rick);
	}

	@Test
	void findByIdThrowsWhenMissing() {
		when(characterRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> characterService.findById(99))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Character with id '99' not found");
	}

	private static Character rick() {
		return new Character(
				1, "Rick Sanchez", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 3, "Citadel of Ricks",
				"img", List.of(1));
	}

}
