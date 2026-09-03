package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;

public record BalanceErrorDto(
		BigDecimal required,
		BigDecimal current) {
}
