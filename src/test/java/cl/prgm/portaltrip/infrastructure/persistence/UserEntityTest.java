package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.model.UserAccount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserEntityTest {

	@Test
	void mapsAndAppliesSameAccount() {
		UserAccount original = account(UUID.randomUUID(), "5000.00");
		UserEntity entity = UserEntity.fromDomain(original);

		assertThat(entity.getId()).isEqualTo(original.id());
		assertThat(entity.getEmail()).isEqualTo(original.email());
		assertThat(entity.getPasswordHash()).isEqualTo(original.passwordHash());
		assertThat(entity.getRole()).isEqualTo(original.role());
		assertThat(entity.toDomain()).isEqualTo(original);

		UserAccount changed = original.debit(new BigDecimal("1000.00"), original.updatedAt().plusMinutes(1));
		entity.apply(changed);
		assertThat(entity.toDomain()).isEqualTo(changed);
	}

	@Test
	void rejectsApplyingAnotherAccount() {
		UserEntity entity = UserEntity.fromDomain(account(UUID.randomUUID(), "5000.00"));

		assertThatThrownBy(() -> entity.apply(account(UUID.randomUUID(), "4000.00")))
				.isInstanceOf(IllegalArgumentException.class);
	}

	private static UserAccount account(UUID id, String balance) {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		return new UserAccount(
				id, "rick@sanchez.dev", "{bcrypt}hash", "Rick Sanchez", "ROLE_USER",
				new BigDecimal(balance), now, now);
	}

}
