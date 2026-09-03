package cl.prgm.portaltrip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PortaltripApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void mainStartsWithTestProfile() {
		PortaltripApplication.main(new String[] {
				"--spring.profiles.active=test",
				"--spring.main.web-application-type=none"
		});
	}

	@Test
	void openApiDocumentsControllerResponses() throws Exception {
		mockMvc.perform(get("/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.paths['/api/v1/characters'].get.responses['200']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/characters'].get.responses['200'].content['application/json'].schema").exists())
				.andExpect(jsonPath("$.paths['/api/v1/characters'].get.responses['500'].content['application/json'].schema['$ref']")
						.value("#/components/schemas/ApiResponseDto"))
				.andExpect(jsonPath("$.paths['/api/v1/characters/{id}'].get.responses['404']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/locations'].get.responses['200']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/locations/{id}'].get.responses['400']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/episodes'].get.responses['200']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/episodes/{id}'].get.responses['404']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/quotes'].post.responses['400']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/quotes'].post.requestBody.content['application/json'].schema['$ref']")
						.value("#/components/schemas/QuoteRequestDto"))
				.andExpect(jsonPath("$.paths['/api/v1/reservations'].post.responses['201']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations'].post.parameters[0].name")
						.value("Idempotency-Key"))
				.andExpect(jsonPath("$.paths['/api/v1/reservations'].post.responses['422']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations'].post.responses['201'].content['application/json'].schema").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations'].post.requestBody.content['application/json'].schema['$ref']")
						.value("#/components/schemas/ReservationRequestDto"))
				.andExpect(jsonPath("$.paths['/api/v1/reservations'].get.responses['200']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations/{id}'].get.responses['404']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations/{id}/cancel'].patch.responses['409']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations/{id}/start'].patch.responses['409']").exists())
				.andExpect(jsonPath("$.paths['/api/v1/reservations/{id}/complete'].patch.responses['409']").exists())
				.andExpect(jsonPath("$.paths['/health'].get.responses['200']").exists())
				.andExpect(jsonPath("$.paths['/health'].get.summary").value("Check application health"))
				.andExpect(jsonPath("$.paths['/api/v1/auth/register'].post").exists())
				.andExpect(jsonPath("$.paths['/api/v1/users/me'].get.security[0].bearerAuth").exists())
				.andExpect(jsonPath("$.components.securitySchemes.bearerAuth.scheme").value("bearer"))
				.andExpect(jsonPath("$.components.schemas.QuoteRequestDto.description")
						.value("Input used to calculate a quote without creating a reservation"))
				.andExpect(jsonPath("$.components.schemas.QuoteRequestDto.properties.tripType.enum[0]").value("express"))
				.andExpect(jsonPath("$.components.schemas.ReservationRequestDto.properties.passengerName.example")
						.value("Morty Smith"))
				.andExpect(jsonPath("$.components.schemas.ReservationResponseDto.properties.status.enum[0]")
						.value("CONFIRMED"));
	}

}
