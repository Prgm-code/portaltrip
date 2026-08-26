package cl.prgm.portaltrip;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> health() {
		return Map.of(
				"status", "UP",
				"application", "portaltrip",
				"timestamp", Instant.now().toString());
	}

}
