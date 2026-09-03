package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.UserService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.UserProfileResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	@Operation(summary = "Get the authenticated user and current balance")
	public ApiResponseDto<UserProfileResponseDto> me(JwtAuthenticationToken authentication) {
		UUID userId = UUID.fromString(authentication.getToken().getClaimAsString("user_id"));
		UserProfileResponseDto user = UserProfileResponseDto.from(userService.findById(userId));
		return ApiResponseDto.success(HttpStatus.OK, "Profile retrieved successfully", user);
	}

}
