package cl.prgm.portaltrip.infrastructure.security;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import cl.prgm.portaltrip.domain.model.UserAccount;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenServiceTest {

	private static final String SECRET = Base64.getEncoder().encodeToString(
			"portaltrip-test-secret-must-be-at-least-32-bytes".getBytes(StandardCharsets.UTF_8));
	private static final String ISSUER = "https://portaltrip.local";
	private static final String AUDIENCE = "portaltrip-api";

	@Test
	void issuesHs256TokenWithUserClaims() {
		SecurityConfig config = new SecurityConfig();
		JwtTokenService service = new JwtTokenService(
				config.jwtEncoder(SECRET), ISSUER, AUDIENCE, Duration.ofMinutes(30));
		JwtDecoder decoder = config.jwtDecoder(SECRET, ISSUER, AUDIENCE);
		UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000002");
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		UserAccount user = new UserAccount(
				userId, "rick@sanchez.dev", "{bcrypt}hash", "Rick Sanchez",
				"ROLE_USER", new BigDecimal("5000.00"), now, now);

		JwtTokenService.IssuedToken issued = service.issue(user);
		Jwt jwt = decoder.decode(issued.value());

		assertThat(jwt.getSubject()).isEqualTo("rick@sanchez.dev");
		assertThat(jwt.getClaimAsString("user_id")).isEqualTo(userId.toString());
		assertThat(jwt.getClaimAsStringList("roles")).containsExactly("ROLE_USER");
		assertThat(jwt.getIssuer().toString()).isEqualTo(ISSUER);
		assertThat(jwt.getAudience()).containsExactly(AUDIENCE);
		assertThat(issued.expiresAt().getEpochSecond()).isEqualTo(jwt.getExpiresAt().getEpochSecond());
		assertThat(issued.expiresAt()).isAfter(jwt.getIssuedAt());
	}

}
