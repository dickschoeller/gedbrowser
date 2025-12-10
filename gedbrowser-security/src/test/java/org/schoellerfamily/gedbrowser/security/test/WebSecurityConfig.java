package org.schoellerfamily.gedbrowser.security.test;

import org.schoellerfamily.gedbrowser.security.auth.AuthenticationFailureHandler;
import org.schoellerfamily.gedbrowser.security.auth.AuthenticationSuccessHandler;
import org.schoellerfamily.gedbrowser.security.auth.LogoutSuccess;
import org.schoellerfamily.gedbrowser.security.auth.RestAuthenticationEntryPoint;
import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    /** */
    @Value("${jwt.cookie:AUTH-TOKEN}")
    private String cookie;

    /** */
    @Value("${spring.profiles.active:production}")
    private String activeProfile;

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
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /** */
    private final TokenHelper tokenHelper;

    /** */
	private final UserDetailsService userDetailsService;

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
        handleCsrf(http)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and().addFilterBefore(tokenAuthenticationFilter,
                    BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and().formLogin()
                .loginPage("/v1/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/v1/logout"))
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies(cookie);

        return http.build();
    }

    /**
     * Work from the http security object and enable or disable CSRF handling,
     * as requested in the application properties.
     *
     * @param http the http security objec
     * @return the http security object
     * @throws Exception if there is a problem
     */
    private HttpSecurity handleCsrf(final HttpSecurity http)
            throws Exception {
        if ("test".equals(activeProfile)) {
            return http.csrf().disable();
        } else {
            return http.csrf().ignoringRequestMatchers("/v1/login", "/v1/signup")
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and();
        }
    }
}
