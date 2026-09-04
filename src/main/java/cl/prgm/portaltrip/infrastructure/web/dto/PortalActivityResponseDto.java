package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PortalActivityResponseDto(
		UUID cycleId, int nextSequence, double progress, BigDecimal payout, BigDecimal balance) {
}
