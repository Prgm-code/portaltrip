package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;

public record PortalStipendResult(BigDecimal payout, UserAccount user) {
}
