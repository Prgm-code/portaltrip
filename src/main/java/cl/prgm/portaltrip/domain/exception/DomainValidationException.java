package cl.prgm.portaltrip.domain.exception;

import java.util.List;

public class DomainValidationException extends RuntimeException {

	private final List<String> errors;

	public DomainValidationException(List<String> errors) {
		super(String.join("; ", errors));
		this.errors = List.copyOf(errors);
	}

	public List<String> errors() {
		return errors;
	}

}
