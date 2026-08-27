package cl.prgm.portaltrip;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PortaltripApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainStartsWithTestProfile() {
		PortaltripApplication.main(new String[] {
				"--spring.profiles.active=test",
				"--spring.main.web-application-type=none"
		});
	}

}
