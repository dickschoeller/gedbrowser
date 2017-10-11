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
public class SubmissionControllerTest {
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
    public final void testGetSubmissionsGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"submission\",\n"
                + "  \"string\" : \"B1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Generations of descendants\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"2\"\n"
                + "  } ]\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/submissions";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ ]";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"submission\",\n"
                + "  \"string\" : \"B1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Generations of descendants\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"2\"\n"
                + "  } ]\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1Attributes() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1/attributes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Generations of descendants\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"2\"\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1Attributes0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1/attributes/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Generations of descendants\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"2\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1Attributes99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1/attributes/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1Generations() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1/"
                + "Generations%20of%20descendants";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Generations of descendants\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"2\"\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1Generations0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1/"
                + "Generations%20of%20descendants/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Generations of descendants\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"2\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1Generations99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/B1/"
                + "Generations%20of%20descendants/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
