package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
@AutoConfigureRestTestClient
class PersonControllerTest {
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
    private ControllerTestHelper helper;

    /** */
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        helper = new ControllerTestHelper(port, restTestClient);
        headers = helper.getHeaders();
    }

    /**
     * @throws RestClientException should be never
     */
    @Test
    void testGetPersonsGl120368() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"person\"",
            "\"string\" : \"I1\"",
            "\"attributes\" : [ {", "\"type\" : \"name\"",
            "\"string\" : \"Living /Williams/\"",
            "\"attributes\" : [ ]", "\"tail\" : \"\"");
    }

    /**
     * @throws RestClientException should be never
     */
    @Test
    void testGetPersonsGl120368NotLoggedIn() throws RestClientException {
        final String url =
            "http://localhost:" + port + "/gedbrowserng/v1/dbs/mini-schoeller/persons";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"person\"",
            "\"string\" : \"I7\"",
            "\"attributes\" : [ {", "\"type\" : \"name\"",
            "\"string\" : \"Arnold/Robinson/\"",
            "\"attributes\" : [ ]",
            "\"tail\" : \"\"");
    }

    /**
     * @throws RestClientException should be never
     */
    @Test
    void testGetPersonsMiniSchoeller() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(Optional.ofNullable(entity.getResponseBody()).orElse("")).contains(
            "\"type\" : \"person\"",
            "\"string\" : \"I1\"",
            "\"attributes\" : [ {",
            "\"type\" : \"name\"",
            "\"string\" : \"Melissa Robinson/Schoeller/\"",
            "\"attributes\" : [ {");
    }

    /** */
    @Test
    void testGetPersonsMiniSchoellerNotLoggedIn() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"person\"",
            "\"string\" : \"I7\"",
            "\"attributes\" : [ {",
            "\"type\" : \"name\"",
            "\"string\" : \"Arnold/Robinson/\"",
            "\"attributes\" : [ ]",
            "\"tail\" : \"\"");
    }

    /**
     * @throws RestClientException should be never
     */
    @Test
    void testGetPersonsMiniSchoellerI2() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I2";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"person\"",
            "\"string\" : \"I2\"",
            "\"attributes\" : [ {",
            "\"type\" : \"name\"",
            "\"string\" : \"Richard John/Schoeller/\"",
            "\"attributes\" : [ ]",
            "\"tail\" : \"\"");
    }

    /** */
    @Test
    void testGetPersonsMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/mini-schoeller"
            + "/persons/Xyzzy";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testCreatePersonsSimple() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = ApiPerson.builder().type("person").build();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiPerson resBody = entity.getResponseBody();
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testCreatePersonsWithName() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createRJS() {
        return ApiPerson.builder()
            .string("")
            .type("person")
            .attribute(
                ApiAttribute.builder().type("name").string("Richard/Schoeller/").tail("").build())
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("M").build())
            .surname("Schoeller")
            .indexName("Schoeller, Richard")
            .build();
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createAlexander() {
        return ApiPerson.builder()
            .string("")
            .type("person")
            .attribute(
                ApiAttribute.builder().type("name").string("Alexander/Romanov/").tail("").build())
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("M").build())
            .surname("Romanov")
            .indexName("Romanov, Alexander")
            .build();
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createAlexandra() {
        return ApiPerson.builder()
            .string("")
            .type("person")
            .attribute(
                ApiAttribute.builder().type("name").string("Alexandra/Romanov/").tail("").build())
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("F").build())
            .surname("Romanov")
            .indexName("Romanov, Alexandra")
            .build();
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeletePerson() throws RestClientException {

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(personEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiPerson> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiPerson> postDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(postDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeletePersonNotAdmin() throws RestClientException {

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(personEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiPerson> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(helper.userLogin(port, restTestClient)))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteSpouseLinkedPerson() throws RestClientException {

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(personEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getResponseBody();
        final String id = resBody.getString();

        final String childUrl = url + "/" + resBody.getString() + "/children";
        final ApiPerson childReqBody = createAlexander();
        final EntityExchangeResult<ApiPerson> childEntity = restTestClient.post()
            .uri(URI.create(childUrl))
            .headers(h -> h.addAll(headers))
            .contentType(MediaType.APPLICATION_JSON)
            .body(childReqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(childEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiPerson child = childEntity.getResponseBody();

        final String fam = child.getFamcs().get(0).getString();
        log.info("The new child, {}, in family {}", child.getString(), fam);

        final ApiPerson p2 = createAlexandra();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p2, headers);
        final String familiesUrl = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/";
        final String fspUrl = familiesUrl + fam + "/spouses";
        log.info("fspUrl: {}", fspUrl);
        final EntityExchangeResult<ApiPerson> pe = restTestClient.post()
            .uri(URI.create(fspUrl))
            .headers(h -> h.addAll(headers))
            .contentType(MediaType.APPLICATION_JSON)
            .body(personReq.getBody())
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(pe.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiPerson gotP2 = pe.getResponseBody();
        assertThat(fam).isEqualTo(gotP2.getFamss().get(0).getString());

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiPerson> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiPerson> postDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(postDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeleteChildLinkedPerson() throws RestClientException {

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(personEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getResponseBody();

        final String childUrl = url + "/" + resBody.getString() + "/children";
        final ApiPerson childReqBody = createAlexander();
        final EntityExchangeResult<ApiPerson> childEntity = restTestClient.post()
            .uri(URI.create(childUrl))
            .headers(h -> h.addAll(headers))
            .body(childReqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(childEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiPerson child = childEntity.getResponseBody();

        final String fam = child.getFamcs().get(0).getString();
        final String childId = child.getString();
        log.info("The new child, {}, in family {}", childId, fam);

        final ApiPerson p2 = createAlexandra();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p2, headers);
        final String familiesUrl = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/";
        final String fspUrl = familiesUrl + fam + "/spouses";
        log.info("fspUrl: {}", fspUrl);
        final EntityExchangeResult<ApiPerson> pe = restTestClient.post()
            .uri(URI.create(fspUrl))
            .headers(h -> h.addAll(headers))
            .body(personReq.getBody())
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(pe.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiPerson gotP2 = pe.getResponseBody();
        assertThat(fam).isEqualTo(gotP2.getFamss().get(0).getString());

        final String deleteUrl = url + "/" + childId;
        final EntityExchangeResult<ApiPerson> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiPerson> entityAfterDelete = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(entityAfterDelete.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testDeletePersonNotFound() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/persons/XXXXXXX";
        final EntityExchangeResult<ApiPerson> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
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
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/XYZZY/persons/SUBM1";
        final EntityExchangeResult<ApiPerson> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(preDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testUpdatePersonWithNote() throws RestClientException {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        final ApiPerson putRequestBody = resBody.toBuilder().attribute(aNote).build();
        final EntityExchangeResult<ApiPerson> putResponseEntity = restTestClient.put()
            .uri(URI.create(url + "/" + putRequestBody.getString()))
            .headers(h -> h.addAll(headers))
            .contentType(MediaType.APPLICATION_JSON)
            .body(putRequestBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertEquals(aNote,
            Optional.ofNullable(putResponseEntity.getResponseBody())
                .map(b -> b.getAttributes().get(2))
                .orElse(null),
            "attribute should be present");
    }

    /**
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    void testGetPersonsMiniSchoellerI2AddSpouse() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I1/spouses";
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson reqBody = createAlexander();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(customHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);

        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    void testGetPersonsMiniSchoellerI2AddParent() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I1/parents";
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson reqBody = createAlexander();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(customHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);

        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    void testGetPersonsMiniSchoellerI2AddParent2() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I2/parents";
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson reqBody = createAlexandra();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(customHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);

        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    void testGetPersonsMiniSchoellerI2AddChild() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I9/children";
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson reqBody = createAlexander();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(customHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);

        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    void testGetPersonsMiniSchoellerI2AddChild2() throws RestClientException {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I10/children";
        final HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson reqBody = createAlexandra();
        final EntityExchangeResult<ApiPerson> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(customHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .body(reqBody)
            .exchange()
            .returnResult(ApiPerson.class);

        final ApiPerson resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
        assertThat(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        assertThat(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }
}
