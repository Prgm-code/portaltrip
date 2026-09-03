package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserJpaRepository userJpaRepository;

	@Test
	void findsUserOrReturnsNotFound() {
		UUID id = UUID.randomUUID();
		OffsetDateTime now = OffsetDateTime.now();
		UserAccount account = new UserAccount(
				id, "rick@sanchez.dev", "hash", "Rick Sanchez", "ROLE_USER",
				new BigDecimal("5000.00"), now, now);
		UserServiceImpl service = new UserServiceImpl(userJpaRepository);
		when(userJpaRepository.findById(id)).thenReturn(Optional.of(UserEntity.fromDomain(account)));

		assertThat(service.findById(id)).isEqualTo(account);

		UUID missingId = UUID.randomUUID();
		when(userJpaRepository.findById(missingId)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.findById(missingId))
				.isInstanceOf(ResourceNotFoundException.class);
	}

}
