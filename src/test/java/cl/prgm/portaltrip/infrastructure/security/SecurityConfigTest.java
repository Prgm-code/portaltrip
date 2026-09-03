package cl.prgm.portaltrip.infrastructure.security;

import java.time.Instant;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecurityConfigTest {

	private static final String SECRET = Base64.getEncoder().encodeToString(
			"portaltrip-test-secret-must-be-at-least-32-bytes".getBytes(java.nio.charset.StandardCharsets.UTF_8));

	@Test
	void rejectsShortSecret() {
		SecurityConfig config = new SecurityConfig();
		String shortSecret = Base64.getEncoder().encodeToString("short".getBytes(java.nio.charset.StandardCharsets.UTF_8));

		assertThatThrownBy(() -> config.jwtEncoder(shortSecret))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("JWT_SECRET_BASE64 must decode to at least 32 bytes");
	}

	@Test
	void decoderRejectsWrongAndMissingAudience() {
		SecurityConfig config = new SecurityConfig();
		JwtEncoder encoder = config.jwtEncoder(SECRET);
		var decoder = config.jwtDecoder(SECRET, "https://portaltrip.local", "portaltrip-api");

		assertThatThrownBy(() -> decoder.decode(token(encoder, List.of("another-api"))))
				.isInstanceOf(JwtValidationException.class);
		assertThatThrownBy(() -> decoder.decode(token(encoder, null)))
				.isInstanceOf(JwtValidationException.class);
	}

	@Test
	void audienceCheckRejectsNullClaim() {
		assertThat(SecurityConfig.hasAudience(null, "portaltrip-api")).isFalse();
	}

	@Test
	void authenticationConverterUsesRolesWithoutPrefix() {
		SecurityConfig config = new SecurityConfig();
		Instant now = Instant.now();
		var jwt = org.springframework.security.oauth2.jwt.Jwt.withTokenValue("token")
				.header("alg", "HS256")
				.subject("rick@sanchez.dev")
				.claim("roles", List.of("ROLE_USER"))
				.issuedAt(now)
				.expiresAt(now.plusSeconds(60))
				.build();

		var authentication = config.jwtAuthenticationConverter().convert(jwt);

		assertThat(authentication.getAuthorities()).extracting("authority").contains("ROLE_USER");
	}

	private static String token(JwtEncoder encoder, List<String> audience) {
		Instant now = Instant.now();
		JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
				.issuer("https://portaltrip.local")
				.subject("rick@sanchez.dev")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(60));
		if (audience != null) {
			claims.audience(audience);
		}
		return encoder.encode(JwtEncoderParameters.from(claims.build())).getTokenValue();
	}

}
