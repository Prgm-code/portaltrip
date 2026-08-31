package cl.prgm.portaltrip.infrastructure.web.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.port.in.QuoteQuery;
import cl.prgm.portaltrip.application.port.in.QuoteService;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuoteController.class)
@Import(GlobalExceptionHandler.class)
class QuoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private QuoteService quoteService;

	@Test
	void quoteReturnsCalculation() throws Exception {
		Quote quote = new Quote(
				new BigDecimal("1200"),
				BigDecimal.ZERO,
				new BigDecimal("216.00"),
				new BigDecimal("360.00"),
				new BigDecimal("380"),
				new BigDecimal("2156.00"),
				RiskLevel.MEDIUM);
		when(quoteService.quote(new QuoteQuery(1, 2, TripType.EXPLORATION, true))).thenReturn(quote);

		mockMvc.perform(post("/api/v1/quotes")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"destinationId": 1, "passengers": 2, "tripType": "exploration", "insurance": true}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.message").value("Quote calculated successfully"))
				.andExpect(jsonPath("$.data.basePrice").value(1200))
				.andExpect(jsonPath("$.data.passengerSurcharge").value(216.0))
				.andExpect(jsonPath("$.data.tripSurcharge").value(360.0))
				.andExpect(jsonPath("$.data.insuranceCost").value(380))
				.andExpect(jsonPath("$.data.total").value(2156.0))
				.andExpect(jsonPath("$.data.risk").value("MEDIUM"));

		verify(quoteService).quote(new QuoteQuery(1, 2, TripType.EXPLORATION, true));
	}

	@Test
	void quoteRejectsInvalidRequest() throws Exception {
		mockMvc.perform(post("/api/v1/quotes")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"destinationId": 1, "passengers": 0, "tripType": "warp", "insurance": false}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void quoteReturns404WhenDestinationMissing() throws Exception {
		when(quoteService.quote(new QuoteQuery(99, 1, TripType.EXPRESS, false)))
				.thenThrow(new ResourceNotFoundException("Location", 99));

		mockMvc.perform(post("/api/v1/quotes")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"destinationId": 99, "passengers": 1, "tripType": "express", "insurance": false}
								"""))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Location with id '99' not found"));
	}

}
