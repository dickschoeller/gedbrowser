package org.schoellerfamily.geoservice.client;

import java.net.URI;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

/**
 * Performs resilient outbound geoservice calls using annotation-driven
 * circuit-breaker and retry policies.
 */
@Component
@RequiredArgsConstructor
public class GeoServiceResilientCaller {

    /** Rest client used for outbound geoservice requests. */
    private final RestClient restClient;

    /**
     * Fetches the geocode response with retry on transient connectivity errors
     * and circuit-breaker protection. Only {@link ResourceAccessException} is
     * retried and counted toward opening the circuit; all other exceptions
     * propagate immediately.
     *
     * <p>Note: {@link CircuitBreaker} does not support backoff between retries.
     * Retries are attempted immediately, which is acceptable for the short
     * connection-error retry cycle used here.</p>
     *
     * @param url geoservice URL.
     * @return parsed geoservice item.
     */
    @CircuitBreaker(
        include = ResourceAccessException.class,
        maxAttemptsExpression = "#{${geoservice.retry.max-attempts:3}}",
        openTimeoutExpression = "#{${geoservice.circuit-breaker.open-timeout-millis:30000}}",
        resetTimeoutExpression = "#{${geoservice.circuit-breaker.reset-timeout-millis:30000}}")
    public GeoServiceItem fetchPrimary(final String url) {
        final GeoServiceItem body = restClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(GeoServiceItem.class)
            .getBody();

        if (body == null) {
            throw new IllegalStateException("Empty geoservice response body for " + url);
        }
        return body;
    }
}
