package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.service.CharacterService;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
@Import(GlobalExceptionHandler.class)
class CharacterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CharacterService characterService;

	@Test
	void findAllReturnsCharacters() throws Exception {
		when(characterService.findAll()).thenReturn(List.of(rick()));

		mockMvc.perform(get("/api/v1/characters"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Rick Sanchez"))
				.andExpect(jsonPath("$[0].origin.name").value("Earth (C-137)"));
	}

	@Test
	void findByIdReturnsCharacter() throws Exception {
		when(characterService.findById(1)).thenReturn(rick());

		mockMvc.perform(get("/api/v1/characters/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.location.id").value(3));
	}

	@Test
	void findByIdReturns404WhenMissing() throws Exception {
		when(characterService.findById(99)).thenThrow(new ResourceNotFoundException("Character", 99));

		mockMvc.perform(get("/api/v1/characters/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Character with id '99' not found"));
	}

	private static Character rick() {
		return new Character(
				1, "Rick Sanchez", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 3, "Citadel of Ricks",
				"img", "url", List.of());
	}

}
