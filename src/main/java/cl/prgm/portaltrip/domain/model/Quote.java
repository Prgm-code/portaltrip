package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;

public record Quote(
		BigDecimal basePrice,
		BigDecimal locationSurcharge,
		BigDecimal passengerSurcharge,
		BigDecimal tripSurcharge,
		BigDecimal insuranceCost,
		BigDecimal total,
		RiskLevel risk) {
}
