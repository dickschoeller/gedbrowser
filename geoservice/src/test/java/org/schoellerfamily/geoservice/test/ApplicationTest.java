package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
    @Value("${local.management.port}")
    private int mgt;

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /** */
    @Test
    public final void shouldReturnStatus200WhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:"
                + this.port
                + "/geocode?name=Bethlehem,%20PA", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void shouldReturnPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:"
                + this.port
                + "/geocode?name=Bethlehem,%20PA", Map.class);
        then(entity.getBody().get("placeName")).isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void shouldReturnModernPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:"
                + this.port
                + "/geocode?name=Bethlehem,%20PA", Map.class);
        then(entity.getBody().get("modernPlaceName"))
                .isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void shouldReturnGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:"
                + this.port
                + "/geocode?name=Bethlehem,%20PA", Map.class);

        then(entity.getBody().get("geocodingResult")).isNotNull();
    }

    /** */
    @Test
    public final void shouldReturnNullGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:"
                + this.port
                + "/geocode?name=XYZZY", Map.class);

        then(entity.getBody().get("geocodingResult")).isNull();
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToManagementEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:"
                + this.mgt + "/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
