package cl.prgm.portaltrip.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserJpaRepository userJpaRepository;

	public UserServiceImpl(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserAccount findById(UUID id) {
		return userJpaRepository.findById(id)
				.map(UserEntity::toDomain)
				.orElseThrow(() -> new ResourceNotFoundException("User", id));
	}

}
