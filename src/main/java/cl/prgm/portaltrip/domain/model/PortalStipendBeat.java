package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PortalStipendBeat(OffsetDateTime at, BigDecimal payout) {
}
