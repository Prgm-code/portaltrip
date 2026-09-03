package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.Instant;

import cl.prgm.portaltrip.application.service.AuthResult;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bearer token and authenticated user")
public record AuthResponseDto(
		String tokenType,
		String accessToken,
		Instant expiresAt,
		UserProfileResponseDto user) {

	public static AuthResponseDto from(AuthResult result) {
		return new AuthResponseDto(
				"Bearer",
				result.accessToken(),
				result.expiresAt(),
				UserProfileResponseDto.from(result.user()));
	}

}
