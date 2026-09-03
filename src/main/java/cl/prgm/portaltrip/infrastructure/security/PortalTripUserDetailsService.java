package cl.prgm.portaltrip.infrastructure.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

@Service
public class PortalTripUserDetailsService implements UserDetailsService {

	private final UserJpaRepository userJpaRepository;

	public PortalTripUserDetailsService(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		UserEntity user = userJpaRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return User.withUsername(user.getEmail())
				.password(user.getPasswordHash())
				.authorities(user.getRole())
				.build();
	}

}
