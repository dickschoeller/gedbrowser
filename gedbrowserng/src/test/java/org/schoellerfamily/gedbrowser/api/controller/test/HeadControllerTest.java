package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HeadControllerTest {
    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    public final void testGetHeadGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .startsWith("{\n  \"type\" : \"head\",\n  \"string\" : \"Header\","
                    + "\n  \"attributes\" : [ "
                    + "{\n    \"type\" : \"attribute\",\n")
            .contains("3C8079D5-1C5A-4473-8939-6631E48D01BB");
    }

    /** */
    @Test
    public final void testGetHeadMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(
                "{\n  \"type\" : \"head\",\n  \"string\" : \"Header\","
                + "\n  \"attributes\" : [ "
                + "{\n    \"type\" : \"attribute\",\n")
            .contains("\"tail\" : \"TMG\"");
    }

    /** */
    @Test
    public final void testGetHeadBadDataSet() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/gedbrowserng/v1/dbs/XYZZY",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains(
                "  \"cause\" : null",
                "  \"stackTrace\" : [ ]",
                "  \"datasetName\" : \"XYZZY\"",
                "  \"message\" : \"Data set XYZZY not found\"",
                "  \"suppressed\" : [ ]",
                "  \"localizedMessage\" : \"Data set XYZZY not found\""
                );
    }
}
