package org.schoellerfamily.gedbrowser.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.Application;
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
@TestPropertySource(properties = {"management.server.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ApplicationTest {
    /**
     * Management port.
     */
    @LocalManagementPort
    private int mgt;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToInfoEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/actuator/info", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToHealthEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/actuator/health", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToLoadEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/actuator/restore", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Reloaded");
    }

    /** */
    @Test
    public final void testApplicationName() {
        final Application a = new Application();
        assertEquals("gedbrowser", a.getApplicationName(), "Application name mismatch");
    }
}
