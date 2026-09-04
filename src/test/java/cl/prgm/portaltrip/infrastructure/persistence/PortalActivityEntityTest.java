package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import cl.prgm.portaltrip.domain.exception.DomainValidationException;
import static org.assertj.core.api.Assertions.*;

class PortalActivityEntityTest {
    private final OffsetDateTime now = OffsetDateTime.parse("2026-09-04T12:00:00Z");

    @Test
    void expiredCycleCannotBeUsedButCompletedSampleCanBeRecovered() {
        var activity = PortalActivityEntity.start(UUID.randomUUID(), now);
        assertThatThrownBy(() -> activity.sample(activity.cycleId(), 1, 1000, 1, now.plusSeconds(90)))
                .isInstanceOf(DomainValidationException.class);
        activity.restart(now);
        activity.sample(activity.cycleId(), 1, 2500, 2, now.plusSeconds(3));
        activity.complete(new BigDecimal("980"));
        activity.sample(activity.cycleId(), 1, 2500, 2, now.plusSeconds(100));
        assertThat(activity.payout()).isEqualByComparingTo("980");
        assertThatThrownBy(() -> activity.sample(activity.cycleId(), 2, 1000, 1, now.plusSeconds(4)))
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void rejectsMalformedMeasurementsAtTheDomainBoundary() {
        var activity = PortalActivityEntity.start(UUID.randomUUID(), now);
        for (long active : new long[] {-1, 5001}) {
            assertThatThrownBy(() -> activity.sample(activity.cycleId(), 1, active, 1, now.plusSeconds(1)))
                    .isInstanceOf(DomainValidationException.class);
        }
        for (double distance : new double[] {Double.NaN, Double.POSITIVE_INFINITY, -1, 21}) {
            assertThatThrownBy(() -> activity.sample(activity.cycleId(), 1, 900, distance, now.plusSeconds(1)))
                    .isInstanceOf(DomainValidationException.class);
        }
        activity.sample(activity.cycleId(), 1, 0, 1, now.plusSeconds(1));
        assertThat(activity.progress()).isZero();
    }
}
