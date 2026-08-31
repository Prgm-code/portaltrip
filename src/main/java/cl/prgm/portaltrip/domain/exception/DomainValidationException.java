package cl.prgm.portaltrip.domain.exception;

import java.util.List;

public class DomainValidationException extends RuntimeException {

	private final List<String> errors;

	public DomainValidationException(String error) {
		this(List.of(error));
	}

	public DomainValidationException(List<String> errors) {
		super(String.join("; ", errors));
		this.errors = List.copyOf(errors);
	}

	public List<String> errors() {
		return errors;
	}

}
