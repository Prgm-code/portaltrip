package cl.prgm.portaltrip.infrastructure.security;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import cl.prgm.portaltrip.domain.model.UserAccount;

@Service
public class JwtTokenService {

	private final JwtEncoder jwtEncoder;
	private final String issuer;
	private final String audience;
	private final Duration ttl;

	public JwtTokenService(
			JwtEncoder jwtEncoder,
			@Value("${app.security.jwt.issuer}") String issuer,
			@Value("${app.security.jwt.audience}") String audience,
			@Value("${app.security.jwt.ttl}") Duration ttl) {
		this.jwtEncoder = jwtEncoder;
		this.issuer = issuer;
		this.audience = audience;
		this.ttl = ttl;
	}

	public IssuedToken issue(UserAccount user) {
		Instant issuedAt = Instant.now();
		Instant expiresAt = issuedAt.plus(ttl);
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(issuer)
				.audience(List.of(audience))
				.issuedAt(issuedAt)
				.expiresAt(expiresAt)
				.subject(user.email())
				.claim("user_id", user.id().toString())
				.claim("roles", List.of(user.role()))
				.build();
		String value = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		return new IssuedToken(value, expiresAt);
	}

	public record IssuedToken(String value, Instant expiresAt) {
	}

}
