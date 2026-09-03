package cl.prgm.portaltrip.infrastructure.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@Profile({"dev", "test"})
public class OpenApiConfig {

	@Bean
	public OpenAPI portaltripOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes(
						"bearerAuth",
						new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")))
				.info(new Info()
						.title("Portaltrip API")
						.version("0.0.1")
						.description("Interdimensional trip catalog, secure user accounts, credit balance, quotes, and user-owned reservations."))
				.servers(List.of(new Server().url("http://localhost:8080")));
	}

}
