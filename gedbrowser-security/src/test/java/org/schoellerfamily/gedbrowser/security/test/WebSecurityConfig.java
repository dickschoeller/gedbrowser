package org.schoellerfamily.gedbrowser.security.test;

import org.schoellerfamily.gedbrowser.security.auth.AuthenticationFailureHandler;
import org.schoellerfamily.gedbrowser.security.auth.AuthenticationSuccessHandler;
import org.schoellerfamily.gedbrowser.security.auth.LogoutSuccess;
import org.schoellerfamily.gedbrowser.security.auth.RestAuthenticationEntryPoint;
import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.schoellerfamily.gedbrowser.security.service.impl.CustomUserDetailsService;
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
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
      return new TokenAuthenticationFilter();
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
     * @return the encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return new PasswordEncoder() {

            @Override
            public String encode(final CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(final CharSequence rawPassword,
                    final String encodedPassword) {
                if (encodedPassword == null && rawPassword == null) {
                    return true;
                }
                if (encodedPassword == null || rawPassword == null) {
                    return false;
                }
                return encodedPassword.equals(rawPassword.toString());
            } };
    }

    /** */
    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    /** */
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /** */
    @Autowired
    private LogoutSuccess logoutSuccess;

    /**
     * @param authenticationManagerBuilder the builder
     * @throws Exception if something goes wrong
     */
    @Autowired
    public void configureGlobal(
            final AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /** */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /** */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        handleCsrf(http)
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
                .loginPage("/v1/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/v1/logout"))
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies(cookie);
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
            return http.csrf().ignoringAntMatchers("/v1/login", "/v1/signup")
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and();
        }
    }
}
