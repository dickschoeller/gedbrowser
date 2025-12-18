package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApplicationTest {
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
    private TestRestTemplate testRestTemplate;

    /** */
    @Test
    public final void testReturnStatus200WhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/geocode?name=Bethlehem,%20PA",
                Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void testReturnStatus200WhenSendingRequestWithModern() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                + "/geocode?name=Bethlehem,%20PA&modernName=Bethlehem,%20PA",
                Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void testReturnPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/geocode?name=Bethlehem,%20PA",
                Map.class);
        then(Optional.ofNullable(entity.getBody())
            .map(b -> b.get("placeName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void testReturnPlaceNameSendingRequestWithModern() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                + "/geocode?name=Bethlehem,%20PA"
                + "&modernName=Bethlehem,%20PA",
                Map.class);
        then(Optional.ofNullable(entity.getBody())
            .map(b -> b.get("placeName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void testReturnModernPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/geocode?name=Allentown,%20PA",
                Map.class);
        then(Optional.ofNullable(entity.getBody())
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Allentown, PA");
    }

    /** */
    @Test
    public final void testReturnModernNameSendingRequestWithModernName() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                + "/geocode?name=Bethlehem,%20Pennsylvania"
                + "&modernName=Bethlehem,%20PA",
                Map.class);
        then(Optional.ofNullable(entity.getBody())
            .map(b -> b.get("modernPlaceName")).orElse(null))
            .isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void testReturnGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/geocode?name=Bethlehem,%20PA",
                Map.class);

        then(Optional.ofNullable(entity.getBody())
            .map(b -> b.get("result")).orElse(null)).isNotNull();
    }

    /** */
    @Test
    public final void testReturnNullGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/geocode?name=XYZZY",
                Map.class);

        then(Optional.ofNullable(entity.getBody())
            .map(b -> b.get("result")).orElse(null)).isNull();
    }

    /** */
    @Test
    public final void testReturn200WhenSendingRequestToInfoEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/actuator/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void testReturn200WhenSendingRequestToHealthEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/actuator/health", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}