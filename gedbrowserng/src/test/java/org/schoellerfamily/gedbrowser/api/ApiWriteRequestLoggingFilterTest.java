package org.schoellerfamily.gedbrowser.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Unit tests for {@link ApiWriteRequestLoggingFilter} request matching behavior.
 */
class ApiWriteRequestLoggingFilterTest {

    @Test
    void testShouldNotFilterForNonWriteMethods() {
        final ApiWriteRequestLoggingFilter filter = new ApiWriteRequestLoggingFilter();
        final MockHttpServletRequest request =
            new MockHttpServletRequest("GET", "/gedbrowserng/v1/dbs/gl120368/persons/I1");

        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void testShouldNotFilterForWriteMethodOutsideDbPath() {
        final ApiWriteRequestLoggingFilter filter = new ApiWriteRequestLoggingFilter();
        final MockHttpServletRequest request =
            new MockHttpServletRequest("PUT", "/gedbrowserng/v1/login");

        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void testShouldFilterForWriteMethodOnDbPath() {
        final ApiWriteRequestLoggingFilter filter = new ApiWriteRequestLoggingFilter();
        final MockHttpServletRequest request =
            new MockHttpServletRequest("PUT", "/gedbrowserng/v1/dbs/gl120368/persons/I1");

        assertFalse(filter.shouldNotFilter(request));
    }

    @Test
    void testShouldNotFilterWhenUriIsNull() {
        final ApiWriteRequestLoggingFilter filter = new ApiWriteRequestLoggingFilter();
        final MockHttpServletRequest request = mock(MockHttpServletRequest.class);
        when(request.getMethod()).thenReturn("PUT");
        when(request.getRequestURI()).thenReturn(null);

        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void testDoFilterInternalInvokesChainForWriteRequest() throws ServletException, IOException {
        final ApiWriteRequestLoggingFilter filter = new ApiWriteRequestLoggingFilter();
        final MockHttpServletRequest request =
            new MockHttpServletRequest("PUT", "/gedbrowserng/v1/dbs/gl120368/persons/I1");
        final Principal principal = () -> "admin";
        request.setUserPrincipal(principal);
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final AtomicInteger chainInvocationCount = new AtomicInteger(0);

        final FilterChain chain = new FilterChain() {
            @Override
            public void doFilter(final ServletRequest req, final ServletResponse res)
                    throws IOException, ServletException {
                chainInvocationCount.incrementAndGet();
                ((MockHttpServletResponse) res).setStatus(204);
            }
        };

        filter.doFilter(request, response, chain);

        assertEquals(1, chainInvocationCount.get());
        assertEquals(204, response.getStatus());
    }
}
