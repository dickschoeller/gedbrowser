package org.schoellerfamily.gedbrowser.controller.test;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Dick Schoeller
 */
public class RefererInterceptor implements ClientHttpRequestInterceptor {
    /** */
    private final String refererUrl;

    /**
     * Constructor.
     *
     * @param refererUrl the URL string to set into the referrer header
     */
    public RefererInterceptor(final String refererUrl) {
        this.refererUrl = refererUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientHttpResponse intercept(
            final HttpRequest request,
            final byte[] body,
            final ClientHttpRequestExecution execution)
            throws IOException {

        final HttpHeaders headers = request.getHeaders();
        headers.add("Referer", refererUrl);
        return execution.execute(request, body);
    }
}
