package cl.prgm.portaltrip.application.service;

import java.util.UUID;

import cl.prgm.portaltrip.domain.model.UserAccount;

public interface UserService {

	UserAccount findById(UUID id);

}
