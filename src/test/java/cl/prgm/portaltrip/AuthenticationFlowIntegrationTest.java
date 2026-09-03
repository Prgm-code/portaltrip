package cl.prgm.portaltrip;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationFlowIntegrationTest {

	private static final String REGISTER_BODY = """
			{
			  "fullName": "Rick Sanchez",
			  "email": "RICK@SANCHEZ.DEV",
			  "password": "portal-gun-123"
			}
			""";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private JwtDecoder jwtDecoder;

	@BeforeEach
	void cleanUsers() {
		userJpaRepository.deleteAll();
	}

	@Test
	void registerLoginAndProfileFlow() throws Exception {
		String registration = mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(REGISTER_BODY))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.data.user.email").value("rick@sanchez.dev"))
				.andExpect(jsonPath("$.data.user.fullName").value("Rick Sanchez"))
				.andExpect(jsonPath("$.data.user.balance").value(5000.0))
				.andReturn().getResponse().getContentAsString();

		String token = JsonPath.read(registration, "$.data.accessToken");
		Jwt jwt = jwtDecoder.decode(token);
		assertThat(jwt.getIssuer().toString()).isEqualTo("https://portaltrip.local");
		assertThat(jwt.getAudience()).containsExactly("portaltrip-api");
		assertThat(jwt.getClaimAsStringList("roles")).containsExactly("ROLE_USER");
		assertThat(userJpaRepository.findByEmail("rick@sanchez.dev").orElseThrow().getPasswordHash())
				.startsWith("{bcrypt}")
				.doesNotContain("portal-gun-123");

		mockMvc.perform(get("/api/v1/users/me").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.email").value("rick@sanchez.dev"))
				.andExpect(jsonPath("$.data.balance").value(5000.0));

		mockMvc.perform(post("/api/v1/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"email":"RICK@SANCHEZ.DEV", "password":"portal-gun-123"}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.accessToken").isNotEmpty());
	}

	@Test
	void rejectsDuplicatesInvalidCredentialsAndInvalidInput() throws Exception {
		mockMvc.perform(post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(REGISTER_BODY)).andExpect(status().isCreated());

		mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(REGISTER_BODY))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("An account with that email already exists"));

		mockMvc.perform(post("/api/v1/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"email":"rick@sanchez.dev", "password":"wrong-password"}
								"""))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Invalid email or password"));

		mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"fullName":"R", "email":"bad", "password":"short"}
								"""))
				.andExpect(status().isBadRequest());
	}

	@Test
	void securityBoundaryReturnsJsonAndAllowsCorsPreflight() throws Exception {
		mockMvc.perform(get("/api/v1/reservations"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Authentication required"));

		mockMvc.perform(get("/api/v1/reservations")
						.header("Authorization", "Bearer malformed"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.status").value(401));

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options("/api/v1/reservations")
						.header("Origin", "http://localhost:4321")
						.header("Access-Control-Request-Method", "POST")
						.header("Access-Control-Request-Headers", "authorization,idempotency-key"))
				.andExpect(status().isOk())
				.andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4321"));
	}

	@Test
	void authenticatedUserReceivesJsonForbiddenForClosedRoute() throws Exception {
		String registration = mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(REGISTER_BODY))
				.andReturn().getResponse().getContentAsString();
		String token = JsonPath.read(registration, "$.data.accessToken");

		mockMvc.perform(post("/api/v1/locations")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.message").value("Access denied"));
	}

}
