package org.schoellerfamily.geoservice.client;

import java.net.URI;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Retryable;
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
     * and circuit-breaker protection.
     *
     * @param url geoservice URL.
     * @return parsed geoservice item.
     */
    @CircuitBreaker(
        maxAttemptsExpression = "#{${geoservice.retry.max-attempts:3}}",
        openTimeoutExpression = "#{${geoservice.circuit-breaker.open-timeout-millis:30000}}",
        resetTimeoutExpression = "#{${geoservice.circuit-breaker.reset-timeout-millis:30000}}")
    @Retryable(
        retryFor = ResourceAccessException.class,
        maxAttemptsExpression = "#{${geoservice.retry.max-attempts:3}}",
        backoff = @Backoff(delayExpression = "#{${geoservice.retry.wait-millis:500}}"))
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
