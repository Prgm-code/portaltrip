package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authenticated PortalTrip user and current credit balance")
public record UserProfileResponseDto(
		UUID id,
		String email,
		String fullName,
		BigDecimal balance) {

	public static UserProfileResponseDto from(UserAccount user) {
		return new UserProfileResponseDto(user.id(), user.email(), user.fullName(), user.balance());
	}

}
