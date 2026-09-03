package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.exception.InsufficientBalanceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserAccountTest {

	@Test
	void debitsAndCreditsImmutableBalance() {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		UserAccount account = account();

		UserAccount debited = account.debit(new BigDecimal("1200.00"), now);
		UserAccount credited = debited.credit(new BigDecimal("200.00"), now.plusMinutes(1));

		assertThat(account.balance()).isEqualByComparingTo("5000.00");
		assertThat(debited.balance()).isEqualByComparingTo("3800.00");
		assertThat(credited.balance()).isEqualByComparingTo("4000.00");
		assertThat(credited.updatedAt()).isEqualTo(now.plusMinutes(1));
	}

	@Test
	void rejectsInvalidAmountsAndInsufficientBalance() {
		OffsetDateTime now = OffsetDateTime.now();
		assertThatThrownBy(() -> account().debit(BigDecimal.ZERO, now))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> account().credit(new BigDecimal("-1"), now))
				.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> account().debit(new BigDecimal("5000.01"), now))
				.isInstanceOfSatisfying(InsufficientBalanceException.class, exception -> {
					assertThat(exception.requiredBalance()).isEqualByComparingTo("5000.01");
					assertThat(exception.currentBalance()).isEqualByComparingTo("5000.00");
				});
	}

	private static UserAccount account() {
		OffsetDateTime createdAt = OffsetDateTime.parse("2025-01-01T10:00:00Z");
		return new UserAccount(
				UUID.randomUUID(), "rick@sanchez.dev", "hash", "Rick Sanchez", "ROLE_USER",
				new BigDecimal("5000.00"), createdAt, createdAt);
	}

}
