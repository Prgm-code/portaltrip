package cl.prgm.portaltrip.domain.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;

import static org.assertj.core.api.Assertions.assertThat;

class QuoteCalculatorTest {

	private final QuoteCalculator calculator = new QuoteCalculator();

	@Test
	void calculatesExpressQuoteForPlanetWithoutInsurance() {
		Quote quote = calculator.calculate(1, TripType.EXPRESS, false, "Planet", "Dimension C-137", 6);

		assertThat(quote.basePrice()).isEqualByComparingTo(new BigDecimal("1200"));
		assertThat(quote.locationSurcharge()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(quote.passengerSurcharge()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(quote.tripSurcharge()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(quote.insuranceCost()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(quote.total()).isEqualByComparingTo(new BigDecimal("1200"));
		assertThat(quote.risk()).isEqualTo(RiskLevel.LOW);
	}

	@Test
	void appliesStationSurchargeAndInsurancePerPassenger() {
		Quote quote = calculator.calculate(2, TripType.EXPLORATION, true, "Space station", "Dimension C-137", 6);

		assertThat(quote.locationSurcharge()).isEqualByComparingTo(new BigDecimal("300.00"));
		assertThat(quote.passengerSurcharge()).isEqualByComparingTo(new BigDecimal("216.00"));
		assertThat(quote.tripSurcharge()).isEqualByComparingTo(new BigDecimal("360.00"));
		assertThat(quote.insuranceCost()).isEqualByComparingTo(new BigDecimal("380"));
		assertThat(quote.total()).isEqualByComparingTo(new BigDecimal("2456.00"));
	}

	@Test
	void appliesPremiumMultiplierAndExtraPassengers() {
		Quote quote = calculator.calculate(3, TripType.PREMIUM, false, "Planet", "Dimension C-137", 6);

		assertThat(quote.passengerSurcharge()).isEqualByComparingTo(new BigDecimal("432.00"));
		assertThat(quote.tripSurcharge()).isEqualByComparingTo(new BigDecimal("780.00"));
		assertThat(quote.total()).isEqualByComparingTo(new BigDecimal("2412.00"));
	}

	@Test
	void treatsZeroPassengersAsOne() {
		Quote quote = calculator.calculate(0, TripType.EXPRESS, true, "Planet", "Dimension C-137", 6);

		assertThat(quote.passengerSurcharge()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(quote.insuranceCost()).isEqualByComparingTo(new BigDecimal("190"));
	}

	@Test
	void ignoresStationSurchargeWhenTypeIsNull() {
		Quote quote = calculator.calculate(1, TripType.EXPRESS, false, null, "Dimension C-137", 6);

		assertThat(quote.locationSurcharge()).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void marksHighRiskWhenLocationHasNoResidents() {
		Quote quote = calculator.calculate(1, TripType.EXPRESS, false, "Planet", "Dimension C-137", 0);

		assertThat(quote.risk()).isEqualTo(RiskLevel.HIGH);
	}

	@Test
	void marksMediumRiskForUnknownDimensionEvenWithManyResidents() {
		Quote quote = calculator.calculate(1, TripType.EXPRESS, false, "Planet", "unknown", 6);

		assertThat(quote.risk()).isEqualTo(RiskLevel.MEDIUM);
	}

	@Test
	void marksMediumRiskWhenLocationHasFewResidents() {
		Quote quote = calculator.calculate(1, TripType.EXPRESS, false, "Planet", "Dimension C-137", 4);

		assertThat(quote.risk()).isEqualTo(RiskLevel.MEDIUM);
	}

	@Test
	void requiresInsuranceOnlyForUnknownDimension() {
		assertThat(calculator.requiresInsurance("unknown")).isTrue();
		assertThat(calculator.requiresInsurance("Unknown")).isTrue();
		assertThat(calculator.requiresInsurance("Dimension C-137")).isFalse();
	}

}
