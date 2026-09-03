package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;

public record ReservationBalanceResult(Reservation reservation, BigDecimal remainingBalance) {
}
