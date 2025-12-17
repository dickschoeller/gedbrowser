package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class LoadEndpointTest {
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
    public final void testAReturn200WhenSendingRequestToClearEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/clear", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("0 locations in the cache");
    }

    /** */
    @Test
    public final void testBReturn200WhenSendingRequestToLoadEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/load", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("917 locations in the cache");
    }

    /** */
    @Test
    public final void testCReturn200WhenSendingRequestToClearEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/clear", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("0 locations in the cache");
    }

    /** */
    @Test
    public final void testDReturn200WhenSendingRequestToLoadAndFindEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/loadAndFind", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("917 locations in the cache");
    }
}