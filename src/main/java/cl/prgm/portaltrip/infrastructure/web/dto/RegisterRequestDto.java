package cl.prgm.portaltrip.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "New PortalTrip account")
public record RegisterRequestDto(
		@NotBlank(message = "Full name is required")
		@Size(min = 3, max = 100, message = "Full name must have between 3 and 100 characters")
		String fullName,
		@NotBlank(message = "Email is required")
		@Email(message = "Email must be valid")
		@Size(max = 320, message = "Email is too long")
		String email,
		@NotBlank(message = "Password is required")
		@Size(min = 8, max = 64, message = "Password must have between 8 and 64 characters")
		String password) {
}
