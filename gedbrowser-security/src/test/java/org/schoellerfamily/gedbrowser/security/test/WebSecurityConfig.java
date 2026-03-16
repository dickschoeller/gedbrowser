package org.schoellerfamily.gedbrowser.security.test;

import org.schoellerfamily.gedbrowser.security.auth.AuthenticationFailureHandler;
import org.schoellerfamily.gedbrowser.security.auth.AuthenticationSuccessHandler;
import org.schoellerfamily.gedbrowser.security.auth.LogoutSuccess;
import org.schoellerfamily.gedbrowser.security.auth.RestAuthenticationEntryPoint;
import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Value("${spring.profiles.active:production}")
    private String activeProfile;

    /** */
    private final CustomUserDetailsService customUserDetailsService;

    /** */
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /** */
    private final LogoutSuccess logoutSuccess;

    /** */
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    /** */
    private final AuthenticationFailureHandler authenticationFailureHandler;

    /** */
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * Register a DaoAuthenticationProvider so AuthenticationManager can use the
     * provided UserDetailsService and PasswordEncoder.
     *
     * @param passwordEncoder the password encoder
     * @return the authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(final PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
            customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Expose the AuthenticationManager from AuthenticationConfiguration.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception if there is a problem
     */
    @Bean
    public AuthenticationManager authenticationManager(
        final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configure the security filter chain using the modern approach.
     *
     * @param http the http security object
     * @return the security filter chain
     * @throws Exception if there is a problem
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // Start with CSRF configuration (kept in helper)
        final HttpSecurity configured = handleCsrf(http);

        configured
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint))
            // Add the token filter before the basic authentication filter
            .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class)
            // Use the newer authorizeHttpRequests API
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            // Configure form login
            .formLogin(form -> form.loginPage("/v1/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler))
            // Configure logout using the PathPatternRequestMatcher builder
            .logout(logout -> logout
                .logoutRequestMatcher(
                    PathPatternRequestMatcher.withDefaults().matcher("/v1/logout"))
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies(cookie));

        return configured.build();
    }

    private HttpSecurity handleCsrf(final HttpSecurity http) throws Exception {
        if ("test".equals(activeProfile)) {
            // Use the lambda-based CsrfConfigurer to disable CSRF in test profile
            http.csrf(csrf -> csrf.disable());
        } else {
            // Configure CSRF using the lambda-based API. Use PathPatternRequestMatcher
            // for endpoints that should be ignored by CSRF protection and set a
            // CookieCsrfTokenRepository with HttpOnly disabled for client access.
            http.csrf(csrf -> csrf
                .ignoringRequestMatchers(
                    PathPatternRequestMatcher.withDefaults().matcher("/v1/login"),
                    PathPatternRequestMatcher.withDefaults().matcher("/v1/signup"))
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        }
        return http;
    }
}
