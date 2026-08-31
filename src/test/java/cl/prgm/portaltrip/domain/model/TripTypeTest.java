package cl.prgm.portaltrip.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TripTypeTest {

	@Test
	void exposesCodeAndMultiplier() {
		assertThat(TripType.EXPRESS.code()).isEqualTo("express");
		assertThat(TripType.EXPRESS.multiplier()).isEqualByComparingTo("1");
		assertThat(TripType.EXPLORATION.code()).isEqualTo("exploration");
		assertThat(TripType.EXPLORATION.multiplier()).isEqualByComparingTo("1.3");
		assertThat(TripType.PREMIUM.code()).isEqualTo("premium");
		assertThat(TripType.PREMIUM.multiplier()).isEqualByComparingTo("1.65");
	}

	@Test
	void resolvesFromCode() {
		assertThat(TripType.fromCode("express")).isEqualTo(TripType.EXPRESS);
		assertThat(TripType.fromCode("exploration")).isEqualTo(TripType.EXPLORATION);
		assertThat(TripType.fromCode("premium")).isEqualTo(TripType.PREMIUM);
	}

	@Test
	void rejectsUnknownCode() {
		assertThatThrownBy(() -> TripType.fromCode("warp"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Unknown trip type: warp");
	}

	@Test
	void exposesEnumStatics() {
		assertThat(TripType.values()).hasSize(3);
		assertThat(TripType.valueOf("EXPRESS")).isEqualTo(TripType.EXPRESS);
		assertThat(RiskLevel.values()).hasSize(3);
		assertThat(RiskLevel.valueOf("MEDIUM")).isEqualTo(RiskLevel.MEDIUM);
		assertThat(ReservationStatus.values()).hasSize(4);
		assertThat(ReservationStatus.valueOf("CANCELLED")).isEqualTo(ReservationStatus.CANCELLED);
	}

}
