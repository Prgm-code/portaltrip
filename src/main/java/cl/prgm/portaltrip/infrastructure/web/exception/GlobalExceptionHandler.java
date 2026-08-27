package cl.prgm.portaltrip.infrastructure.web.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.infrastructure.web.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining("; "));
		if (message.isBlank()) {
			message = "Validation failed";
		}
		return ResponseEntity.badRequest()
				.body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), message));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException exception) {
		String message = exception.getConstraintViolations().stream()
				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
				.collect(Collectors.joining("; "));
		if (message.isBlank()) {
			message = "Validation failed";
		}
		return ResponseEntity.badRequest()
				.body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), message));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
		return ResponseEntity.badRequest()
				.body(ErrorResponse.of(
						HttpStatus.BAD_REQUEST.value(),
						"Invalid value for parameter '" + exception.getName() + "'"));
	}

}
