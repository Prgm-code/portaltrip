package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.domain.exception.PortalStipendCooldownException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.domain.service.PortalStipendCalculator;
import cl.prgm.portaltrip.infrastructure.persistence.PortalStipendEntity;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.PortalStipendJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortalStipendServiceImplTest {

	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private PortalStipendJpaRepository portalStipendJpaRepository;
	@Mock
	private PortalStipendCalculator calculator;

	@Test
	void creditsTheWalletWithAServerSidePayout() {
		UUID userId = UUID.randomUUID();
		UserEntity user = UserEntity.fromDomain(account(userId, "5000.00"));
		when(userJpaRepository.findByIdForUpdate(userId)).thenReturn(Optional.of(user));
		when(portalStipendJpaRepository.findByUser_IdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
				any(), any())).thenReturn(List.of());
		when(calculator.coolingDown(any(), any())).thenReturn(false);
		when(calculator.sample(any(), any(), eq(3.0), eq(3200L))).thenReturn(new BigDecimal("980.00"));
		when(portalStipendJpaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
		PortalStipendServiceImpl service = new PortalStipendServiceImpl(
				userJpaRepository, portalStipendJpaRepository, calculator);

		var result = service.claim(userId, 3.0, 3200);

		verify(calculator).sample(any(), any(), eq(3.0), eq(3200L));
		assertThat(result.payout()).isEqualByComparingTo("980.00");
		assertThat(result.user().balance()).isEqualByComparingTo("5980.00");
		assertThat(user.toDomain().balance()).isEqualByComparingTo("5980.00");
		ArgumentCaptor<PortalStipendEntity> saved = ArgumentCaptor.forClass(PortalStipendEntity.class);
		verify(portalStipendJpaRepository).save(saved.capture());
		assertThat(saved.getValue().getAmount()).isEqualByComparingTo("980.00");
	}

	@Test
	void rejectsClaimsDuringTheCooldown() {
		UUID userId = UUID.randomUUID();
		UserEntity user = UserEntity.fromDomain(account(userId, "5000.00"));
		when(userJpaRepository.findByIdForUpdate(userId)).thenReturn(Optional.of(user));
		when(portalStipendJpaRepository.findByUser_IdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
				any(), any())).thenReturn(List.of());
		when(calculator.coolingDown(any(), any())).thenReturn(true);
		PortalStipendServiceImpl service = new PortalStipendServiceImpl(
				userJpaRepository, portalStipendJpaRepository, calculator);

		assertThatThrownBy(() -> service.claim(userId, 3.0, 3200))
				.isInstanceOf(PortalStipendCooldownException.class);
	}

	@Test
	void claimsMissingUserAsNotFound() {
		UUID userId = UUID.randomUUID();
		when(userJpaRepository.findByIdForUpdate(userId)).thenReturn(Optional.empty());
		PortalStipendServiceImpl service = new PortalStipendServiceImpl(
				userJpaRepository, portalStipendJpaRepository, calculator);

		assertThatThrownBy(() -> service.claim(userId, 3.0, 3200))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	private static UserAccount account(UUID id, String balance) {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		return new UserAccount(
				id, "rick@sanchez.dev", "{bcrypt}hash", "Rick Sanchez", "ROLE_USER",
				new BigDecimal(balance), now, now);
	}

}
