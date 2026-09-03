package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;

public record ReservationBalanceResponseDto(
		ReservationResponseDto reservation,
		BigDecimal remainingBalance) {
}
