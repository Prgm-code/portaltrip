package cl.prgm.portaltrip.infrastructure.web.controller;

import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.infrastructure.web.dto.HealthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Health")
public class HomeController {

	@GetMapping(path = { "/health", "/healthcheck" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Healthcheck de la aplicación")
	public HealthResponse health() {
		return new HealthResponse("UP", "portaltrip", Instant.now().toString());
	}

}
