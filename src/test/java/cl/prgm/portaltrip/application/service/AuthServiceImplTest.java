package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.prgm.portaltrip.domain.exception.DuplicateUserException;
import cl.prgm.portaltrip.domain.exception.InvalidCredentialsException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;
import cl.prgm.portaltrip.infrastructure.security.JwtTokenService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtTokenService jwtTokenService;

	private AuthServiceImpl authService;

	@BeforeEach
	void setUp() {
		authService = new AuthServiceImpl(
				userJpaRepository, passwordEncoder, authenticationManager, jwtTokenService,
				new BigDecimal("5000.00"));
	}

	@Test
	void registersNormalizedUserAndIssuesToken() {
		when(passwordEncoder.encode("portal-gun-123")).thenReturn("{bcrypt}hash");
		when(userJpaRepository.saveAndFlush(any(UserEntity.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
		when(jwtTokenService.issue(any(UserAccount.class)))
				.thenReturn(new JwtTokenService.IssuedToken("token", Instant.parse("2026-01-01T10:30:00Z")));

		AuthResult result = authService.register("  Rick Sanchez  ", " RICK@SANCHEZ.DEV ", "portal-gun-123");

		assertThat(result.accessToken()).isEqualTo("token");
		assertThat(result.user().email()).isEqualTo("rick@sanchez.dev");
		assertThat(result.user().fullName()).isEqualTo("Rick Sanchez");
		assertThat(result.user().balance()).isEqualByComparingTo("5000.00");
		assertThat(result.user().role()).isEqualTo("ROLE_USER");
	}

	@Test
	void rejectsExistingEmailBeforeEncodingPassword() {
		when(userJpaRepository.existsByEmail("rick@sanchez.dev")).thenReturn(true);

		assertThatThrownBy(() -> authService.register(
				"Rick Sanchez", "RICK@SANCHEZ.DEV", "portal-gun-123"))
				.isInstanceOf(DuplicateUserException.class);
		verify(passwordEncoder, never()).encode(any());
	}

	@Test
	void translatesUniqueConstraintRaceToDuplicateUser() {
		when(passwordEncoder.encode(any())).thenReturn("{bcrypt}hash");
		when(userJpaRepository.saveAndFlush(any(UserEntity.class)))
				.thenThrow(new DataIntegrityViolationException("duplicate"));

		assertThatThrownBy(() -> authService.register(
				"Rick Sanchez", "rick@sanchez.dev", "portal-gun-123"))
				.isInstanceOf(DuplicateUserException.class);
	}

	@Test
	void logsInNormalizedUserAndIssuesToken() {
		UserEntity user = userEntity();
		when(userJpaRepository.findByEmail("rick@sanchez.dev")).thenReturn(Optional.of(user));
		when(jwtTokenService.issue(any(UserAccount.class)))
				.thenReturn(new JwtTokenService.IssuedToken("token", Instant.parse("2026-01-01T10:30:00Z")));

		AuthResult result = authService.login(" RICK@SANCHEZ.DEV ", "portal-gun-123");

		assertThat(result.user().email()).isEqualTo("rick@sanchez.dev");
		verify(authenticationManager).authenticate(any());
	}

	@Test
	void hidesAuthenticationFailureAndMissingUser() {
		when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad"));
		assertThatThrownBy(() -> authService.login("rick@sanchez.dev", "wrong-password"))
				.isInstanceOf(InvalidCredentialsException.class);

		org.mockito.Mockito.reset(authenticationManager);
		when(userJpaRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
		assertThatThrownBy(() -> authService.login("missing@example.com", "portal-gun-123"))
				.isInstanceOf(InvalidCredentialsException.class);
	}

	private static UserEntity userEntity() {
		OffsetDateTime now = OffsetDateTime.parse("2026-01-01T10:00:00Z");
		return UserEntity.fromDomain(new UserAccount(
				UUID.randomUUID(), "rick@sanchez.dev", "{bcrypt}hash", "Rick Sanchez",
				"ROLE_USER", new BigDecimal("5000.00"), now, now));
	}

}
