package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
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

    private HttpStatus getStatus(final MutableHttpRequest<?> request) {
        try {
            return client.toBlocking().exchange(request, String.class).getStatus();
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

    @Test
    void testCreateGeoCodeEntryReturnsCreated() {
        final Map<String, Object> create = Map.of(
            "placeName", "Test Place Create Status",
            "modernPlaceName", "Test Place Create Status Modern");
        final HttpStatus status = getStatus(HttpRequest.POST("/geocode", create));
        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    void testCreateGeoCodeEntryPersistsData() {
        final String placeName = "Test Place Create Persist";
        final Map<String, Object> create = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern Create Persist");
        getStatus(HttpRequest.POST("/geocode", create));

        final Map<String, Object> body = getMap("/geocode?name=Test%20Place%20Create%20Persist");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Modern Create Persist");
    }

    @Test
    void testUpdateGeoCodeEntryReturnsOk() {
        final String placeName = "Test Place Update Status";
        final Map<String, Object> create = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern Before Update Status");
        getStatus(HttpRequest.POST("/geocode", create));

        final Map<String, Object> update = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern After Update Status");
        final HttpStatus status = getStatus(HttpRequest.PUT("/geocode", update));
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void testUpdateGeoCodeEntryPersistsData() {
        final String placeName = "Test Place Update Persist";
        final Map<String, Object> create = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern Before Update Persist");
        getStatus(HttpRequest.POST("/geocode", create));

        final Map<String, Object> update = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern After Update Persist");
        getStatus(HttpRequest.PUT("/geocode", update));

        final Map<String, Object> body = getMap("/geocode?name=Test%20Place%20Update%20Persist");
        assertThat(Optional.ofNullable(body)
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Modern After Update Persist");
    }

    @Test
    void testDeleteGeoCodeEntryReturnsOk() {
        final String placeName = "Test Place Delete Status";
        final Map<String, Object> create = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern Delete Status");
        getStatus(HttpRequest.POST("/geocode", create));

        final HttpStatus status = getStatus(HttpRequest.DELETE(
            "/geocode?name=Test%20Place%20Delete%20Status"));
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void testDeleteGeoCodeEntryRemovesData() {
        final String placeName = "Test Place Delete Recreate";
        final Map<String, Object> create = Map.of(
            "placeName", placeName,
            "modernPlaceName", "Modern Delete Recreate");
        getStatus(HttpRequest.POST("/geocode", create));
        getStatus(HttpRequest.DELETE("/geocode?name=Test%20Place%20Delete%20Recreate"));

        final HttpStatus status = getStatus(HttpRequest.POST("/geocode", create));
        assertEquals(HttpStatus.CREATED, status);
    }
}
