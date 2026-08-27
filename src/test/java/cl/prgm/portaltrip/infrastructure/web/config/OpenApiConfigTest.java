package cl.prgm.portaltrip.infrastructure.web.config;

import org.junit.jupiter.api.Test;

import io.swagger.v3.oas.models.OpenAPI;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

	@Test
	void buildsApiInfo() {
		OpenAPI openAPI = new OpenApiConfig().portaltripOpenAPI();

		assertThat(openAPI.getInfo().getTitle()).isEqualTo("Portaltrip API");
		assertThat(openAPI.getInfo().getVersion()).isEqualTo("0.0.1");
		assertThat(openAPI.getServers()).hasSize(1);
	}

}
