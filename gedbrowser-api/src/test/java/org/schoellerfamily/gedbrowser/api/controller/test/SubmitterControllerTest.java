package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class SubmitterControllerTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    public final void testGetSubmittersGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"submitter\",\n"
                + "  \"string\" : \"U1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Phil Williams\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ]\n"
                + "}, {";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"submitter\",\n"
                + "  \"string\" : \"U1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Phil Williams\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ]\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1Attributes() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1/attributes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ]\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1Attributes0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1/attributes/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1Attributes99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1/attributes/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info(entity.getBody());
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1Name() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1/"
                + "name";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ]\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1Name0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1/"
                + "name/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1Name99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1/"
                + "name/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info(entity.getBody());
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info(entity.getBody());
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
