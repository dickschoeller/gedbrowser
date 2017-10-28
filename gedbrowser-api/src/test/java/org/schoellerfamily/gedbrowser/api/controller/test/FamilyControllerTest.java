package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"wife\",\n"
                + "    \"string\" : \"I3\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"child\",\n"
                + "    \"string\" : \"I1\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"wife\",\n"
                + "    \"string\" : \"I3\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"child\",\n"
                + "    \"string\" : \"I1\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
                + "}, {\n"
                + "  \"type\" : \"wife\",\n"
                + "  \"string\" : \"I3\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
                + "}, {\n"
                + "  \"type\" : \"child\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Temple Emanu-el, Providence, Providence"
                + " County, Rhode Island, USA\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Temple Emanu-el, Providence, Providence"
                + " County, Rhode Island, USA\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateFamiliesSimple()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiFamily reqBody = new ApiFamily("family", "");
        final HttpEntity<ApiFamily> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiFamily> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiFamily.class);
        final ApiFamily resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateFamiliesWithMarriage()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Marriage", ""));
        final ApiFamily reqBody = new ApiFamily("family", "", attributes);
        final HttpEntity<ApiFamily> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiFamily> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiFamily.class);
        final ApiFamily resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateFamiliesMarriageAttribute()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final ApiFamily reqBody = new ApiFamily("family", "");
        final HttpEntity<ApiFamily> req = new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiFamily> familyEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiFamily.class);
        then(familyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new family.
        final ApiFamily resBody = familyEntity.getBody();
        final String id = resBody.getString();

        // Create a new attribute for the family.
        // This is the real step being tested.
        final String attrUrl = url + "/" + id + "/attributes/0";
        final List<ApiAttribute> attrs = new ArrayList<>();
        attrs.add(new ApiAttribute("attribute", "Date", "1 JAN 1950"));
        final ApiAttribute attr =
                new ApiAttribute("Marriage", "attribute", attrs, "");
        final HttpEntity<ApiAttribute> attrReq =
                new HttpEntity<>(attr, headers);
        final ResponseEntity<ApiAttribute> attrEntity = testRestTemplate
                .postForEntity(new URI(attrUrl), attrReq, ApiAttribute.class);
        then(attrEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check the contents of the new attribute
        final ApiAttribute newBody = attrEntity.getBody();
        then(newBody.getType()).isEqualTo("attribute");
        then(newBody.getString()).isEqualTo("Marriage");
        then(newBody.getAttributes())
                .contains(new ApiAttribute("attribute", "Date", "1 JAN 1950"));

        // Now fetch the family again
        final String checkUrl = url + "/" + id;
        final ResponseEntity<ApiFamily> checkEntity =
                testRestTemplate.getForEntity(checkUrl, ApiFamily.class);
        final ApiFamily checkFamily = checkEntity.getBody();
        // Do some checks.
        then(checkFamily.getString()).isEqualTo(id);
        final List<ApiAttribute> attributes = checkFamily.getAttributes();
        assertMatch(attributes.get(0), attr);
    }

    /**
     * @param o1 object 1
     * @param o2 object 2
     */
    private void assertMatch(final ApiAttribute o1, final ApiAttribute o2) {
        assertEquals("types don't match", o1.getType(), o2.getType());
        assertEquals("strings don't match", o1.getString(), o2.getString());
        assertEquals("attributes don't match",
                o1.getAttributes(), o2.getAttributes());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteFamily()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final ApiFamily reqBody = new ApiFamily("family", "");
        final HttpEntity<ApiFamily> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiFamily> familyEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiFamily.class);
        then(familyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new family.
        final ApiFamily resBody = familyEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiFamily> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiFamily.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiFamily> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiFamily.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteFamilyNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families/XXXXXXX";
        final ResponseEntity<ApiFamily> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiFamily.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(url, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteFamilyDatabaseNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/XYZZY/families/SUBM1";
        final ResponseEntity<ApiFamily> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiFamily.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(url, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteFamilyAttribute()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Marriage", ""));
        attributes.add(new ApiAttribute("attribute", "Divorce", ""));
        final ApiFamily reqBody = new ApiFamily("family", "", attributes);
        final HttpEntity<ApiFamily> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiFamily> familyEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiFamily.class);
        then(familyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new family.
        final ApiFamily resBody = familyEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id + "/attributes/1";
        final ResponseEntity<ApiAttribute> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiAttribute.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiAttribute> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiAttribute.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        testRestTemplate.exchange(
                url + "/" + id, HttpMethod.DELETE, null, String.class);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testUpdateFamilyWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/families";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Marriage", ""));
        final ApiFamily reqBody = new ApiFamily("family", "", attributes);
        final HttpEntity<ApiFamily> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiFamily> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiFamily.class);
        final ApiFamily resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                new ApiAttribute("attribute", "Note", "this is a note");
        resBody.getAttributes().add(
                aNote);
        final HttpEntity<ApiFamily> putRequestEntity =
                new HttpEntity<ApiFamily>(resBody);
        final ResponseEntity<ApiFamily> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiFamily.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(1));
    }
}
