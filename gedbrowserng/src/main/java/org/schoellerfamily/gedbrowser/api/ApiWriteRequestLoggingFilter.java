package org.schoellerfamily.gedbrowser.api;

import java.io.IOException;
import java.security.Principal;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Logs write API requests so save-path behavior can be diagnosed from server logs.
 */
@Component
@Slf4j
public final class ApiWriteRequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        final String method = request.getMethod();
        if (!"PUT".equals(method) && !"POST".equals(method) && !"DELETE".equals(method)) {
            return true;
        }
        final String uri = request.getRequestURI();
        return uri == null || !uri.contains("/v1/dbs/");
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        final Principal principal = request.getUserPrincipal();
        final String username = principal == null ? "anonymous" : principal.getName();
        log.info("API write attempt: method={} uri={} user={}",
                request.getMethod(), request.getRequestURI(), username);
        filterChain.doFilter(request, response);
        log.info("API write result: method={} uri={} status={} user={}",
                request.getMethod(), request.getRequestURI(), response.getStatus(), username);
    }
}
