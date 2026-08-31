package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;

import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server-calculated price breakdown and destination risk")
public record QuoteDto(
		@Schema(description = "Base price in credits", example = "1200")
		BigDecimal basePrice,
		@Schema(description = "Location surcharge", example = "300")
		BigDecimal locationSurcharge,
		@Schema(description = "Additional passenger surcharge", example = "216")
		BigDecimal passengerSurcharge,
		@Schema(description = "Trip type surcharge", example = "360")
		BigDecimal tripSurcharge,
		@Schema(description = "Insurance cost", example = "380")
		BigDecimal insuranceCost,
		@Schema(description = "Final price in credits", example = "2456")
		BigDecimal total,
		@Schema(description = "Destination risk", example = "MEDIUM", allowableValues = {"LOW", "MEDIUM", "HIGH"})
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
