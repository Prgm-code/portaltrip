package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.port.in.LocationService;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
@Import(GlobalExceptionHandler.class)
class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private LocationService locationService;

	@Test
	void findAllReturnsLocations() throws Exception {
		when(locationService.findAll()).thenReturn(List.of(
				new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of())));

		mockMvc.perform(get("/api/v1/locations"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].id").value(1))
				.andExpect(jsonPath("$.data[0].name").value("Earth (C-137)"));
	}

	@Test
	void findByIdReturnsLocation() throws Exception {
		when(locationService.findById(1)).thenReturn(
				new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(38)));

		mockMvc.perform(get("/api/v1/locations/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.residentIds[0]").value(38));
	}

	@Test
	void findByIdReturns404WhenMissing() throws Exception {
		when(locationService.findById(99)).thenThrow(new ResourceNotFoundException("Location", 99));

		mockMvc.perform(get("/api/v1/locations/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.data").doesNotExist())
				.andExpect(jsonPath("$.message").value("Location with id '99' not found"));
	}

	@Test
	void findByIdRejectsNonNumericId() throws Exception {
		mockMvc.perform(get("/api/v1/locations/abc"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

}
