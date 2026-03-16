package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
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
 * Contains integration tests for the note controller.
 *
 * @author Richard Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@AutoConfigureRestTestClient
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.UnitTestContainsTooManyAsserts" })
class NoteControllerIT {
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

    @Test
    void testReadNotesGl120368() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        @SuppressWarnings({ "java:S6126" })
        final String bodyFragment = "[ {\n" + "  \"type\" : \"note\",\n"
            + "  \"string\" : \"N1\",\n" + "  \"attributes\" : [ ],\n"
            + "  \"tail\" : \"_P_CCINFO 1-1319\"\n" + "}, {\n" + "  \"type\" : \"note\",\n"
            + "  \"string\" : \"N2\",\n" + "  \"attributes\" : [ ],\n"
            + "  \"tail\" : \"_P_CCINFO 1-1319\"\n" + "}, {\n";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    @Test
    void testReadNotesGl120368N13() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes/N13";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        @SuppressWarnings({ "java:S6126" })
        final String bodyFragment = "{\n" + "  \"type\" : \"note\",\n" + "  \"string\" : \"N13\",\n"
            + "  \"attributes\" : [ ],\n" + "  \"tail\" : \"_P_CCINFO 1-1319\\n"
            + "Suffolk County Record Office, Parish Register, St Mary"
            + " Magdalene, Debenham, Suffolk, England, Baptisms 1813-\\n"
            + "1864 (FB47/D1/10), Samuel son of William Amass & Marianne"
            + " Thurston alias Amass of Debenham Butcher was\\n"
            + "baptised by George Smalley vicar on 23/3/1828.\"\n" + "}";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    @Test
    void testReadNotesGl120368N66() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes/N66";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        @SuppressWarnings({ "java:S6126" })
        final String bodyFragment = "{\n" + "  \"type\" : \"note\",\n" + "  \"string\" : \"N66\",\n"
            + "  \"attributes\" : [ {\n" + "    \"type\" : \"attribute\",\n"
            + "    \"string\" : \"Changed\",\n" + "    \"attributes\" : [ {\n"
            + "      \"type\" : \"date\",\n" + "      \"string\" : \"22 APR 2007\",\n"
            + "      \"attributes\" : [ {\n" + "        \"type\" : \"attribute\",\n"
            + "        \"string\" : \"Time\",\n" + "        \"attributes\" : [ ],\n"
            + "        \"tail\" : \"21:46:14\"\n" + "      } ],\n" + "      \"tail\" : \"\"\n"
            + "    } ],\n" + "    \"tail\" : \"\"\n" + "  } ],\n"
            + "  \"tail\" : \"_P_CCINFO 1-1319\"\n" + "}";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    @Test
    void testReadNotesGl120368N1932() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes/N1932";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        @SuppressWarnings({ "java:S6126" })
        final String bodyFragment = "{\n" + "  \"type\" : \"note\",\n"
            + "  \"string\" : \"N1932\",\n" + "  \"attributes\" : [ {\n"
            + "    \"type\" : \"sourcelink\",\n" + "    \"string\" : \"S33734\",\n"
            + "    \"attributes\" : [ {\n" + "      \"type\" : \"attribute\",\n"
            + "      \"string\" : \"Note\",\n" + "      \"attributes\" : [ ],\n"
            + "      \"tail\" : \"Record originated in...\"\n" + "    } ],\n"
            + "    \"tail\" : \"\"\n" + "  }, {\n" + "    \"type\" : \"sourcelink\",\n"
            + "    \"string\" : \"S33716\",\n" + "    \"attributes\" : [ {\n"
            + "      \"type\" : \"attribute\",\n" + "      \"string\" : \"Note\",\n"
            + "      \"attributes\" : [ ],\n" + "      \"tail\" : \"Record originated in...\"\n"
            + "    } ],\n" + "    \"tail\" : \"\"\n" + "  }, {\n"
            + "    \"type\" : \"attribute\",\n" + "    \"string\" : \"Changed\",\n"
            + "    \"attributes\" : [ {\n" + "      \"type\" : \"date\",\n"
            + "      \"string\" : \"8 MAR 2008\",\n" + "      \"attributes\" : [ {\n"
            + "        \"type\" : \"attribute\",\n" + "        \"string\" : \"Time\",\n"
            + "        \"attributes\" : [ ],\n" + "        \"tail\" : \"22:45:54\"\n"
            + "      } ],\n" + "      \"tail\" : \"\"\n" + "    } ],\n" + "    \"tail\" : \"\"\n"
            + "  } ],\n" + "  \"tail\" : \"Prince Philip, born at Mon Repos, Corfu 10"
            + " June 1921, renounced his rights to the throne of Greece"
            + " and was naturalised in Great Britain taking the surname of"
            + " Mountbatten 28 Feb. 1947, created Duke of Edinburgh, Earl"
            + " of Merioneth and Baron Greenwich in the Peerage of the"
            + " United Kingdom 19 Nov. 1947, granted the style and dignity"
            + " of Prince of the United Kingdom of Great Britain and"
            + " Northern Ireland 22 Feb. 1957, Knight of the Garter"
            + " (1947), Knight of the Thistle (1952), Order of Merit"
            + " (1968), Privy Councillor (1951) and Privy Councillor of"
            + " Canada (1957) Admiral of the of the Fleet, Field Marshal"
            + " and Marshal of Royal Air Force, etc; m Westminster Abbey,"
            + " London 20 Nov. 1947, Elizabeth II, Queen of Great Britain"
            + " and Northern Ireland (born at 17 Bruton St, London W1 21"
            + " April 1926), and has issue (see GREAT BRITAIN - Almanach"
            + " de Gotha 1998; 1999; 2000).\"\n" + "}";

        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).startsWith(bodyFragment);
    }

    @Test
    void testReadNotesGl120368Xyzzy() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368"
            + "/notes/Xyzzy";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testCreateNotesSimple() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiNote reqBody = ApiNote.builder().type("note").string("").tail("testing").build();
        final EntityExchangeResult<ApiNote> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiNote.class);
        final ApiNote resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getTail()).isEqualTo(reqBody.getTail());
    }

    @Test
    void testDeleteNote() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes";
        final ApiNote reqBody = ApiNote.builder()
            .type("note")
            .string("")
            .tail("this is a note")
            .build();
        final EntityExchangeResult<ApiNote> noteEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiNote.class);
        assertThat(noteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiNote resBody = noteEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiNote> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiNote.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiNote> postDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiNote.class);
        assertThat(postDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testDeleteNoteNotFound() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/notes/XXXXXXX";
        final EntityExchangeResult<ApiNote> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiNote.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testDeleteNoteDatabaseNotFound() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/XYZZY/notes/SUBM1";
        final EntityExchangeResult<ApiNote> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiNote.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(url))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testUpdateNoteWithNote() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/notes";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(
            ApiAttribute.builder().type("attribute").string("Note").tail("first note").build());
        final ApiNote reqBody = ApiNote.builder()
            .type("note")
            .string("")
            .attributes(attributes)
            .tail("Top level note")
            .build();
        final EntityExchangeResult<ApiNote> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiNote.class);
        final ApiNote resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        final ApiNote putRequestBody = resBody.toBuilder().attribute(aNote).build();
        final EntityExchangeResult<ApiNote> putResponseEntity = restTestClient.put()
            .uri(URI.create(url + "/" + putRequestBody.getString()))
            .body(putRequestBody)
            .exchange()
            .returnResult(ApiNote.class);
        assertEquals(java.util.Optional.ofNullable(putResponseEntity.getResponseBody())
            .map(b -> b.getAttributes().get(1))
            .orElse(null), aNote, "attribute should be present");
    }
}
