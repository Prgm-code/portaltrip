package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.DuplicateUserException;
import cl.prgm.portaltrip.domain.exception.InvalidCredentialsException;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;
import cl.prgm.portaltrip.infrastructure.security.JwtTokenService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;
	private final BigDecimal registrationCredit;

	public AuthServiceImpl(
			UserJpaRepository userJpaRepository,
			PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager,
			JwtTokenService jwtTokenService,
			@Value("${app.wallet.registration-credit}") BigDecimal registrationCredit) {
		this.userJpaRepository = userJpaRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
		this.registrationCredit = registrationCredit;
	}

	@Override
	@Transactional
	public AuthResult register(String fullName, String email, String password) {
		String normalizedEmail = normalizeEmail(email);
		if (userJpaRepository.existsByEmail(normalizedEmail)) {
			throw new DuplicateUserException();
		}

		OffsetDateTime now = OffsetDateTime.now();
		UserAccount user = new UserAccount(
				UUID.randomUUID(),
				normalizedEmail,
				passwordEncoder.encode(password),
				fullName.trim(),
				"ROLE_USER",
				registrationCredit,
				now,
				now);
		try {
			UserAccount saved = userJpaRepository.saveAndFlush(UserEntity.fromDomain(user)).toDomain();
			return tokenFor(saved);
		} catch (DataIntegrityViolationException exception) {
			throw new DuplicateUserException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AuthResult login(String email, String password) {
		String normalizedEmail = normalizeEmail(email);
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(normalizedEmail, password));
		} catch (AuthenticationException exception) {
			throw new InvalidCredentialsException();
		}
		UserAccount user = userJpaRepository.findByEmail(normalizedEmail)
				.map(UserEntity::toDomain)
				.orElseThrow(InvalidCredentialsException::new);
		return tokenFor(user);
	}

	private AuthResult tokenFor(UserAccount user) {
		JwtTokenService.IssuedToken token = jwtTokenService.issue(user);
		return new AuthResult(token.value(), token.expiresAt(), user);
	}

	private String normalizeEmail(String email) {
		return email.trim().toLowerCase(Locale.ROOT);
	}

}
