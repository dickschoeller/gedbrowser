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
public class PersonControllerTest {
    /** */
    private static final int TRUNCATE_LENGTH = 500;

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
    public final void testGetPersonsGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Living /Williams/\",\n"
                + "    \"attributes\" : [ ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().substring(0, TRUNCATE_LENGTH))
                .startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Reference Number\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"1\"\n"
                + "  }, {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Melissa Robinson/Schoeller/\",\n"
                + "    \"attributes\" : [ {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I2\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Reference Number\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"2\"\n"
                + "  }, {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Richard John/Schoeller/\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Attributes() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/attributes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Reference Number\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"2\"\n"
                + "}, {\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Richard John/Schoeller/\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Attributes5() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/attributes/5";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Education\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"17 MAY 1980\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Rensselaer Polytechnic Institute,"
                + " Troy, New York, USA\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S4\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ],\n"
                + "  \"tail\" : \"BSEE\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Attributes99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/persons/I2/attributes/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Birth() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/Birth";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Birth\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"14 DEC 1958\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Womack Army Hospital, Fort Bragg,"
                + " Manchester, Cumberland County, North Carolina, USA\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S3\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ],\n"
                + "  \"tail\" : \"\"\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Birth0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/Birth/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Birth\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"14 DEC 1958\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Womack Army Hospital, Fort Bragg,"
                + " Manchester, Cumberland County, North Carolina, USA\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S3\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ],\n"
                + "  \"tail\" : \"\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Birth99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/persons/I2/Birth/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/persons/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
