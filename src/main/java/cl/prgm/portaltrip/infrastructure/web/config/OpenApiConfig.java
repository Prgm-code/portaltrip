package cl.prgm.portaltrip.infrastructure.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI portaltripOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Portaltrip API")
						.version("0.0.1")
						.description("Consulta de locations, characters y episodes."))
				.servers(List.of(new Server().url("http://localhost:8080")));
	}

}
