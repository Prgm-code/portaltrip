package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;

import cl.prgm.portaltrip.domain.model.PortalStipendResult;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credits granted for holding the portal link")
public record PortalStipendResponseDto(
		@Schema(description = "Amount credited on this claim", example = "980.00")
		BigDecimal payout,
		@Schema(description = "Wallet balance after the credit", example = "5980.00")
		BigDecimal balance) {

	public static PortalStipendResponseDto from(PortalStipendResult result) {
		return new PortalStipendResponseDto(result.payout(), result.user().balance());
	}

}
