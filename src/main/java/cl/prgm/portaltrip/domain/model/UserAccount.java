package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import cl.prgm.portaltrip.domain.exception.InsufficientBalanceException;

public record UserAccount(
		UUID id,
		String email,
		String passwordHash,
		String fullName,
		String role,
		BigDecimal balance,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt) {

	public UserAccount debit(BigDecimal amount, OffsetDateTime now) {
		if (amount.signum() <= 0) {
			throw new IllegalArgumentException("Debit amount must be positive");
		}
		if (balance.compareTo(amount) < 0) {
			throw new InsufficientBalanceException(amount, balance);
		}
		return withBalance(balance.subtract(amount), now);
	}

	public UserAccount credit(BigDecimal amount, OffsetDateTime now) {
		if (amount.signum() <= 0) {
			throw new IllegalArgumentException("Credit amount must be positive");
		}
		return withBalance(balance.add(amount), now);
	}

	private UserAccount withBalance(BigDecimal newBalance, OffsetDateTime now) {
		return new UserAccount(id, email, passwordHash, fullName, role, newBalance, createdAt, now);
	}

}
