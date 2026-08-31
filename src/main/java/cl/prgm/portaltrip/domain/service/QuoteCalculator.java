package cl.prgm.portaltrip.domain.service;

import java.math.BigDecimal;

import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;

/**
 * Quote rules are the same as the ones in travelRules.ts of the frontend.
 */
public class QuoteCalculator {

	public static final BigDecimal BASE_PRICE = new BigDecimal("1200");
	public static final BigDecimal INSURANCE_PRICE = new BigDecimal("190");

	private static final BigDecimal STATION_RATE = new BigDecimal("0.25");
	private static final BigDecimal EXTRA_PASSENGER_RATE = new BigDecimal("0.18");

	public Quote calculate(
			int passengers,
			TripType tripType,
			boolean insurance,
			String locationType,
			String dimension,
			int residentCount) {
		int passengerCount = Math.max(1, passengers);
		BigDecimal locationSurcharge = isStation(locationType)
				? BASE_PRICE.multiply(STATION_RATE)
				: BigDecimal.ZERO;
		BigDecimal passengerSurcharge = BASE_PRICE
				.multiply(EXTRA_PASSENGER_RATE)
				.multiply(BigDecimal.valueOf(Math.max(0, passengerCount - 1)));
		BigDecimal tripSurcharge = BASE_PRICE.multiply(tripType.multiplier().subtract(BigDecimal.ONE));
		BigDecimal insuranceCost = insurance
				? INSURANCE_PRICE.multiply(BigDecimal.valueOf(passengerCount))
				: BigDecimal.ZERO;
		BigDecimal total = BASE_PRICE
				.add(locationSurcharge)
				.add(passengerSurcharge)
				.add(tripSurcharge)
				.add(insuranceCost);
		return new Quote(
				BASE_PRICE,
				locationSurcharge,
				passengerSurcharge,
				tripSurcharge,
				insuranceCost,
				total,
				riskLevel(dimension, residentCount));
	}

	public boolean requiresInsurance(String dimension) {
		return "unknown".equalsIgnoreCase(dimension);
	}

	private boolean isStation(String locationType) {
		return locationType != null && locationType.toLowerCase().contains("station");
	}

	private RiskLevel riskLevel(String dimension, int residentCount) {
		if (residentCount == 0) {
			return RiskLevel.HIGH;
		}
		if (requiresInsurance(dimension) || residentCount < 5) {
			return RiskLevel.MEDIUM;
		}
		return RiskLevel.LOW;
	}

}
