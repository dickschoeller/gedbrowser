package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
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
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings({ "PMD.JUnitTestsShouldIncludeAssert" })
@AutoConfigureRestTestClient
class SourceControllerTest {
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
    void testReadSourcesGl120368() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/sources";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String bodyFragment = "[ {\n" + "  \"type\" : \"source\",\n"
            + "  \"string\" : \"S1688\",\n" + "  \"attributes\" : [ {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Author\",\n"
            + "    \"attributes\" : [ ],\n" + "    \"tail\" : \"Ancestry.com\"\n" + "  }, {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Title\",\n"
            + "    \"attributes\" : [ ],\n" + "    \"tail\" : \"1841 England Census\"\n"
            + "  }, {\n" + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Published\",\n"
            + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"Provo, UT, USA: The Generations Network," + " Inc., 2006\"\n"
            + "  }, {\n" + "    \"type\" : \"notelink\",\n" + "    \"string\" : \"N1350\",\n"
            + "    \"attributes\" : [ ],\n" + "    \"tail\" : \"\"\n" + "  }, {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Changed\",\n"
            + "    \"attributes\" : [ {\n" + "      \"type\" : \"date\",\n"
            + "      \"string\" : \"2 APR 2007\",\n" + "      \"attributes\" : [ {\n"
            + "        \"type\" : \"attribute\",\n" + "        \"string\" : \"Time\",\n"
            + "        \"attributes\" : [ ],\n" + "        \"tail\" : \"21:26:46\"\n"
            + "      } ],\n" + "      \"tail\" : \"\"\n" + "    } ],\n" + "    \"tail\" : \"\"\n"
            + "  } ],\n" + "  \"images\" : [ ],\n" + "  \"title\" : \"1841 England Census\"\n"
            + "}, {";
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    void testReadSourcesMiniSchoeller() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/sources";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String bodyFragment = "[ {\n" + "  \"type\" : \"source\",\n"
            + "  \"string\" : \"S2\",\n" + "  \"attributes\" : [ {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Title\",\n"
            + "    \"attributes\" : [ ],\n" + "    \"tail\" : \"Schoeller, Melissa Robinson, birth"
            + " certificate\"\n" + "  }, {\n" + "    \"type\" : \"attribute\",\n"
            + "    \"string\" : \"Abbreviation\",\n" + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"SchoellerMelissaBirthCert\"\n" + "  }, {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Note\",\n"
            + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"We have the original of this document\"\n" + "  } ],\n"
            + "  \"images\" : [ ],\n" + "  \"title\" : \"Schoeller, Melissa Robinson,"
            + " birth certificate\"\n" + "}, {";
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    void testReadSourcesMiniSchoellerS2() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/sources/S2";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        final String bodyFragment = "{\n" + "  \"type\" : \"source\",\n"
            + "  \"string\" : \"S2\",\n" + "  \"attributes\" : [ {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Title\",\n"
            + "    \"attributes\" : [ ],\n" + "    \"tail\" : \"Schoeller, Melissa Robinson, birth"
            + " certificate\"\n" + "  }, {\n" + "    \"type\" : \"attribute\",\n"
            + "    \"string\" : \"Abbreviation\",\n" + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"SchoellerMelissaBirthCert\"\n" + "  }, {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Note\",\n"
            + "    \"attributes\" : [ ],\n"
            + "    \"tail\" : \"We have the original of this document\"\n" + "  } ],\n"
            + "  \"images\" : [ ],\n" + "  \"title\" : \"Schoeller, Melissa Robinson, birth"
            + " certificate\"\n" + "}";
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    void testReadSourcesMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/mini-schoeller"
            + "/sources/Xyzzy";
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
    void testCreateSourcesSimple() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/sources";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiSource reqBody = ApiSource.builder()
            .type("source")
            .string("")
            .title("Unknown")
            .build();
        final EntityExchangeResult<ApiSource> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiSource.class);
        final ApiSource resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteSource() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/sources";
        final ApiSource reqBody = ApiSource.builder()
            .type("source")
            .string("")
            .title("Unknown")
            .build();
        final EntityExchangeResult<ApiSource> sourceEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiSource.class);
        assertThat(sourceEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new source.
        final ApiSource resBody = sourceEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiSource> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSource.class);
        assertThat(preDeleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiSource> postDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSource.class);
        assertThat(postDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteSourceNotFound() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/sources/XXXXXXX";
        final EntityExchangeResult<ApiSource> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSource.class);
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
    void testDeleteSubmitterDatabaseNotFound() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/XYZZY/sources/SUBM1";
        final EntityExchangeResult<ApiSource> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiSource.class);
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
    void testUpdateSourceWithNote() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/sources";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<ApiAttribute> attributes = List
            .of(ApiAttribute.builder().type("attribute").string("Note").tail("first note").build());
        final ApiSource reqBody = ApiSource.builder()
            .type("source")
            .string("")
            .attributes(attributes)
            .title("Unknown")
            .build();
        final EntityExchangeResult<ApiSource> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiSource.class);
        final ApiSource resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        final ApiSource putBody = resBody.toBuilder().attribute(aNote).build();
        final EntityExchangeResult<ApiSource> putResponseEntity = restTestClient.put()
            .uri(URI.create(url + "/" + putBody.getString()))
            .body(putBody)
            .exchange()
            .returnResult(ApiSource.class);
        assertEquals(aNote,
            java.util.Optional.ofNullable(putResponseEntity.getResponseBody())
                .map(b -> b.getAttributes().get(1))
                .orElse(null),
            "attribute should be present");
    }
}
