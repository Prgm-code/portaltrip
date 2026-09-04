package cl.prgm.portaltrip.domain.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.model.PortalStipendBeat;

import static org.assertj.core.api.Assertions.assertThat;

class PortalStipendCalculatorTest {

	private final PortalStipendCalculator calculator = new PortalStipendCalculator(() -> 0);

	@Test
	void samplesMeanWhenTheLinkHasNotBeenHeldRecently() {
		OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");

		assertThat(calculator.sample(List.of(), now, 1.5, 2400)).isEqualByComparingTo("650.00");
		assertThat(calculator.coolingDown(List.of(), now)).isFalse();
	}

	@Test
	void reducesPayoutForRecentHelpsAndChains() {
		OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");
		PortalStipendBeat minuteAgo = new PortalStipendBeat(
				now.minusMinutes(1), new BigDecimal("650.00"));
		PortalStipendBeat tenSecondsAgo = new PortalStipendBeat(
				now.minusSeconds(10), new BigDecimal("650.00"));

		assertThat(calculator.sample(List.of(minuteAgo), now, 1.5, 2400))
				.isEqualByComparingTo(expected(1, 650, false));
		assertThat(calculator.sample(List.of(tenSecondsAgo), now, 1.5, 2400))
				.isEqualByComparingTo(expected(1, 650, true));
	}

	@Test
	void floorsPayoutWhenTheEarnWindowIsExhausted() {
		OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");
		PortalStipendBeat fat = new PortalStipendBeat(now.minusMinutes(5), new BigDecimal("5000.00"));

		assertThat(calculator.sample(List.of(fat), now, 1.5, 2400)).isEqualByComparingTo("200.00");
	}

	@Test
	void coolingDownOnlyInsideTheSixSecondFloor() {
		OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");

		assertThat(calculator.coolingDown(
				List.of(new PortalStipendBeat(now.minusSeconds(3), new BigDecimal("650.00"))),
				now)).isTrue();
		assertThat(calculator.coolingDown(
				List.of(new PortalStipendBeat(now.minusSeconds(7), new BigDecimal("650.00"))),
				now)).isFalse();
	}

	@Test
    void ignoresRewardsOutsideTheRollingWindow() {
        OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");
        assertThat(calculator.sample(List.of(new PortalStipendBeat(now.minusMinutes(20),
                new BigDecimal("5000"))), now, 1.5, 2400)).isEqualByComparingTo("650");
    }

	@Test
    void moreValidatedMovementIncreasesRewardWithinAModerateCeiling() {
        OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");
        assertThat(calculator.sample(List.of(), now, 1.5, 2400)).isEqualByComparingTo("650");
        assertThat(calculator.sample(List.of(), now, 3.75, 2400)).isEqualByComparingTo("813");
        assertThat(calculator.sample(List.of(), now, 6, 2400)).isEqualByComparingTo("975");
        assertThat(calculator.sample(List.of(), now, 100, 2400)).isEqualByComparingTo("975");
        assertThat(new PortalStipendCalculator(() -> 100).sample(List.of(), now, 6, 2400))
                .isEqualByComparingTo("1350");
        assertThat(new PortalStipendCalculator(() -> -100).sample(List.of(), now, 1.5, 2400))
                .isEqualByComparingTo("300");
    }

	@Test
    void activeTimeHasAModerateBoundedBonus() {
        OffsetDateTime now = OffsetDateTime.parse("2026-09-04T10:00:00Z");
        assertThat(calculator.sample(List.of(), now, 1.5, 2400)).isEqualByComparingTo("650");
        assertThat(calculator.sample(List.of(), now, 1.5, 5200)).isEqualByComparingTo("715");
        assertThat(calculator.sample(List.of(), now, 1.5, 8000)).isEqualByComparingTo("780");
        assertThat(calculator.sample(List.of(), now, 1.5, 80000)).isEqualByComparingTo("780");
        assertThat(new PortalStipendCalculator(() -> 100).sample(List.of(), now, 6, 8000))
                .isEqualByComparingTo("1620");
    }

	private static BigDecimal expected(int recent, double earned, boolean chained) {
		double help = Math.max(0.1, Math.exp(-recent * 0.48));
		double earn = Math.max(0.1, 1 - earned / 6500);
		double chain = chained ? 0.52 : 1;
		return BigDecimal.valueOf(Math.max(200, Math.round(650 * help * earn * chain)));
	}

}
