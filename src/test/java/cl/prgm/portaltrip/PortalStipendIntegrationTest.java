package cl.prgm.portaltrip;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.jayway.jsonpath.JsonPath;
import cl.prgm.portaltrip.application.service.PortalActivityService;
import cl.prgm.portaltrip.infrastructure.persistence.repository.PortalActivityJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.PortalStipendJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;
import cl.prgm.portaltrip.infrastructure.web.dto.PortalActivityRequestDto;
import cl.prgm.portaltrip.infrastructure.web.dto.PortalActivityResponseDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PortalStipendIntegrationTest {
    @Autowired private MockMvc mvc;
    @Autowired private UserJpaRepository users;
    @Autowired private PortalStipendJpaRepository stipends;
    @Autowired private PortalActivityJpaRepository activities;
    @Autowired private PortalActivityService service;
    @Autowired private JdbcTemplate jdbc;
    private String token;
    private UUID userId;
    private UUID cycle;

    @BeforeEach
    void prepare() throws Exception {
        activities.deleteAll();
        stipends.deleteAll();
        users.deleteAll();
        String registration = mvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\":\"Rick Sanchez\",\"email\":\"rick.stipend@sanchez.dev\",\"password\":\"portal-gun-123\"}"))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        token = JsonPath.read(registration, "$.data.accessToken");
        userId = UUID.fromString(JsonPath.read(registration, "$.data.user.id"));
        String start = mvc.perform(post("/api/v1/users/me/portal-activity/start")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.payout").value(0))
                .andReturn().getResponse().getContentAsString();
        cycle = UUID.fromString(JsonPath.read(start, "$.data.cycleId"));
    }

    // Advance the stored sample timestamp instead of sleeping in timing tests.
    void elapsed(int seconds) {
        jdbc.update("update portal_activity set sampled_at = ? where user_id = ?",
                java.time.OffsetDateTime.now().minusSeconds(seconds), userId);
    }

    String report(int sequence, int activeMs, double distance, int expected) throws Exception {
        return mvc.perform(post("/api/v1/users/me/portal-activity")
                .header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
                .content("{\"cycleId\":\"" + cycle + "\",\"sequence\":" + sequence
                        + ",\"activeMs\":" + activeMs + ",\"distance\":" + distance + "}"))
                .andExpect(status().is(expected)).andReturn().getResponse().getContentAsString();
    }

    @Test
    void sustainedActivityPaysOnceEvenWhenFinalSampleIsRetriedConcurrently() throws Exception {
        elapsed(1); report(1, 900, 0.6, 200);
        elapsed(1); report(2, 900, 0.6, 200);
        assertThat(stipends.count()).isZero();
        elapsed(1);
        PortalActivityRequestDto sample = new PortalActivityRequestDto(cycle, 3, 900, 0.6);
        try (var executor = Executors.newFixedThreadPool(2)) {
            Callable<PortalActivityResponseDto> task = () -> service.report(userId, sample);
            var results = executor.invokeAll(List.of(task, task));
            assertThat(results.get(0).get().payout()).isPositive();
            assertThat(results.get(1).get().payout()).isEqualByComparingTo(results.get(0).get().payout());
        }
        assertThat(stipends.count()).isEqualTo(1);
        var stipend = stipends.findAll().getFirst();
        assertThat(stipend.getId()).isNotNull();
        assertThat(stipend.getCreatedAt()).isNotNull();
        BigDecimal paid = stipend.getAmount();
        assertThat(users.findById(userId).orElseThrow().toDomain().balance())
                .isEqualByComparingTo(new BigDecimal("5000").add(paid));
        report(3, 900, 0.6, 200);
        assertThat(stipends.count()).isEqualTo(1);
    }

    @Test
    void stationaryPointerAndBriefPassDoNotPayAndLongPauseResetsProgress() throws Exception {
        elapsed(1); report(1, 900, 0, 200);
        elapsed(1); report(2, 50, 1, 200);
        assertThat(stipends.count()).isZero();
        elapsed(6);
        String response = report(3, 900, 1, 200);
        assertThat(((Number) JsonPath.read(response, "$.data.progress")).doubleValue()).isZero();
        assertThat(stipends.count()).isZero();
    }

    @Test
    void rejectsDirectClaimsRapidSamplesWrongCyclesAndImpossibleTime() throws Exception {
        mvc.perform(post("/api/v1/users/me/portal-stipend").header("Authorization", "Bearer " + token))
                .andExpect(status().is4xxClientError());
        report(1, 900, 1, 422);
        elapsed(1); report(1, 5000, 1, 422);
        elapsed(1); report(3, 900, 1, 422);
        cycle = UUID.randomUUID();
        report(1, 900, 1, 422);
        assertThat(stipends.count()).isZero();
    }

    @Test
    void startReusesUnfinishedCycleAndAnotherUserCannotSubmitIt() throws Exception {
        assertThat(service.start(userId).cycleId()).isEqualTo(cycle);
        String registration = mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\":\"Morty Smith\",\"email\":\"morty@example.org\",\"password\":\"portal-gun-123\"}"))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        token = JsonPath.read(registration, "$.data.accessToken");
        report(1, 900, 1, 404);
    }

    @Test
    void expiredActivityStartsFreshAndMissingAccountIsRejected() {
        jdbc.update("update portal_activity set started_at = ? where user_id = ?",
                java.time.OffsetDateTime.now().minusMinutes(2), userId);
        assertThat(service.start(userId).cycleId()).isNotEqualTo(cycle);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> service.start(UUID.randomUUID()))
                .isInstanceOf(cl.prgm.portaltrip.domain.exception.ResourceNotFoundException.class);
    }

    @Test
    void freshCycleStillRespectsPayoutCooldown() throws Exception {
        elapsed(3); report(1, 2500, 2, 200);
        cycle = service.start(userId).cycleId();
        elapsed(3); report(1, 2500, 2, 429);
        assertThat(stipends.count()).isEqualTo(1);
    }
}
