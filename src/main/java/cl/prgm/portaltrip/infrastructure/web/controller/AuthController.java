package cl.prgm.portaltrip.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.AuthService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.AuthResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.LoginRequestDto;
import cl.prgm.portaltrip.infrastructure.web.dto.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Register an account and receive the initial travel credit")
	public ApiResponseDto<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
		AuthResponseDto response = AuthResponseDto.from(
				authService.register(request.fullName(), request.email(), request.password()));
		return ApiResponseDto.success(HttpStatus.CREATED, "Account created successfully", response);
	}

	@PostMapping("/login")
	@Operation(summary = "Log in with email and password")
	public ApiResponseDto<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		AuthResponseDto response = AuthResponseDto.from(
				authService.login(request.email(), request.password()));
		return ApiResponseDto.success(HttpStatus.OK, "Login successful", response);
	}

}
