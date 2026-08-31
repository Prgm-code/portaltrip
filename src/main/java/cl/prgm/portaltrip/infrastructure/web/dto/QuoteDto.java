package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;

import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.RiskLevel;

public record QuoteDto(
		BigDecimal basePrice,
		BigDecimal locationSurcharge,
		BigDecimal passengerSurcharge,
		BigDecimal tripSurcharge,
		BigDecimal insuranceCost,
		BigDecimal total,
		RiskLevel risk) {

	public static QuoteDto from(Quote quote) {
		return new QuoteDto(
				quote.basePrice(),
				quote.locationSurcharge(),
				quote.passengerSurcharge(),
				quote.tripSurcharge(),
				quote.insuranceCost(),
				quote.total(),
				quote.risk());
	}

}
