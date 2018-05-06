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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
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
                + "/gedbrowserng/v1/dbs/gl120368/submissions";
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
                + "/gedbrowserng/v1/dbs/mini-schoeller/submissions";
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
                + "/gedbrowserng/v1/dbs/gl120368/submissions/B1";
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
    public final void testGetSubmissionsGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submissions/Xyzzy";
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
                + "/gedbrowserng/v1/dbs/gl120368/submissions";
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
    public final void testDeleteSubmission()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submissions";
        final ApiSubmission reqBody = new ApiSubmission("submission", "");
        final HttpEntity<ApiSubmission> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmission> submissionEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmission.class);
        then(submissionEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new submission.
        final ApiSubmission resBody = submissionEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiSubmission> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiSubmission.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiSubmission> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiSubmission.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteSubmissionNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submissions/XXXXXXX";
        final ResponseEntity<ApiSubmission> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiSubmission.class);
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
    public final void testDeleteSubmissionDatabaseNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/XYZZY/submissions/SUBM1";
        final ResponseEntity<ApiSubmission> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiSubmission.class);
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
    public final void testUpdateSubmissionWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submissions";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
        final ApiSubmission reqBody =
                new ApiSubmission("submission", "", attributes);
        final HttpEntity<ApiSubmission> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmission> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmission.class);
        final ApiSubmission resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                new ApiAttribute("attribute", "Note", "this is a note");
        resBody.getAttributes().add(
                aNote);
        final HttpEntity<ApiSubmission> putRequestEntity =
                new HttpEntity<ApiSubmission>(resBody);
        final ResponseEntity<ApiSubmission> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiSubmission.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(1));
    }
}
