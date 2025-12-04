package org.schoellerfamily.gedbrowser.api;

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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
     * {@inheritDoc}
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

    /**
     * @param authenticationManagerBuilder the builder
     * @throws Exception if something goes wrong
     */
    @Autowired
    public void configureGlobal(
            final AuthenticationManagerBuilder authenticationManagerBuilder,
            final PasswordEncoder passwordEncoder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        configureCsrf(httpSecurity)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and().addFilterBefore(jwtAuthenticationTokenFilter(),
                    BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and().formLogin()
                .loginPage(contextPath + "/v1/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(contextPath + "/v1/logout"))
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies(cookie);
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
            return http.csrf().ignoringAntMatchers(
                        contextPath + "/v1/login",
                        contextPath + "/v1/signup")
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and();
        }
    }
}
