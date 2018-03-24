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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
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
public class SourceControllerTest {
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
    public final void testReadSourcesGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/sources";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
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
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
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
                + "      } ],\n"
                + "      \"tail\" : \"\"\n"
                + "    } ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ],\n"
                + "  \"title\" : \"1841 England Census\"\n"
                + "}, {";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testReadSourcesMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/mini-schoeller/sources";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
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
                + "  } ],\n"
                + "  \"title\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "}, {";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testReadSourcesMiniSchoellerS2() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/mini-schoeller/sources/S2";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
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
                + "  } ],\n"
                + "  \"title\" : \"Schoeller, Melissa Robinson, birth"
                + " certificate\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testReadSourcesMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/mini-schoeller"
                + "/sources/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateSourcesSimple()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/sources";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiSource reqBody = new ApiSource("source", "", "Unknown");
        final HttpEntity<ApiSource> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSource> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSource.class);
        final ApiSource resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteSource()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/sources";
        final ApiSource reqBody = new ApiSource("source", "", "Unknown");
        final HttpEntity<ApiSource> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSource> sourceEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSource.class);
        then(sourceEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new source.
        final ApiSource resBody = sourceEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiSource> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiSource.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiSource> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiSource.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteSourceNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/sources/XXXXXXX";
        final ResponseEntity<ApiSource> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiSource.class);
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
                + "/gedbrowser-api/v1/dbs/XYZZY/sources/SUBM1";
        final ResponseEntity<ApiSource> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiSource.class);
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
    public final void testUpdateSourceWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/sources";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
        final ApiSource reqBody = new ApiSource("source", "", attributes,
                "Unknown");
        final HttpEntity<ApiSource> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiSource> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiSource.class);
        final ApiSource resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                new ApiAttribute("attribute", "Note", "this is a note");
        resBody.getAttributes().add(
                aNote);
        final HttpEntity<ApiSource> putRequestEntity =
                new HttpEntity<ApiSource>(resBody);
        final ResponseEntity<ApiSource> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiSource.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(1));
    }
}
