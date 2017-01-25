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
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
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

    /** */
    private static final int THIRTY_SECONDS = 30 * 1000;

    /** */
    private static final int TWO_SECONDS = 2 * 1000;

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
        then(entity.getBody().get("placeName")).isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void testReturnPlaceNameSendingRequestWithModern() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                + "/geocode?name=Bethlehem,%20PA"
                + "&modernName=Bethlehem,%20Pennsylvania",
                Map.class);
        then(entity.getBody().get("placeName")).isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void testReturnModernPlaceNameSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/geocode?name=Allentown,%20PA",
                Map.class);
        then(entity.getBody().get("modernPlaceName"))
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
        then(entity.getBody().get("modernPlaceName"))
                .isEqualTo("Bethlehem, PA");
    }

    /** */
    @Test
    public final void testReturnGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/geocode?name=Bethlehem,%20PA",
                Map.class);

        then(entity.getBody().get("result")).isNotNull();
    }

    /** */
    @Test
    public final void testReturnNullGeocodeWhenSendingRequestToController() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/geocode?name=XYZZY",
                Map.class);

        then(entity.getBody().get("result")).isNull();
    }

    /** */
    @Test
    public final void testReturn200WhenSendingRequestToInfoEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void testReturn200WhenSendingRequestToHealthEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/health", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void testReturn200WhenSendingRequestToLoadEndpoint() {
        final ClientHttpRequestFactory requestFactory = testRestTemplate
                .getRestTemplate().getRequestFactory();
        final OkHttpClientHttpRequestFactory rf =
                (OkHttpClientHttpRequestFactory) requestFactory;
        rf.setConnectTimeout(TWO_SECONDS);
        rf.setReadTimeout(THIRTY_SECONDS);
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/load", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("locations in the cache");
    }
}
