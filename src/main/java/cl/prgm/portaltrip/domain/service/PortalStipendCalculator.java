package cl.prgm.portaltrip.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;

import org.springframework.stereotype.Component;

import cl.prgm.portaltrip.domain.model.PortalStipendBeat;

/**
 * Server-owned Gaussian portal payout with rolling-window fatigue.
 * The amount is always decided here; clients never send a figure.
 */
@Component
public class PortalStipendCalculator {

	public static final long COOLDOWN_MS = 6_000;
	static final long HELP_WINDOW_MS = 12 * 60 * 1000L;
	public static final long EARN_WINDOW_MS = 20 * 60 * 1000L;
	static final BigDecimal EARN_CAP = new BigDecimal("6500");
	static final double PAYOUT_MEAN = 650;
	static final double PAYOUT_STD = 100;
	static final double PAYOUT_MIN = 300;
	static final double PAYOUT_MAX = 900;
	static final double FATIGUE_FLOOR = 0.1;
	static final long PAYOUT_FLOOR = 200;
	static final long CHAIN_MS = 28_000;
	static final double CHAIN_FACTOR = 0.52;

	private final DoubleSupplier gaussian;

	public PortalStipendCalculator() {
		Random random = new Random();
		this.gaussian = random::nextGaussian;
	}

	PortalStipendCalculator(DoubleSupplier gaussian) {
		this.gaussian = gaussian;
	}

	public boolean coolingDown(List<PortalStipendBeat> beats, OffsetDateTime now) {
		return beats.stream()
				.map(PortalStipendBeat::at)
				.max(Comparator.naturalOrder())
				.filter(last -> Duration.between(last, now).toMillis() < COOLDOWN_MS)
				.isPresent();
	}

	public BigDecimal sample(List<PortalStipendBeat> beats, OffsetDateTime now, double movement, long activeMs) {
		long recent = beats.stream()
				.filter(beat -> within(beat.at(), now, HELP_WINDOW_MS))
				.count();
		BigDecimal earned = beats.stream()
				.filter(beat -> within(beat.at(), now, EARN_WINDOW_MS))
				.map(PortalStipendBeat::payout)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		OffsetDateTime last = beats.stream()
				.map(PortalStipendBeat::at)
				.max(Comparator.naturalOrder())
				.orElse(null);
		double chained = last != null && Duration.between(last, now).toMillis() < CHAIN_MS
				? CHAIN_FACTOR
				: 1;
		double helpFactor = Math.max(FATIGUE_FLOOR, Math.exp(-recent * 0.48));
		double earnFactor = Math.max(
				FATIGUE_FLOOR,
				1 - earned.doubleValue() / EARN_CAP.doubleValue());
        // Only distance accepted by the activity validator reaches this calculation.
        // Minimum qualifying movement gets the base; six portal widths reach the bonus ceiling.
        double movementFactor = 1 + 0.5 * Math.clamp((movement - 1.5) / 4.5, 0, 1);
		double timeFactor = 1 + 0.2 * Math.clamp((activeMs - 2400) / 5600.0, 0, 1);
		long payout = Math.max(
				PAYOUT_FLOOR,
				Math.round(sampleBase() * movementFactor * timeFactor * helpFactor * earnFactor * chained));
		return BigDecimal.valueOf(payout).setScale(2, RoundingMode.UNNECESSARY);
	}

	private double sampleBase() {
		double raw = PAYOUT_MEAN + gaussian.getAsDouble() * PAYOUT_STD;
		return Math.min(PAYOUT_MAX, Math.max(PAYOUT_MIN, raw));
	}

	private static boolean within(OffsetDateTime at, OffsetDateTime now, long windowMs) {
		return Duration.between(at, now).toMillis() < windowMs;
	}

}
