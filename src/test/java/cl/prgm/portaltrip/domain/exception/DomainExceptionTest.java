package cl.prgm.portaltrip.domain.exception;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainExceptionTest {

	@Test
	void resourceNotFoundIncludesResourceAndId() {
		ResourceNotFoundException exception = new ResourceNotFoundException("Character", 99);

		assertThat(exception.getMessage()).isEqualTo("Character with id '99' not found");
	}

	@Test
	void domainValidationCarriesErrorList() {
		DomainValidationException exception = new DomainValidationException(List.of("first error", "second error"));

		assertThat(exception.errors()).containsExactly("first error", "second error");
		assertThat(exception.getMessage()).isEqualTo("first error; second error");
	}

	@Test
	void invalidReservationStateDescribesTransition() {
		InvalidReservationStateException exception = new InvalidReservationStateException(
				"PT-2026-000001",
				cl.prgm.portaltrip.domain.model.ReservationStatus.COMPLETED,
				cl.prgm.portaltrip.domain.model.ReservationStatus.CANCELLED);

		assertThat(exception.getMessage())
				.isEqualTo("Reservation 'PT-2026-000001' cannot transition from COMPLETED to CANCELLED");
	}

}
