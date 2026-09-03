package cl.prgm.portaltrip.application.service;

import java.time.Instant;

import cl.prgm.portaltrip.domain.model.UserAccount;

public record AuthResult(
		String accessToken,
		Instant expiresAt,
		UserAccount user) {
}
