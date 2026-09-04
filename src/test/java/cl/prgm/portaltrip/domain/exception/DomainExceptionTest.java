package cl.prgm.portaltrip.domain.exception;

import java.math.BigDecimal;
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

	@Test
	void duplicateUserHidesExistingEmail() {
		assertThat(new DuplicateUserException().getMessage())
				.isEqualTo("An account with that email already exists");
	}

	@Test
	void invalidCredentialsHidesWhichFieldFailed() {
		assertThat(new InvalidCredentialsException().getMessage())
				.isEqualTo("Invalid email or password");
	}

	@Test
	void idempotencyConflictExplainsKeyReuse() {
		assertThat(new IdempotencyConflictException().getMessage())
				.isEqualTo("Idempotency key was already used for a different reservation");
	}

	@Test
	void insufficientBalanceCarriesRequiredAndCurrentAmounts() {
		InsufficientBalanceException exception = new InsufficientBalanceException(
				new BigDecimal("2000.00"), new BigDecimal("100.00"));

		assertThat(exception.getMessage()).isEqualTo("Insufficient balance");
		assertThat(exception.requiredBalance()).isEqualByComparingTo("2000.00");
		assertThat(exception.currentBalance()).isEqualByComparingTo("100.00");
	}

	@Test
	void portalStipendCooldownIsExplicit() {
		assertThat(new PortalStipendCooldownException().getMessage())
				.isEqualTo("Portal stipend cooldown");
	}

}
