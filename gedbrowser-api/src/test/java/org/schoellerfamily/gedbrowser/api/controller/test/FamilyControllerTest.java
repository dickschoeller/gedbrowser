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
public class FamilyControllerTest {
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
    public final void testGetFamiliesGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"family\",\n"
                + "  \"string\" : \"F1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"husband\",\n"
                + "    \"string\" : \"I1354\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/families";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"family\",\n"
                + "  \"string\" : \"F1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"husband\",\n"
                + "    \"string\" : \"I2\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"wife\",\n"
                + "    \"string\" : \"I3\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"child\",\n"
                + "    \"string\" : \"I1\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesGl120368F1593() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families/F1593";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"family\",\n"
                + "  \"string\" : \"F1593\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"husband\",\n"
                + "    \"string\" : \"I4351\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/families/F1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"family\",\n"
                + "  \"string\" : \"F1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"husband\",\n"
                + "    \"string\" : \"I2\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"wife\",\n"
                + "    \"string\" : \"I3\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"child\",\n"
                + "    \"string\" : \"I1\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Attributes() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/families/F1/attributes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"husband\",\n"
                + "  \"string\" : \"I2\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}, {\n"
                + "  \"type\" : \"wife\",\n"
                + "  \"string\" : \"I3\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}, {\n"
                + "  \"type\" : \"child\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}, {\n";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Attributes4() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/families/F1/attributes/4";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Marriage\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"27 MAY 1984\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Temple Emanu-el, Providence, Providence"
                + " County, Rhode Island, USA\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Note\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"The ceremony performed by Rabbi Wayne"
                + " Franklin and Cantor Ivan\\nPerlman.  The best man and"
                + " matron of honor were Dale Matcovitch\\nand Carol Robinson"
                + " Sacerdote.\"\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S4\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ],\n"
                + "  \"tail\" : \"\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Attributes99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/families/F1/attributes/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Marriage() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/families/F1/Marriage";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Marriage\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"27 MAY 1984\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Temple Emanu-el, Providence, Providence"
                + " County, Rhode Island, USA\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Note\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"The ceremony performed by Rabbi Wayne"
                + " Franklin and Cantor Ivan\\nPerlman.  The best man and"
                + " matron of honor were Dale Matcovitch\\nand Carol Robinson"
                + " Sacerdote.\"\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S4\",\n"
                + "    \"attributes\" : [ ]\n"
                + "  } ],\n"
                + "  \"tail\" : \"\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Children() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/families/F1/child";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"child\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}, {\n";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Child0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/families/F1/child/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"child\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ ]\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1Child99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/families/F1/child/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/families/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
