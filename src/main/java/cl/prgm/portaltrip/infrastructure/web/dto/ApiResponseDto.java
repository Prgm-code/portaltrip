package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Unified response envelope returned by every API endpoint")
public record ApiResponseDto<T>(
		@Schema(description = "HTTP status code", example = "200", requiredMode = Schema.RequiredMode.REQUIRED)
		int status,
		@Schema(description = "Human-readable result", example = "Quote calculated successfully", requiredMode = Schema.RequiredMode.REQUIRED)
		String message,
		@Schema(description = "Response payload; omitted when an error has no structured details")
		T data,
		@Schema(description = "UTC response timestamp", example = "2026-08-31T22:30:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
		Instant timestamp) {

	public static <T> ApiResponseDto<T> success(HttpStatus status, String message, T data) {
		return new ApiResponseDto<>(status.value(), message, data, Instant.now());
	}

	public static ApiResponseDto<Void> error(HttpStatus status, String message) {
		return new ApiResponseDto<>(status.value(), message, null, Instant.now());
	}

	public static <T> ApiResponseDto<T> error(HttpStatus status, String message, T data) {
		return new ApiResponseDto<>(status.value(), message, data, Instant.now());
	}

}
