package cl.prgm.portaltrip.infrastructure.security;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortalTripUserDetailsServiceTest {

	@Mock
	private UserJpaRepository userJpaRepository;

	@Test
	void loadsCredentialsAndAuthorityOrHidesMissingUser() {
		PortalTripUserDetailsService service = new PortalTripUserDetailsService(userJpaRepository);
		OffsetDateTime now = OffsetDateTime.now();
		UserEntity entity = UserEntity.fromDomain(new UserAccount(
				UUID.randomUUID(), "rick@sanchez.dev", "{bcrypt}hash", "Rick Sanchez",
				"ROLE_USER", new BigDecimal("5000.00"), now, now));
		when(userJpaRepository.findByEmail("rick@sanchez.dev")).thenReturn(Optional.of(entity));

		var details = service.loadUserByUsername("rick@sanchez.dev");
		assertThat(details.getUsername()).isEqualTo("rick@sanchez.dev");
		assertThat(details.getPassword()).isEqualTo("{bcrypt}hash");
		assertThat(details.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");

		when(userJpaRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.loadUserByUsername("missing@example.com"))
				.isInstanceOf(UsernameNotFoundException.class)
				.hasMessage("User not found");
	}

}
