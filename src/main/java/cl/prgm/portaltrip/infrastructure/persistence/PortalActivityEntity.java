package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;
import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** One bounded activity record per user. The service holds the user's database lock. */
@Entity
@Table(name = "portal_activity")
public class PortalActivityEntity {
	@Id
	@Column(name = "user_id")
	private UUID userId;
	@Column(name = "cycle_id", nullable = false)
	private UUID cycleId;
	@Column(name = "started_at", nullable = false)
	private OffsetDateTime startedAt;
	@Column(name = "sampled_at", nullable = false)
	private OffsetDateTime sampledAt;
	@Column(nullable = false)
	private int sequence;
	@Column(name = "active_ms", nullable = false)
	private long activeMs;
	@Column(nullable = false)
	private double distance;
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal payout;

	protected PortalActivityEntity() {}

	public static PortalActivityEntity start(UUID userId, OffsetDateTime now) {
		PortalActivityEntity activity = new PortalActivityEntity();
		activity.userId = userId;
		activity.restart(now);
		return activity;
	}

	public void restart(OffsetDateTime now) {
		cycleId = UUID.randomUUID();
		startedAt = sampledAt = now;
		sequence = 0;
		activeMs = 0;
		distance = 0;
		payout = BigDecimal.ZERO;
	}

	public boolean expired(OffsetDateTime now) {
		return Duration.between(startedAt, now).toSeconds() >= 90;
	}

	public boolean completed() { return payout.signum() > 0; }
	public UUID cycleId() { return cycleId; }
	public int nextSequence() { return sequence + 1; }
	public BigDecimal payout() { return payout; }
	public double movement() { return distance; }
	public long activeDurationMs() { return activeMs; }
	public double progress() { return Math.min(1, Math.min(activeMs / 2400.0, distance / 1.5)); }
	public void complete(BigDecimal amount) { payout = amount; }

	public void sample(UUID cycle, int next, long active, double movement, OffsetDateTime now) {
		if (!cycleId.equals(cycle)) throw invalid("Unknown portal cycle");
		// A lost response can be retried, including after the reward was committed.
		if (next == sequence) return;
		if (completed() || expired(now)) throw invalid("Portal cycle ended; start again");
		if (next != sequence + 1) throw invalid("Portal samples must be sent in order");
		long elapsed = Duration.between(sampledAt, now).toMillis();
		if (elapsed < 750) throw invalid("Portal samples are too frequent");
		if (active < 0 || active > 5000 || !Double.isFinite(movement) || movement < 0 || movement > 20)
			throw invalid("Invalid portal activity");
		if (active > elapsed + 150) throw invalid("Activity exceeds server elapsed time");
		// Time while stationary does not qualify. Long gaps require fresh sustained activity.
		if (elapsed > 5000 || active == 0 || movement == 0) {
			activeMs = 0;
			distance = 0;
		} else {
			activeMs += Math.min(active, elapsed);
			distance += Math.min(movement, Math.min(active, elapsed) / 1000.0 * 4);
		}
		sampledAt = now;
		sequence = next;
	}

	private static DomainValidationException invalid(String message) {
		return new DomainValidationException(message);
	}
}
