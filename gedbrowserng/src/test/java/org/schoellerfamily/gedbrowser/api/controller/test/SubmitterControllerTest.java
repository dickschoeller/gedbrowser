package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.RestClientException;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings({ "PMD.JUnitTestsShouldIncludeAssert", "null" })
@AutoConfigureRestTestClient
public class SubmitterControllerTest {
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
    public final void testGetSubmittersGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(String.class);
        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody())
            .contains("\"type\" : \"submitter\"",
            		"\"string\" : \"U1\"",
            		"\"string\" : \"Phil Williams\"",
            		"\"name\" : \"Phil Williams\"");
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368U1() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters/U1";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(String.class);
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

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(entity.getResponseBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetSubmittersGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters/Xyzzy";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    public final void testCreateSubmittersSimple()
            throws RestClientException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiSubmitter reqBody = ApiSubmitter.builder().type("submitter").string("").build();
        final EntityExchangeResult<ApiSubmitter> entity = restTestClient.post()
                .uri(URI.create(url))
                .headers(h -> h.addAll(headers))
                .body(reqBody)
                .exchange()
                .returnResult(ApiSubmitter.class);
        final ApiSubmitter resBody = entity.getResponseBody();

        final HttpStatusCode status = entity.getStatus();
        then(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    public final void testDeleteSubmitter()
            throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters";
        final ApiSubmitter reqBody = ApiSubmitter.builder().type("submitter").string("").build();
        final EntityExchangeResult<ApiSubmitter> submitterEntity = restTestClient.post()
                .uri(URI.create(url))
                .headers(h -> h.addAll(headers))
                .body(reqBody)
                .exchange()
                .returnResult(ApiSubmitter.class);

        final HttpStatusCode status1 = submitterEntity.getStatus();
        then(status1).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new submitter.
        final ApiSubmitter resBody = submitterEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiSubmitter> preDeleteEntity = restTestClient.get()
                .uri(URI.create(deleteUrl))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(ApiSubmitter.class);

        final HttpStatusCode status2 = preDeleteEntity.getStatus();
        then(status2).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
                .uri(URI.create(deleteUrl))
                .exchange()
                .returnResult(String.class);

        final HttpStatusCode status3 = deleteEntity.getStatus();
        then(status3).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiSubmitter> postDeleteEntity = restTestClient.get()
                .uri(URI.create(deleteUrl))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(ApiSubmitter.class);
        final HttpStatusCode status4 = postDeleteEntity.getStatus();
        then(status4).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    public final void testDeleteSubmitterNotFound()
            throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters/XXXXXXX";
        final EntityExchangeResult<ApiSubmitter> preDeleteEntity = restTestClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(ApiSubmitter.class);
        then(preDeleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);
        then(deleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    public final void testDeleteSubmitterDatabaseNotFound()
            throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/XYZZY/submitters/SUBM1";
        final EntityExchangeResult<ApiSubmitter> preDeleteEntity = restTestClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(ApiSubmitter.class);
        then(preDeleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);
        then(deleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    public final void testUpdateSubmitterWithNote()
            throws RestClientException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/submitters";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<ApiAttribute> attributes = List.of(
                ApiAttribute.builder().type("attribute").string("Note").tail("first note").build());
        final ApiSubmitter reqBody =
                ApiSubmitter.builder().type("submitter").string("").attributes(attributes).build();
        final EntityExchangeResult<ApiSubmitter> entity = restTestClient.post()
                .uri(URI.create(url))
                .headers(h -> h.addAll(headers))
                .body(reqBody)
                .exchange()
                .returnResult(ApiSubmitter.class);
        final ApiSubmitter resBody = entity.getResponseBody();
        then(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                ApiAttribute.builder().type("attribute").string("Note").tail("this is a note").build();
        final ApiSubmitter putRequestBody = resBody.toBuilder()
        		.attribute(aNote)
        		.build();
        final EntityExchangeResult<ApiSubmitter> putResponseEntity = restTestClient.put()
                .uri(URI.create(url + "/" + putRequestBody.getString()))
                .headers(h -> h.addAll(headers))
                .body(putRequestBody)
                .exchange()
                .returnResult(ApiSubmitter.class);
        assertEquals(aNote,
                java.util.Optional.ofNullable(putResponseEntity.getResponseBody())
                        .map(b -> b.getAttributes().get(1)).orElse(null),
                "attribute should be present");
    }
}