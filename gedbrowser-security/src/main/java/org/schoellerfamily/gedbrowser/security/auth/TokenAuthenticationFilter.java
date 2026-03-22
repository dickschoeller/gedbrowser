package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Represents token authentication filter.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    /**
     * The token helper value.
     */
    private final TokenHelper tokenHelper;

    /**
     * The user details service value.
     */
    private final UserDetailsService userDetailsService;

    /**
     * The r o o t  m a t c h e r constant.
     */
    public static final String ROOT_MATCHER = "/";

    /**
     * The f a v i c o n  m a t c h e r constant.
     */
    public static final String FAVICON_MATCHER = "/favicon.ico";

    /**
     * The h t m l  m a t c h e r constant.
     */
    public static final String HTML_MATCHER = "/**/*.html";

    /**
     * The c s s  m a t c h e r constant.
     */
    public static final String CSS_MATCHER = "/**/*.css";

    /**
     * The j s  m a t c h e r constant.
     */
    public static final String JS_MATCHER = "/**/*.js";

    /**
     * The i m g  m a t c h e r constant.
     */
    public static final String IMG_MATCHER = "/images/*";

    /**
     * The l o g i n  m a t c h e r constant.
     */
    public static final String LOGIN_MATCHER = "/auth/login";

    /**
     * The l o g o u t  m a t c h e r constant.
     */
    public static final String LOGOUT_MATCHER = "/auth/logout";

    /** */
    private final List<String> pathsToSkip = List.of(
            ROOT_MATCHER,
            HTML_MATCHER,
            FAVICON_MATCHER,
            CSS_MATCHER,
            JS_MATCHER,
            IMG_MATCHER,
            LOGIN_MATCHER,
            LOGOUT_MATCHER
    );

    /**
     * Executes do filter internal.
     *
     * @param request the request
     */
    @Override
    public void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final String authToken = tokenHelper.getToken(request);
        if (authToken == null || skipPathRequest(request, pathsToSkip)) {
            log.debug("Going anonymous");
            SecurityContextHolder.getContext()
                    .setAuthentication(new AnonAuthentication());
        } else {
            processToken(authToken);
        }

        chain.doFilter(request, response);
    }

    private void processToken(final String authToken) {
        // get username from token
        try {
            final String username =
                    tokenHelper.getUsernameFromToken(authToken);
            // get user
            final UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);
            // create authentication
            final TokenBasedAuthentication authentication =
                new TokenBasedAuthentication(userDetails, authToken);
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        } catch (Exception e) {
            log.debug("Caught exception, going anonymous", e);
            SecurityContextHolder.getContext()
                    .setAuthentication(new AnonAuthentication());
        }
    }

    private boolean skipPathRequest(final HttpServletRequest request,
            final List<String> paths) {
        Assert.notNull(paths, "path cannot be null.");
        final List<RequestMatcher> m =
                paths.stream()
                .map(path -> PathPatternRequestMatcher.withDefaults().matcher(path))
                .map(RequestMatcher.class::cast)
                .toList();
        final OrRequestMatcher matchers = new OrRequestMatcher(m);
        return matchers.matches(request);
    }
}
