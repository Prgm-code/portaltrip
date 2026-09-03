package cl.prgm.portaltrip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:portaltrip-dev;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"app.security.jwt.secret=cG9ydGFsdHJpcC10ZXN0LXNlY3JldC1tdXN0LWJlLWF0LWxlYXN0LTMyLWJ5dGVz"
})
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class DevOpenApiProfileTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void exposesOpenApiAndSwaggerUi() throws Exception {
		mockMvc.perform(get("/api-docs"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/swagger-ui.html"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/swagger-ui/index.html"));
	}
}
