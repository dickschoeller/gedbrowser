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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
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
                + "/gedbrowser-api/v1/dbs/gl120368/submitters";
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
                + "  } ],\n"
                + "  \"name\" : \"Phil Williams\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/submitters/U1";
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
                + "  } ],\n"
                + "  \"name\" : \"Phil Williams\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/submitters/Xyzzy";
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
                + "/gedbrowser-api/v1/dbs/gl120368/submitters";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiSubmitter reqBody = new ApiSubmitter("submitter", "", "? ?");
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
    public final void testDeleteSubmitter()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/submitters";
        final ApiSubmitter reqBody = new ApiSubmitter("submitter", "", "? ?");
        final HttpEntity<ApiSubmitter> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmitter> submitterEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmitter.class);
        then(submitterEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new submitter.
        final ApiSubmitter resBody = submitterEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiSubmitter> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiSubmitter.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiSubmitter> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiSubmitter.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteSubmitterNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/submitters/XXXXXXX";
        final ResponseEntity<ApiSubmitter> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiSubmitter.class);
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
    public final void testDeleteSubmitterDatabaseNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/XYZZY/submitters/SUBM1";
        final ResponseEntity<ApiSubmitter> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiSubmitter.class);
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
    public final void testUpdateSubmitterWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/submitters";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
        final ApiSubmitter reqBody =
                new ApiSubmitter("submitter", "", attributes, "? ?");
        final HttpEntity<ApiSubmitter> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSubmitter> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSubmitter.class);
        final ApiSubmitter resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                new ApiAttribute("attribute", "Note", "this is a note");
        resBody.getAttributes().add(
                aNote);
        final HttpEntity<ApiSubmitter> putRequestEntity =
                new HttpEntity<ApiSubmitter>(resBody);
        final ResponseEntity<ApiSubmitter> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiSubmitter.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(1));
    }
}
