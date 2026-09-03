package cl.prgm.portaltrip.infrastructure.web.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.prgm.portaltrip.application.service.UserService;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.web.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

	private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	void meReturnsAuthenticatedProfile() throws Exception {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		when(userService.findById(USER_ID)).thenReturn(new UserAccount(
				USER_ID,
				"rick@sanchez.dev",
				"{bcrypt}hash",
				"Rick Sanchez",
				"ROLE_USER",
				new BigDecimal("5000.00"),
				now,
				now));

		mockMvc.perform(get("/api/v1/users/me").with(userJwt()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.message").value("Profile retrieved successfully"))
				.andExpect(jsonPath("$.data.id").value(USER_ID.toString()))
				.andExpect(jsonPath("$.data.email").value("rick@sanchez.dev"))
				.andExpect(jsonPath("$.data.fullName").value("Rick Sanchez"))
				.andExpect(jsonPath("$.data.balance").value(5000.0));
	}

	@Test
	void meReturns404WhenUserIsMissing() throws Exception {
		when(userService.findById(USER_ID)).thenThrow(new ResourceNotFoundException("User", USER_ID));

		mockMvc.perform(get("/api/v1/users/me").with(userJwt()))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	private static org.springframework.test.web.servlet.request.RequestPostProcessor userJwt() {
		Instant now = Instant.now();
		Jwt jwt = Jwt.withTokenValue("test-token")
				.header("alg", "none")
				.subject("rick@sanchez.dev")
				.claim("user_id", USER_ID.toString())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(60))
				.build();
		JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
		return request -> {
			request.setUserPrincipal(authentication);
			return request;
		};
	}

}
