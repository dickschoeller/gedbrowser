package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.RestClientException;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(
    classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
public class SubmissionControllerTest {
    /**
     * RestTestClient injected by Spring's test support.
     */
    @Autowired
    private RestTestClient restTestClient;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    void testGetSubmissionsGl120368() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/submissions";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String bodyFragment = "[ {\n" + "  \"type\" : \"submission\",\n"
            + "  \"string\" : \"B1\",\n" + "  \"attributes\" : [ {\n"
            + "    \"type\" : \"attribute\",\n"
            + "    \"string\" : \"Generations of descendants\",\n" + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"2\"\n" + "  } ]\n" + "}";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    void testGetSubmissionsMiniSchoeller() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/submissions";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String bodyFragment = "[ ]";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    void testGetSubmissionsGl120368B1() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/submissions/B1";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String bodyFragment = "{\n" + "  \"type\" : \"submission\",\n"
            + "  \"string\" : \"B1\",\n" + "  \"attributes\" : [ {\n"
            + "    \"type\" : \"attribute\",\n"
            + "    \"string\" : \"Generations of descendants\",\n" + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"2\"\n" + "  } ]\n" + "}";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    void testGetSubmissionsGl120368Xyzzy() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/submissions/Xyzzy";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testCreateSubmissionsSimple() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/submissions";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiSubmission reqBody = ApiSubmission.builder().type("submission").string("").build();
        final EntityExchangeResult<ApiSubmission> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiSubmission.class);
        final ApiSubmission resBody = entity.getResponseBody();

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteSubmission() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/submissions";
        final ApiSubmission reqBody = ApiSubmission.builder().type("submission").string("").build();
        final EntityExchangeResult<ApiSubmission> submissionEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiSubmission.class);
        assertThat(submissionEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new submission.
        final ApiSubmission resBody = submissionEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiSubmission> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSubmission.class);
        assertThat(preDeleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiSubmission> postDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSubmission.class);
        assertThat(postDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteSubmissionNotFound() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/submissions/XXXXXXX";
        final EntityExchangeResult<ApiSubmission> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSubmission.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteSubmissionDatabaseNotFound() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/XYZZY/submissions/SUBM1";
        final EntityExchangeResult<ApiSubmission> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSubmission.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testUpdateSubmissionWithNote() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/submissions";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<ApiAttribute> attributes = List.of(ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .attributes(List.of())
            .tail("first note")
            .build());
        final ApiSubmission reqBody = ApiSubmission.builder()
            .type("submission")
            .string("")
            .attributes(attributes)
            .build();
        final EntityExchangeResult<ApiSubmission> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiSubmission.class);
        final ApiSubmission resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .attributes(List.of())
            .tail("this is a note")
            .build();
        final ApiSubmission putRequestBody = resBody.toBuilder().attribute(aNote).build();
        final EntityExchangeResult<ApiSubmission> putResponseEntity = restTestClient.put()
            .uri(URI.create(url + "/" + putRequestBody.getString()))
            .headers(h -> h.addAll(headers))
            .body(putRequestBody)
            .exchange()
            .returnResult(ApiSubmission.class);
        assertEquals(aNote,
            java.util.Optional.ofNullable(putResponseEntity.getResponseBody())
                .map(b -> b.getAttributes().get(1))
                .orElse(null),
            "attribute should be present");
    }
}
