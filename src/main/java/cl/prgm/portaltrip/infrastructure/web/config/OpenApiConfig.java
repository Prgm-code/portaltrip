package cl.prgm.portaltrip.infrastructure.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@Profile({"dev", "test"})
public class OpenApiConfig {

	@Bean
	public OpenAPI portaltripOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Portaltrip API")
						.version("0.0.1")
						.description("Read-only location, character, and episode catalog with interdimensional trip quotes and reservation lifecycle management."))
				.servers(List.of(new Server().url("http://localhost:8080")));
	}

}
