package org.schoellerfamily.gedbrowser.security.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.security.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Dick Schoeller
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private TokenHelper tokenHelper;

    /** */
    @Autowired
    private UserDetailsService userDetailsService;

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
    public void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final String authToken = tokenHelper.getToken(request);
        if (authToken == null || skipPathRequest(request, pathsToSkip)) {
            logger.debug("Going anonymous");
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
            logger.debug("Caught exception, going anonymous", e);
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
        final List<RequestMatcher> m =
                paths.stream()
                .map(path -> new AntPathRequestMatcher(path))
                .collect(Collectors.toList());
        final OrRequestMatcher matchers = new OrRequestMatcher(m);
        return matchers.matches(request);
    }
}
