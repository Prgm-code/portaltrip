package cl.prgm.portaltrip.domain.exception;

public class DuplicateUserException extends RuntimeException {

	public DuplicateUserException() {
		super("An account with that email already exists");
	}

}
