package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;



/**
 * Contains integration tests for application.
 *
 * @author Richard Schoeller
 */
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@AutoConfigureRestTestClient
public class ApplicationIT {
    // The assert check is suppressed because using BDD assertions, which don't
    // match the PMD check.

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /**
     * Management port.
     */
    @LocalManagementPort
    private int mgt;

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private RestTestClient restTestClient;

    @Test
    void testReturnStatus200WhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port + "/geocode?name=Bethlehem,%20PA"))
            .exchange()
            .returnResult(Map.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testReturnStatus200WhenSendingRequestWithModern() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port
                + "/geocode?name=Bethlehem,%20PA&modernName=Bethlehem,%20PA"))
            .exchange()
            .returnResult(Map.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testReturnPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port + "/geocode?name=Bethlehem,%20PA"))
            .exchange()
            .returnResult(Map.class);
        assertThat(Optional.ofNullable(entity.getResponseBody())
            .map(b -> b.get("placeName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    @Test
    void testReturnPlaceNameSendingRequestWithModern() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port
                + "/geocode?name=Bethlehem,%20PA"
                + "&modernName=Bethlehem,%20PA"))
            .exchange()
            .returnResult(Map.class);
        assertThat(Optional.ofNullable(entity.getResponseBody())
            .map(b -> b.get("placeName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    @Test
    void testReturnModernPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port + "/geocode?name=Allentown,%20PA"))
            .exchange()
            .returnResult(Map.class);
        assertThat(Optional.ofNullable(entity.getResponseBody())
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Allentown, PA");
    }

    @Test
    void testReturnModernNameSendingRequestWithModernName() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port
                + "/geocode?name=Bethlehem,%20Pennsylvania"
                + "&modernName=Bethlehem,%20PA"))
            .exchange()
            .returnResult(Map.class);
        assertThat(Optional.ofNullable(entity.getResponseBody())
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    @Test
    void testReturnGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
            .uri(URI.create("http://localhost:" + port + "/geocode?name=Bethlehem,%20PA"))
            .exchange()
            .returnResult(Map.class);

        assertThat(Optional.ofNullable(entity.getResponseBody())
            .map(b -> b.get("result")))
            .isPresent();
    }

    @Test
    void testReturnNullGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = this.restTestClient.get()
            .uri(URI.create("http://localhost:" + this.port + "/geocode?name=XYZZY"))
            .exchange()
            .returnResult(Map.class);

        assertThat(Optional.ofNullable(entity.getResponseBody())
            .map(b -> b.get("result")))
            .isNotPresent();
    }

    @Test
    void testReturn200WhenSendingRequestToInfoEndpoint() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + mgt + "/actuator/info"))
                .exchange()
                .returnResult(Map.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testReturn200WhenSendingRequestToHealthEndpoint() {
        @SuppressWarnings("rawtypes")
        final EntityExchangeResult<Map> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + mgt + "/actuator/health"))
                .exchange()
                .returnResult(Map.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
    }
}
