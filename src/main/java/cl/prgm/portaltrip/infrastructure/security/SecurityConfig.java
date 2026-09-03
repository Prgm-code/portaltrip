package cl.prgm.portaltrip.infrastructure.security;

import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(
			PortalTripUserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(provider);
	}

	@Bean
	JwtEncoder jwtEncoder(@Value("${app.security.jwt.secret}") String encodedSecret) {
		return NimbusJwtEncoder.withSecretKey(secretKey(encodedSecret))
				.algorithm(MacAlgorithm.HS256)
				.build();
	}

	@Bean
	JwtDecoder jwtDecoder(
			@Value("${app.security.jwt.secret}") String encodedSecret,
			@Value("${app.security.jwt.issuer}") String issuer,
			@Value("${app.security.jwt.audience}") String audience) {
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey(encodedSecret))
				.macAlgorithm(MacAlgorithm.HS256)
				.build();
		OAuth2TokenValidator<Jwt> audienceValidator = new JwtClaimValidator<List<String>>(
				JwtClaimNames.AUD,
				claim -> hasAudience(claim, audience));
		decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
				JwtValidators.createDefaultWithIssuer(issuer),
				audienceValidator));
		return decoder;
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		authoritiesConverter.setAuthoritiesClaimName("roles");
		authoritiesConverter.setAuthorityPrefix("");
		JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
		authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
		return authenticationConverter;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource(
			@Value("${app.cors.allowed-origins}") List<String> allowedOrigins) {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Idempotency-Key"));
		configuration.setExposedHeaders(List.of("Location"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			JwtAuthenticationConverter jwtAuthenticationConverter,
			RestAuthenticationEntryPoint authenticationEntryPoint,
			RestAccessDeniedHandler accessDeniedHandler) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/", "/index.html", "/health", "/healthcheck", "/favicon.ico", "/error").permitAll()
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/locations/**", "/api/v1/characters/**", "/api/v1/episodes/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/quotes").permitAll()
						.requestMatchers("/api/v1/users/**", "/api/v1/reservations/**").authenticated()
						.anyRequest().denyAll())
				.exceptionHandling(exceptions -> exceptions
						.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.oauth2ResourceServer(oauth2 -> oauth2
						.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler)
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
		return http.build();
	}

	private SecretKey secretKey(String encodedSecret) {
		byte[] bytes = Base64.getDecoder().decode(encodedSecret);
		if (bytes.length < 32) {
			throw new IllegalArgumentException("JWT_SECRET_BASE64 must decode to at least 32 bytes");
		}
		return new SecretKeySpec(bytes, "HmacSHA256");
	}

	static boolean hasAudience(List<String> claim, String audience) {
		return claim != null && claim.contains(audience);
	}

}
