package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.service.EpisodeService;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Episode;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EpisodeController.class)
@Import(GlobalExceptionHandler.class)
class EpisodeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EpisodeService episodeService;

	@Test
	void findAllReturnsEpisodes() throws Exception {
		when(episodeService.findAll()).thenReturn(List.of(
				new Episode(1, "Pilot", "December 2, 2013", "S01E01", List.of())));

		mockMvc.perform(get("/api/v1/episodes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].code").value("S01E01"));
	}

	@Test
	void findByIdReturnsEpisode() throws Exception {
		when(episodeService.findById(1)).thenReturn(
				new Episode(1, "Pilot", "December 2, 2013", "S01E01", List.of(1, 2)));

		mockMvc.perform(get("/api/v1/episodes/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.characterIds[1]").value(2));
	}

	@Test
	void findByIdReturns404WhenMissing() throws Exception {
		when(episodeService.findById(99)).thenThrow(new ResourceNotFoundException("Episode", 99));

		mockMvc.perform(get("/api/v1/episodes/99"))
				.andExpect(status().isNotFound());
	}

}
