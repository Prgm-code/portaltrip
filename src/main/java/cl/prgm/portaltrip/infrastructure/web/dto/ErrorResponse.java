package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.Instant;

public record ErrorResponse(int code, String message, Instant timestamp) {

	public static ErrorResponse of(int code, String message) {
		return new ErrorResponse(code, message, Instant.now());
	}

}
