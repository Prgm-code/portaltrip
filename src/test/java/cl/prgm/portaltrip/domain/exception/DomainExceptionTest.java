package cl.prgm.portaltrip.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainExceptionTest {

	@Test
	void resourceNotFoundIncludesResourceAndId() {
		ResourceNotFoundException exception = new ResourceNotFoundException("Character", 99);

		assertThat(exception.getMessage()).isEqualTo("Character with id '99' not found");
	}

}
