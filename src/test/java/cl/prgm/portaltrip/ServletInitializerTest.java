package cl.prgm.portaltrip;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServletInitializerTest {

	@Test
	void configureUsesApplicationSource() {
		ServletInitializer initializer = new ServletInitializer();
		SpringApplicationBuilder builder = mock(SpringApplicationBuilder.class);
		when(builder.sources(PortaltripApplication.class)).thenReturn(builder);

		assertThat(initializer.configure(builder)).isSameAs(builder);
		verify(builder).sources(PortaltripApplication.class);
	}

}
