package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

/**
 * Contains integration tests for application.
 *
 * @author Richard Schoeller
 */
@MicronautTest(environments = "test")
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApplicationIT {
    /** HTTP client bound to embedded test server. */
    @Inject
    @Client("/")
    private HttpClient client;

    @SuppressWarnings({"unchecked", "PMD.LooseCoupling"})
    private Map<String, Object> getMap(final String uri) {
        return client.toBlocking().retrieve(HttpRequest.GET(uri), Map.class);
    }

    private HttpStatus getStatus(final String uri) {
        try {
            return client.toBlocking().exchange(HttpRequest.GET(uri), String.class).getStatus();
        } catch (HttpClientResponseException ex) {
            return ex.getStatus();
        }
    }

    @Test
    void testReturnStatus200WhenSendingRequestToController() {
        assertEquals(HttpStatus.OK, getStatus("/geocode?name=Bethlehem,%20PA"));
    }

    @Test
    void testReturnStatus200WhenSendingRequestWithModern() {
        assertEquals(HttpStatus.OK,
            getStatus("/geocode?name=Bethlehem,%20PA&modernName=Bethlehem,%20PA"));
    }

    @Test
    void testReturnPlaceNameSendingRequestToController() {
        final Map<String, Object> body = getMap("/geocode?name=Bethlehem,%20PA");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("placeName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    @Test
    void testReturnPlaceNameSendingRequestWithModern() {
        final Map<String, Object> body =
            getMap("/geocode?name=Bethlehem,%20PA&modernName=Bethlehem,%20PA");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("placeName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    @Test
    void testReturnModernPlaceNameSendingRequestToController() {
        final Map<String, Object> body = getMap("/geocode?name=Allentown,%20PA");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Allentown, PA");
    }

    @Test
    void testReturnModernNameSendingRequestWithModernName() {
        final Map<String, Object> body =
            getMap("/geocode?name=Bethlehem,%20Pennsylvania&modernName=Bethlehem,%20PA");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    @Test
    void testReturnGeocodeWhenSendingRequestToController() {
        final Map<String, Object> body = getMap("/geocode?name=Bethlehem,%20PA");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("result")))
            .isPresent();
    }

    @Test
    void testReturnNullGeocodeWhenSendingRequestToController() {
        final Map<String, Object> body = getMap("/geocode?name=XYZZY");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("result")))
            .isNotPresent();
    }

    @Test
    void testReturn200WhenSendingRequestToInfoEndpoint() {
        assertEquals(HttpStatus.OK, getStatus("/actuator/info"));
    }

    @Test
    void testReturn200WhenSendingRequestToHealthEndpoint() {
        assertEquals(HttpStatus.OK, getStatus("/actuator/health"));
    }
}
