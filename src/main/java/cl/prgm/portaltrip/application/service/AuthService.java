package cl.prgm.portaltrip.application.service;

public interface AuthService {

	AuthResult register(String fullName, String email, String password);

	AuthResult login(String email, String password);

}
