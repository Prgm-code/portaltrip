package cl.prgm.portaltrip.infrastructure.web.controller;

import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.HealthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Health")
public class HomeController {

	@GetMapping(path = { "/health", "/healthcheck" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Check application health")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Application is healthy", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "500",
				description = "Application is unavailable",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public HealthResponse health() {
		return new HealthResponse("UP", "portaltrip", Instant.now().toString());
	}

}
