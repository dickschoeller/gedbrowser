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
public class SourceControllerTest {
    /** */
    private static final int TRUNCATE_LENGTH = 500;

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
    public final void testGetSourcesGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/sources";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody().substring(0, TRUNCATE_LENGTH));
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"source\",\n"
                + "  \"string\" : \"S1688\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Author\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"Ancestry.com\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Title\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"1841 England Census\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Published\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"Provo, UT, USA: The Generations Network,"
                + " Inc., 2006\"\n"
                + "  }, {\n"
                + "    \"type\" : \"noteLink\",\n"
                + "    \"string\" : \"N1350\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Changed\",\n"
                + "    \"attributes\" : [ {\n"
                + "      \"type\" : \"date\",\n"
                + "      \"string\" : \"2 APR 2007\",\n"
                + "      \"attributes\" : [ {\n"
                + "        \"type\" : \"attribute\",\n"
                + "        \"string\" : \"Time\",\n"
                + "        \"attributes\" : [ ],\n"
                + "        \"tail\" : \"21:26:46\"\n"
                + "      } ]\n"
                + "    } ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ]\n"
                + "}, {";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/sources";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody().substring(0, TRUNCATE_LENGTH));
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"source\",\n"
                + "  \"string\" : \"S2\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Title\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Abbreviation\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"SchoellerMelissaBirthCert\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Note\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"We have the original of this document\"\n"
                + "  } ]\n"
                + "}, {";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/sources/S2";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"source\",\n"
                + "  \"string\" : \"S2\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Title\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Abbreviation\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"SchoellerMelissaBirthCert\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Note\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"We have the original of this document\"\n"
                + "  } ]\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2Attributes() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/sources/S2/attributes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Title\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "}, {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Abbreviation\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"SchoellerMelissaBirthCert\"\n"
                + "}, {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Note\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"We have the original of this document\"\n"
                + "} ]";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2Attributes1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/sources/S2/attributes/1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Abbreviation\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"SchoellerMelissaBirthCert\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2Attributes99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/sources/S2/attributes/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info(entity.getBody());
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2Title() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/sources/S2/Title";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Title\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "} ]";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2Title0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/sources/S2/Title/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info("\n" + entity.getBody());
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Title\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerS2Title99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/sources/S2/Title/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info(entity.getBody());
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSourcesMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/sources/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        logger.info(entity.getBody());
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
