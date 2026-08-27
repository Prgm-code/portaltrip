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

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.infrastructure.web.dto.ErrorResponse;
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
		ResponseEntity<ErrorResponse> response = handler.handleNotFound(
				new ResourceNotFoundException("Location", 7));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody().code()).isEqualTo(404);
		assertThat(response.getBody().message()).contains("Location");
	}

	@Test
	void mapsValidationErrors() throws Exception {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
		bindingResult.addError(new FieldError("request", "name", "is required"));
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
				methodParameter(), bindingResult);

		ResponseEntity<ErrorResponse> response = handler.handleValidation(exception);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().message()).isEqualTo("name: is required");
	}

	@Test
	void mapsEmptyValidationErrors() throws Exception {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
				methodParameter(), bindingResult);

		ResponseEntity<ErrorResponse> response = handler.handleValidation(exception);

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

		ResponseEntity<ErrorResponse> response = handler.handleConstraint(
				new ConstraintViolationException(Set.of(typed)));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().message()).contains("must be greater than or equal to 1");
	}

	@Test
	void mapsEmptyConstraintViolations() {
		ResponseEntity<ErrorResponse> response = handler.handleConstraint(
				new ConstraintViolationException(Set.of()));

		assertThat(response.getBody().message()).isEqualTo("Validation failed");
	}

	@Test
	void mapsTypeMismatch() {
		MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
				"abc", Integer.class, "id", methodParameter(), new NumberFormatException("abc"));

		ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(exception);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().message()).isEqualTo("Invalid value for parameter 'id'");
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
