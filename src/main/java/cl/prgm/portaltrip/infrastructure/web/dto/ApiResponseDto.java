package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponseDto<T>(int status, String message, T data, Instant timestamp) {

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
