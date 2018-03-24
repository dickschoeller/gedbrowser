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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
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
public class NoteControllerTest {
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
    public final void testReadNotesGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"note\",\n"
                + "  \"string\" : \"N1\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"_P_CCINFO 1-1319\"\n"
                + "}, {\n"
                + "  \"type\" : \"note\",\n"
                + "  \"string\" : \"N2\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"_P_CCINFO 1-1319\"\n"
                + "}, {\n";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testReadNotesGl120368N13() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes/N13";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"note\",\n"
                + "  \"string\" : \"N13\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"_P_CCINFO 1-1319\\n"
                + "Suffolk County Record Office, Parish Register, St Mary"
                + " Magdalene, Debenham, Suffolk, England, Baptisms 1813-\\n"
                + "1864 (FB47/D1/10), Samuel son of William Amass & Marianne"
                + " Thurston alias Amass of Debenham Butcher was\\n"
                + "baptised by George Smalley vicar on 23/3/1828.\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testReadNotesGl120368N66() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes/N66";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"note\",\n"
                + "  \"string\" : \"N66\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Changed\",\n"
                + "    \"attributes\" : [ {\n"
                + "      \"type\" : \"date\",\n"
                + "      \"string\" : \"22 APR 2007\",\n"
                + "      \"attributes\" : [ {\n"
                + "        \"type\" : \"attribute\",\n"
                + "        \"string\" : \"Time\",\n"
                + "        \"attributes\" : [ ],\n"
                + "        \"tail\" : \"21:46:14\"\n"
                + "      } ],\n"
                + "      \"tail\" : \"\"\n"
                + "    } ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ],\n"
                + "  \"tail\" : \"_P_CCINFO 1-1319\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testReadNotesGl120368N1932() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes/N1932";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"note\",\n"
                + "  \"string\" : \"N1932\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S33734\",\n"
                + "    \"attributes\" : [ {\n"
                + "      \"type\" : \"attribute\",\n"
                + "      \"string\" : \"Note\",\n"
                + "      \"attributes\" : [ ],\n"
                + "      \"tail\" : \"Record originated in...\"\n"
                + "    } ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S33716\",\n"
                + "    \"attributes\" : [ {\n"
                + "      \"type\" : \"attribute\",\n"
                + "      \"string\" : \"Note\",\n"
                + "      \"attributes\" : [ ],\n"
                + "      \"tail\" : \"Record originated in...\"\n"
                + "    } ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Changed\",\n"
                + "    \"attributes\" : [ {\n"
                + "      \"type\" : \"date\",\n"
                + "      \"string\" : \"8 MAR 2008\",\n"
                + "      \"attributes\" : [ {\n"
                + "        \"type\" : \"attribute\",\n"
                + "        \"string\" : \"Time\",\n"
                + "        \"attributes\" : [ ],\n"
                + "        \"tail\" : \"22:45:54\"\n"
                + "      } ],\n"
                + "      \"tail\" : \"\"\n"
                + "    } ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ],\n"
                + "  \"tail\" : \"Prince Philip, born at Mon Repos, Corfu 10"
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
                + " de Gotha 1998; 1999; 2000).\"\n"
                + "}";

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testReadNotesGl120368Xyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368"
                + "/notes/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateNotesSimple()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiNote reqBody = new ApiNote("note", "", "testing");
        final HttpEntity<ApiNote> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiNote> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiNote.class);
        final ApiNote resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getTail()).isEqualTo(reqBody.getTail());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteNote()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes";
        final ApiNote reqBody = new ApiNote("note", "", "this is a note");
        final HttpEntity<ApiNote> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiNote> noteEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiNote.class);
        then(noteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new note.
        final ApiNote resBody = noteEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiNote> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiNote.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiNote> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiNote.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteNoteNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes/XXXXXXX";
        final ResponseEntity<ApiNote> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiNote.class);
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
    public final void testDeleteNoteDatabaseNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/XYZZY/notes/SUBM1";
        final ResponseEntity<ApiNote> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiNote.class);
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
    public final void testUpdateNoteWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/v1/dbs/gl120368/notes";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
        final ApiNote reqBody =
                new ApiNote("note", "", attributes, "Top level note");
        final HttpEntity<ApiNote> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiNote> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiNote.class);
        final ApiNote resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                new ApiAttribute("attribute", "Note", "this is a note");
        resBody.getAttributes().add(
                aNote);
        final HttpEntity<ApiNote> putRequestEntity =
                new HttpEntity<ApiNote>(resBody);
        final ResponseEntity<ApiNote> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiNote.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(1));
    }
}
