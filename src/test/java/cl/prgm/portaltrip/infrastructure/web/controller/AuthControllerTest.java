package cl.prgm.portaltrip.infrastructure.web.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.service.AuthResult;
import cl.prgm.portaltrip.application.service.AuthService;
import cl.prgm.portaltrip.domain.exception.DuplicateUserException;
import cl.prgm.portaltrip.domain.exception.InvalidCredentialsException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {

	private static final String REGISTER_BODY = """
			{
			  "fullName": "Rick Sanchez",
			  "email": "rick@sanchez.dev",
			  "password": "portal-gun-123"
			}
			""";

	private static final String LOGIN_BODY = """
			{
			  "email": "rick@sanchez.dev",
			  "password": "portal-gun-123"
			}
			""";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthService authService;

	@Test
	void registerReturns201WithTokenAndProfile() throws Exception {
		when(authService.register("Rick Sanchez", "rick@sanchez.dev", "portal-gun-123"))
				.thenReturn(authResult());

		mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(REGISTER_BODY))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value(201))
				.andExpect(jsonPath("$.message").value("Account created successfully"))
				.andExpect(jsonPath("$.data.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.data.accessToken").value("token"))
				.andExpect(jsonPath("$.data.user.email").value("rick@sanchez.dev"))
				.andExpect(jsonPath("$.data.user.fullName").value("Rick Sanchez"))
				.andExpect(jsonPath("$.data.user.balance").value(5000.0));
	}

	@Test
	void loginReturns200WithToken() throws Exception {
		when(authService.login("rick@sanchez.dev", "portal-gun-123")).thenReturn(authResult());

		mockMvc.perform(post("/api/v1/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(LOGIN_BODY))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.message").value("Login successful"))
				.andExpect(jsonPath("$.data.accessToken").value("token"))
				.andExpect(jsonPath("$.data.user.id").value("00000000-0000-0000-0000-000000000002"));
	}

	@Test
	void registerRejectsInvalidBody() throws Exception {
		mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"fullName":"R","email":"bad","password":"short"}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void registerReturns409WhenEmailExists() throws Exception {
		when(authService.register(anyString(), anyString(), anyString()))
				.thenThrow(new DuplicateUserException());

		mockMvc.perform(post("/api/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(REGISTER_BODY))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("An account with that email already exists"));
	}

	@Test
	void loginReturns401ForInvalidCredentials() throws Exception {
		when(authService.login(anyString(), anyString())).thenThrow(new InvalidCredentialsException());

		mockMvc.perform(post("/api/v1/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(LOGIN_BODY))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Invalid email or password"));
	}

	private static AuthResult authResult() {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		UserAccount user = new UserAccount(
				UUID.fromString("00000000-0000-0000-0000-000000000002"),
				"rick@sanchez.dev",
				"{bcrypt}hash",
				"Rick Sanchez",
				"ROLE_USER",
				new BigDecimal("5000.00"),
				now,
				now);
		return new AuthResult("token", Instant.parse("2026-01-01T10:30:00Z"), user);
	}

}
