package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.lang.NonNull;
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
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    /** */
    private final TokenHelper tokenHelper;

    /** */
    private final UserDetailsService userDetailsService;

    /** */
    public static final String ROOT_MATCHER = "/";

    /** */
    public static final String FAVICON_MATCHER = "/favicon.ico";

    /** */
    public static final String HTML_MATCHER = "/**/*.html";

    /** */
    public static final String CSS_MATCHER = "/**/*.css";

    /** */
    public static final String JS_MATCHER = "/**/*.js";

    /** */
    public static final String IMG_MATCHER = "/images/*";

    /** */
    public static final String LOGIN_MATCHER = "/auth/login";

    /** */
    public static final String LOGOUT_MATCHER = "/auth/logout";

    /** */
    private final List<String> pathsToSkip = Arrays.asList(
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
     * {@inheritDoc}
     */
    @Override
    public void doFilterInternal(
            @NonNull
            final HttpServletRequest request,
            @NonNull
            final HttpServletResponse response,
            @NonNull
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

    /**
     * @param authToken the token
     */
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
                    new TokenBasedAuthentication(userDetails);
            authentication.setToken(authToken);
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        } catch (Exception e) {
            log.debug("Caught exception, going anonymous", e);
            SecurityContextHolder.getContext()
                    .setAuthentication(new AnonAuthentication());
        }
    }

    /**
     * @param request the request
     * @param paths list of paths to skip
     * @return true if there are matches
     */
    private boolean skipPathRequest(final HttpServletRequest request,
            final List<String> paths) {
        Assert.notNull(paths, "path cannot be null.");
        final List<RequestMatcher> m = paths.stream()
            .map(path -> (RequestMatcher) PathPatternRequestMatcher.withDefaults().matcher(path))
            .toList();
        final OrRequestMatcher matchers = new OrRequestMatcher(m);
        return matchers.matches(request);
    }
}
