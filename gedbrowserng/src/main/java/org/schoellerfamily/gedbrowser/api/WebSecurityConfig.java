package org.schoellerfamily.gedbrowser.api;

import org.schoellerfamily.gedbrowser.security.auth.AuthenticationFailureHandler;
import org.schoellerfamily.gedbrowser.security.auth.AuthenticationSuccessHandler;
import org.schoellerfamily.gedbrowser.security.auth.LogoutSuccess;
import org.schoellerfamily.gedbrowser.security.auth.RestAuthenticationEntryPoint;
import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    /** */
    private final CustomUserDetailsService jwtUserDetailsService;

    /** */
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /** */
    private final LogoutSuccess logoutSuccess;

    /** */
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    /** */
    private final AuthenticationFailureHandler authenticationFailureHandler;

    /** */
    private final TokenHelper tokenHelper;

    /** */
	private final UserDetailsService userDetailsService;

	/** */
	@Value("${server.servlet.context-path:/gedbrowserng}")
    private String contextPath;

    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Value("${spring.profiles.active:production}")
    private String activeProfile;

    /**
     * @return the token authentication filter
     */
    @Bean
    public TokenAuthenticationFilter jwtAuthenticationTokenFilter() {
      return new TokenAuthenticationFilter(tokenHelper, userDetailsService);
    }

    /**
     * Register a DaoAuthenticationProvider so AuthenticationManager can use
     * the provided UserDetailsService and PasswordEncoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            final PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(jwtUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Expose the AuthenticationManager from AuthenticationConfiguration.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configure the security filter chain using the modern approach.
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // Apply CSRF configuration first (may disable in test profile)
        final HttpSecurity configured = configureCsrf(http);

        configured
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint))
            // Add the JWT filter before the basic authentication filter
            .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
            // Use the newer authorizeHttpRequests API instead of deprecated authorizeRequests
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            // Configure form login with handlers
            .formLogin(form -> form
                    .loginProcessingUrl("/v1/login")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler))
            // Configure logout
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/v1/logout"))
                    .logoutSuccessHandler(logoutSuccess)
                    .deleteCookies(cookie));

        return configured.build();
    }

    /**
     * Work from the http security object and enable or disable CSRF handling,
     * as requested in the application properties.
     *
     * @param http the http security object
     * @return the http security object
     * @throws Exception if there is a problem
     */
    private HttpSecurity configureCsrf(final HttpSecurity http) throws Exception {
        if ("test".equals(activeProfile)) {
            return http.csrf().disable();
        } else {
                return http.csrf().ignoringRequestMatchers(
                    "/v1/login",
                    "/v1/signup")
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and();
        }
    }
}