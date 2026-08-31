package cl.prgm.portaltrip.infrastructure.web.exception;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import cl.prgm.portaltrip.domain.exception.InvalidReservationStateException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	void mapsNotFound() {
		ResponseEntity<ApiResponseDto<Void>> response = handler.handleNotFound(
				new ResourceNotFoundException("Location", 7));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody().status()).isEqualTo(404);
		assertThat(response.getBody().message()).contains("Location");
	}

	@Test
	void mapsValidationErrors() throws Exception {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
		bindingResult.addError(new FieldError("request", "name", "is required"));
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
				methodParameter(), bindingResult);

		ResponseEntity<ApiResponseDto<Void>> response = handler.handleValidation(exception);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().message()).isEqualTo("name: is required");
	}

	@Test
	void mapsEmptyValidationErrors() throws Exception {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
				methodParameter(), bindingResult);

		ResponseEntity<ApiResponseDto<Void>> response = handler.handleValidation(exception);

		assertThat(response.getBody().message()).isEqualTo("Validation failed");
	}

	@Test
	void mapsConstraintViolations() {
		ConstraintViolation<?> violation = mock(ConstraintViolation.class);
		Path path = mock(Path.class);
		when(path.toString()).thenReturn("findById.id");
		when(violation.getPropertyPath()).thenReturn(path);
		when(violation.getMessage()).thenReturn("must be greater than or equal to 1");
		@SuppressWarnings("unchecked")
		ConstraintViolation<Object> typed = (ConstraintViolation<Object>) violation;

		ResponseEntity<ApiResponseDto<Void>> response = handler.handleConstraint(
				new ConstraintViolationException(Set.of(typed)));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().message()).contains("must be greater than or equal to 1");
	}

	@Test
	void mapsEmptyConstraintViolations() {
		ResponseEntity<ApiResponseDto<Void>> response = handler.handleConstraint(
				new ConstraintViolationException(Set.of()));

		assertThat(response.getBody().message()).isEqualTo("Validation failed");
	}

	@Test
	void mapsTypeMismatch() {
		MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
				"abc", Integer.class, "id", methodParameter(), new NumberFormatException("abc"));

		ResponseEntity<ApiResponseDto<Void>> response = handler.handleTypeMismatch(exception);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().message()).isEqualTo("Invalid value for parameter 'id'");
	}

	@Test
	void mapsMissingHttpResourceTo404() {
		ResponseEntity<ApiResponseDto<Void>> response = handler.handleNoResource();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody().status()).isEqualTo(404);
		assertThat(response.getBody().message()).isEqualTo("Resource not found");
	}

	@Test
	void mapsDomainValidationTo422WithErrors() {
		DomainValidationException exception = new DomainValidationException(List.of("first error", "second error"));

		ResponseEntity<ApiResponseDto<List<String>>> response = handler.handleDomainValidation(exception);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(response.getBody().status()).isEqualTo(422);
		assertThat(response.getBody().message()).isEqualTo("Validation failed");
		assertThat(response.getBody().data()).containsExactly("first error", "second error");
	}

	@Test
	void mapsInvalidReservationStateTo409() {
		InvalidReservationStateException exception = new InvalidReservationStateException(
				"PT-2026-000001", ReservationStatus.COMPLETED, ReservationStatus.CANCELLED);

		ResponseEntity<ApiResponseDto<Void>> response = handler.handleInvalidState(exception);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(response.getBody().status()).isEqualTo(409);
		assertThat(response.getBody().message()).contains("cannot transition from COMPLETED to CANCELLED");
	}

	@Test
	void mapsUnexpectedExceptionTo500() {
		ResponseEntity<ApiResponseDto<Void>> response = handler.handleUnexpected(
				new IllegalStateException("database unavailable"));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(response.getBody().status()).isEqualTo(500);
		assertThat(response.getBody().message()).isEqualTo("Internal server error");
		assertThat(response.getBody().data()).isNull();
	}

	private static MethodParameter methodParameter() {
		try {
			return new MethodParameter(List.class.getMethod("size"), -1);
		}
		catch (NoSuchMethodException exception) {
			throw new IllegalStateException(exception);
		}
	}

}
