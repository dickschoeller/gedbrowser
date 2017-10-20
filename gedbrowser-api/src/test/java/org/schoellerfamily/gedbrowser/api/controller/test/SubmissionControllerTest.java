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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
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
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
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

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateSubmissionsSimple()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiSubmission reqBody = new ApiSubmission("submission", "");
        final HttpEntity<ApiSubmission> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmission> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmission.class);
        final ApiSubmission resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateSubmissionDateAttribute()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/submissions";
        final ApiSubmission reqBody = new ApiSubmission("submission", "");
        final HttpEntity<ApiSubmission> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmission> submissionEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmission.class);
        then(submissionEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiSubmission resBody = submissionEntity.getBody();
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
        final ResponseEntity<ApiSubmission> checkEntity =
                testRestTemplate.getForEntity(checkUrl, ApiSubmission.class);
        final ApiSubmission checkSubmission = checkEntity.getBody();
        // Do some checks.
        then(checkSubmission.getString()).isEqualTo(id);
        final List<ApiAttribute> attributes = checkSubmission.getAttributes();
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
