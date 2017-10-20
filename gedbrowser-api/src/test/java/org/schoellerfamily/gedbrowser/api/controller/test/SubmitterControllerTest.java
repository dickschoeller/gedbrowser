package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class SubmitterControllerTest {
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
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"submitter\",\n"
                + "  \"string\" : \"U1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Phil Williams\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ]\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/U1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"submitter\",\n"
                + "  \"string\" : \"U1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Phil Williams\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Phil Williams\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
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
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateSubmittersSimple()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiSubmitter reqBody = new ApiSubmitter("submitter", "");
        final HttpEntity<ApiSubmitter> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmitter> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmitter.class);
        final ApiSubmitter resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateSubmitterDateAttribute()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submitters";
        final ApiSubmitter reqBody = new ApiSubmitter("submitter", "");
        final HttpEntity<ApiSubmitter> req = new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmitter> submitterEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmitter.class);
        then(submitterEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiSubmitter resBody = submitterEntity.getBody();
        final String id = resBody.getString();

        // Create a new attribute for the family.
        // This is the real step being tested.
        final String attrUrl = url + "/" + id + "/attributes/0";
        final ApiAttribute attr =
                new ApiAttribute("attribute", "Date", "1 JAN 1950");
        final HttpEntity<ApiAttribute> attrReq =
                new HttpEntity<>(attr, headers);
        final ResponseEntity<ApiAttribute> attrEntity = testRestTemplate
                .postForEntity(new URI(attrUrl), attrReq, ApiAttribute.class);
        then(attrEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check the contents of the new attribute
        final ApiAttribute newBody = attrEntity.getBody();
        then(newBody.getType()).isEqualTo("attribute");
        then(newBody.getString()).isEqualTo("Date");
        then(newBody.getTail()).isEqualTo("1 JAN 1950");

        // Now fetch the family again
        final String checkUrl = url + "/" + id;
        final ResponseEntity<ApiSubmitter> checkEntity =
                testRestTemplate.getForEntity(checkUrl, ApiSubmitter.class);
        final ApiSubmitter checkSubmitter = checkEntity.getBody();
        // Do some checks.
        then(checkSubmitter.getString()).isEqualTo(id);
        final List<ApiAttribute> attributes = checkSubmitter.getAttributes();
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
}
