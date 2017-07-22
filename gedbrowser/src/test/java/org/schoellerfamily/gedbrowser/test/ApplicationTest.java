package org.schoellerfamily.gedbrowser.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
    public final void shouldReturn200WhenSendingRequestToInfoEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToHealthEndpoint() {
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/health", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToLoadEndpoint() {
        final ClientHttpRequestFactory requestFactory = testRestTemplate
                .getRestTemplate().getRequestFactory();
        final HttpComponentsClientHttpRequestFactory rf =
                (HttpComponentsClientHttpRequestFactory) requestFactory;
        rf.setConnectTimeout(TWO_SECONDS);
        rf.setReadTimeout(THIRTY_SECONDS);
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/restore", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Reloaded");
    }

    /** */
    @Test
    public final void testApplicationName() {
        final Application a = new Application();
        assertEquals("Application name mismatch", "gedbrowser",
                a.getApplicationName());
    }
}
