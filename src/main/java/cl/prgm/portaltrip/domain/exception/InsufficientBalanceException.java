package cl.prgm.portaltrip.domain.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

	private final BigDecimal requiredBalance;
	private final BigDecimal currentBalance;

	public InsufficientBalanceException(BigDecimal requiredBalance, BigDecimal currentBalance) {
		super("Insufficient balance");
		this.requiredBalance = requiredBalance;
		this.currentBalance = currentBalance;
	}

	public BigDecimal requiredBalance() {
		return requiredBalance;
	}

	public BigDecimal currentBalance() {
		return currentBalance;
	}

}
