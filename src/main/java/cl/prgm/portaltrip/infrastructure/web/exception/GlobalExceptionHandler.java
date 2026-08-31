package cl.prgm.portaltrip.infrastructure.web.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleNotFound(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponseDto.error(HttpStatus.NOT_FOUND, exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleValidation(MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining("; "));
		if (message.isBlank()) {
			message = "Validation failed";
		}
		return ResponseEntity.badRequest()
				.body(ApiResponseDto.error(HttpStatus.BAD_REQUEST, message));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleConstraint(ConstraintViolationException exception) {
		String message = exception.getConstraintViolations().stream()
				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
				.collect(Collectors.joining("; "));
		if (message.isBlank()) {
			message = "Validation failed";
		}
		return ResponseEntity.badRequest()
				.body(ApiResponseDto.error(HttpStatus.BAD_REQUEST, message));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
		return ResponseEntity.badRequest()
				.body(ApiResponseDto.error(
						HttpStatus.BAD_REQUEST,
						"Invalid value for parameter '" + exception.getName() + "'"));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleNoResource() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponseDto.error(HttpStatus.NOT_FOUND, "Resource not found"));
	}

	@ExceptionHandler(DomainValidationException.class)
	public ResponseEntity<ApiResponseDto<List<String>>> handleDomainValidation(DomainValidationException exception) {
		return ResponseEntity.unprocessableEntity()
				.body(ApiResponseDto.error(
						HttpStatus.UNPROCESSABLE_ENTITY,
						"Validation failed",
						exception.errors()));
	}

	@ExceptionHandler(InvalidReservationStateException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleInvalidState(InvalidReservationStateException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiResponseDto.error(HttpStatus.CONFLICT, exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto<Void>> handleUnexpected(Exception exception) {
		LOGGER.error("Unhandled server error", exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponseDto.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
	}

}
