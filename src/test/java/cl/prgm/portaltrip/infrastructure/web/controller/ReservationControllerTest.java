package cl.prgm.portaltrip.infrastructure.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.service.CharacterService;
import cl.prgm.portaltrip.application.service.LocationService;
import cl.prgm.portaltrip.application.service.ReservationService;
import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@Import(GlobalExceptionHandler.class)
class ReservationControllerTest {

	private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

	private static final String VALID_BODY = """
			{
				"passengerName": "Rick Sanchez",
				"email": "rick@sanchez.dev",
				"destinationId": 1,
				"travelDate": "2099-01-01",
				"passengers": 2,
				"companionIds": [2],
				"tripType": "express",
				"insurance": true,
				"comments": "stable portal"
			}
			""";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ReservationService reservationService;

	@MockitoBean
	private LocationService locationService;

	@MockitoBean
	private CharacterService characterService;

	@Test
	void createReturns201() throws Exception {
		when(reservationService.create(any(ReservationDraft.class)))
				.thenReturn(reservation(ReservationStatus.CONFIRMED));
		stubCatalogRefs();

		mockMvc.perform(post("/api/v1/reservations")
						.contentType(MediaType.APPLICATION_JSON)
						.content(VALID_BODY))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value(201))
				.andExpect(jsonPath("$.message").value("Reservation created successfully"))
				.andExpect(jsonPath("$.data.id").value(ID.toString()))
				.andExpect(jsonPath("$.data.number").value("PT-2026-000001"))
				.andExpect(jsonPath("$.data.status").value("CONFIRMED"))
				.andExpect(jsonPath("$.data.destination.name").value("Earth (C-137)"))
				.andExpect(jsonPath("$.data.companions[0].name").value("Morty Smith"))
				.andExpect(jsonPath("$.data.companions[0].image").value("img-morty"))
				.andExpect(jsonPath("$.data.tripType").value("express"))
				.andExpect(jsonPath("$.data.quote.total").value(1776.0));
	}

	@Test
	void createRejectsInvalidBody() throws Exception {
		mockMvc.perform(post("/api/v1/reservations")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"passengerName": "", "email": "nope", "destinationId": 1,
								 "travelDate": "2020-01-01", "passengers": 0, "companionIds": [],
								 "tripType": "express", "insurance": false}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void createReturns400WithDomainErrors() throws Exception {
		when(reservationService.create(any(ReservationDraft.class)))
				.thenThrow(new DomainValidationException(List.of("Every selected companion must be alive.")));

		mockMvc.perform(post("/api/v1/reservations")
						.contentType(MediaType.APPLICATION_JSON)
						.content(VALID_BODY))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.data[0]").value("Every selected companion must be alive."));
	}

	@Test
	void findAllReturnsReservations() throws Exception {
		when(reservationService.findAll()).thenReturn(List.of(reservation(ReservationStatus.CONFIRMED)));
		stubCatalogRefs();

		mockMvc.perform(get("/api/v1/reservations"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].number").value("PT-2026-000001"));
	}

	@Test
	void findByIdReturnsReservation() throws Exception {
		when(reservationService.findById(ID)).thenReturn(reservation(ReservationStatus.CONFIRMED));
		stubCatalogRefs();

		mockMvc.perform(get("/api/v1/reservations/{id}", ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.passengerName").value("Rick Sanchez"));
	}

	@Test
	void findByIdReturns404WhenMissing() throws Exception {
		when(reservationService.findById(ID)).thenThrow(new ResourceNotFoundException("Reservation", ID));

		mockMvc.perform(get("/api/v1/reservations/{id}", ID))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	void findByIdRejectsNonUuidId() throws Exception {
		mockMvc.perform(get("/api/v1/reservations/abc"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void startReturnsInProgressReservation() throws Exception {
		when(reservationService.start(ID)).thenReturn(reservation(ReservationStatus.IN_PROGRESS));
		stubCatalogRefs();

		mockMvc.perform(patch("/api/v1/reservations/{id}/start", ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.status").value("IN_PROGRESS"));
	}

	@Test
	void completeReturnsCompletedReservation() throws Exception {
		when(reservationService.complete(ID)).thenReturn(reservation(ReservationStatus.COMPLETED));
		stubCatalogRefs();

		mockMvc.perform(patch("/api/v1/reservations/{id}/complete", ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.status").value("COMPLETED"));
	}

	@Test
	void cancelReturnsCancelledReservation() throws Exception {
		when(reservationService.cancel(ID)).thenReturn(reservation(ReservationStatus.CANCELLED));
		stubCatalogRefs();

		mockMvc.perform(patch("/api/v1/reservations/{id}/cancel", ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.status").value("CANCELLED"));
	}

	@Test
	void cancelReturns409OnIllegalTransition() throws Exception {
		when(reservationService.cancel(ID)).thenThrow(new InvalidReservationStateException(
				"PT-2026-000001", ReservationStatus.COMPLETED, ReservationStatus.CANCELLED));

		mockMvc.perform(patch("/api/v1/reservations/{id}/cancel", ID))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.status").value(409))
				.andExpect(jsonPath("$.message")
						.value("Reservation 'PT-2026-000001' cannot transition from COMPLETED to CANCELLED"));
	}

	private void stubCatalogRefs() {
		Location earth = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(1, 2, 3, 4, 5));
		Character morty = new Character(
				2, "Morty Smith", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 1, "Earth (C-137)", "img-morty", List.of());
		when(locationService.findById(1)).thenReturn(earth);
		when(characterService.findById(2)).thenReturn(morty);
	}

	private static Reservation reservation(ReservationStatus status) {
		Quote quote = new Quote(
				new BigDecimal("1200"),
				BigDecimal.ZERO,
				new BigDecimal("216.00"),
				BigDecimal.ZERO,
				new BigDecimal("360"),
				new BigDecimal("1776.00"),
				RiskLevel.LOW);
		return new Reservation(
				ID,
				"PT-2026-000001",
				status,
				"Rick Sanchez",
				"rick@sanchez.dev",
				1,
				LocalDate.of(2099, 1, 1),
				2,
				List.of(2),
				TripType.EXPRESS,
				true,
				"stable portal",
				quote,
				OffsetDateTime.parse("2026-01-01T10:00:00Z"),
				null,
				null);
	}

}
