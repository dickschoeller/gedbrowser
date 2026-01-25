package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
@AutoConfigureRestTestClient
class FamilyControllerTest {
    /** */
    @Autowired
    private RestTestClient restTestClient;

    /** */
    @LocalServerPort
    private int port;

    /** */
    private ControllerTestHelper helper;

    /** */
    private HttpHeaders headers;

    /** */
    @BeforeEach
    void setUp() {
        helper = new ControllerTestHelper(port, restTestClient);
        headers = helper.getHeaders();
    }

    /** */
    @Test
    void testGetFamiliesGl120368() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/families";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody())
            .contains(
                "\"type\" : \"family\"",
                "\"string\" : \"F1\"",
                "\"attributes\" : [ ]",
                "\"images\" : [ ]");
    }

    /** */
    @Test
    void testGetFamiliesMiniSchoeller() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/families";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"family\"", "\"string\" : \"F1\"",
            "\"string\" : \"Marriage\"", "\"type\" : \"date\"", "\"string\" : \"27 MAY 1984\"",
            "\"string\" : \"Temple Emanu-el, Providence, Providence County, Rhode Island, USA\"",
            "\"tail\" : \"The ceremony performed by Rabbi Wayne"
                + " Franklin and Cantor Ivan\\nPerlman.  The best man and"
                + " matron of honor were Dale Matcovitch\\nand Carol Robinson"
                + " Sacerdote. The witnesses were Mark\\nA. Friedman, fraternity"
                + " brother of the groom and Donald S.\\nFriedman, a friend of"
                + " bride and groom\"");
    }

    /** */
    @Test
    void testGetFamiliesGl120368F1593() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/F1593";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"family\"", "\"string\" : \"F1593\"",
            "\"attributes\" : [ {", "\"type\" : \"sourcelink\"", "\"string\" : \"S33723\"",
            "\"type\" : \"attribute\"", "\"string\" : \"Note\"", "\"attributes\" : [ ]",
            "\"tail\" : \"Record originated in...\"");
    }

    /** */
    @Test
    void testGetFamiliesMiniSchoellerF1() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/mini-schoeller/families/F1";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody()).contains("\"type\" : \"family\"", "\"string\" : \"F1\"",
            "\"string\" : \"Marriage\"", "\"string\" : \"27 MAY 1984\"",
            "\"string\" : \"Temple Emanu-el, Providence, Providence County, Rhode Island, USA\"",
            "\"tail\" : \"The ceremony performed by Rabbi Wayne"
                + " Franklin and Cantor Ivan\\nPerlman.  The best man and"
                + " matron of honor were Dale Matcovitch\\nand Carol Robinson"
                + " Sacerdote. The witnesses were Mark\\nA. Friedman, fraternity"
                + " brother of the groom and Donald S.\\nFriedman, a friend of"
                + " bride and groom\"");
    }

    /** */
    @Test
    void testGetFamiliesMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/mini-schoeller"
            + "/families/Xyzzy";
        final EntityExchangeResult<String> entity = restTestClient.get()
            .uri(URI.create(url))
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testCreateFamiliesSimple() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/families";
        final ApiFamily reqBody = ApiFamily.builder().type("family").string("").build();
        final EntityExchangeResult<ApiFamily> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiFamily.class);
        final ApiFamily resBody = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
    }

    @Test
    void testCreateFamiliesWithMarriage() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/families";
        final ApiFamily reqBody = ApiFamily.builder()
            .type("family")
            .string("")
            .attribute(ApiAttribute.builder().type("attribute").string("Marriage").tail("").build())
            .build();
        final EntityExchangeResult<ApiFamily> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiFamily.class);
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final ApiFamily resBody = entity.getResponseBody();
        assertThat(resBody.getType()).isEqualTo(reqBody.getType());
    }

    /** */
    @Test
    void testDeleteFamily() {
        // Create a family.
        // We want to be sure we know the structure of the family
        // we are modifying.
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/families";
        final ApiFamily reqBody = ApiFamily.builder().type("family").string("").build();
        final EntityExchangeResult<ApiFamily> familyEntity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(reqBody)
            .exchange()
            .returnResult(ApiFamily.class);
        assertThat(familyEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        // Capture information about new family.
        final ApiFamily resBody = familyEntity.getResponseBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final EntityExchangeResult<ApiFamily> preDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiFamily.class);
        assertThat(preDeleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<String> deleteEntity = restTestClient.delete()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(String.class);
        assertThat(deleteEntity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        final EntityExchangeResult<ApiFamily> postDeleteEntity = restTestClient.get()
            .uri(URI.create(deleteUrl))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiFamily.class);
        assertThat(postDeleteEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    /** */
    @Test
    void testDeleteFamilyNotFound() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/XXXXXXX";
        final EntityExchangeResult<ApiFamily> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiFamily.class);
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

    /** */
    @Test
    void testDeleteFamilyDatabaseNotFound() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/XYZZY/families/SUBM1";
        final EntityExchangeResult<ApiFamily> preDeleteEntity = restTestClient.get()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .exchange()
            .returnResult(ApiFamily.class);
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

    /** */
    @Test
    void testUpdateFamilyWithNote() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/families";
        final ApiFamily familyRequest = ApiFamily.builder()
            .type("family")
            .string("")
            .attribute(ApiAttribute.builder().type("attribute").string("Marriage").tail("").build())
            .attribute(ApiAttribute.builder().type("child").string("I1").tail("").build())
            .build();
        final HttpEntity<ApiFamily> req = new HttpEntity<>(familyRequest, headers);
        final EntityExchangeResult<ApiFamily> entity = restTestClient.post()
            .uri(URI.create(url))
            .headers(h -> h.addAll(headers))
            .body(req.getBody())
            .exchange()
            .returnResult(ApiFamily.class);
        final ApiFamily familyPostResponse = entity.getResponseBody();
        assertThat(entity.getStatus()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(familyPostResponse.getType()).isEqualTo(familyRequest.getType());
        assertThat(familyPostResponse.getAttributes().size()).isEqualTo(1);
        assertThat(familyPostResponse.getChildren().size()).isEqualTo(1);

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        final ApiFamily familyPutRequest = familyPostResponse.toBuilder().attribute(aNote).build();
        assertThat(familyPutRequest.getAttributes().size()).isEqualTo(2);
        final EntityExchangeResult<ApiFamily> putResponseEntity = restTestClient.put()
            .uri(URI.create(url + "/" + familyPutRequest.getString()))
            .headers(h -> h.addAll(headers))
            .body(familyPutRequest)
            .exchange()
            .returnResult(ApiFamily.class);
        final ApiFamily familyPutResponse = putResponseEntity.getResponseBody();
        final List<ApiAttribute> attributesPutResponse = familyPutResponse.getAttributes();
        log.info("Attribute list size: {}", attributesPutResponse.size());
        assertThat(attributesPutResponse.size()).isEqualTo(2);
        for (final ApiAttribute a : attributesPutResponse) {
            log.info("attribute: {} {} {}", a.getType(), a.getString(), a.getTail());
        }
        assertEquals(aNote, attributesPutResponse.get(1), "attribute should be present");
    }

    /** */
    @Test
    void testCreateSpouseInFamily() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/F1/spouses";
        final ApiPerson reqBody = createAlexandra();
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

    /** */
    @Test
    void testCreateSpouseInFamily2() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/F2/spouses";
        final ApiPerson reqBody = createAlexander();
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

    /** */
    @Test
    void testCreateChildInFamily() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/F1/children";
        final ApiPerson reqBody = createAlexandra();
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

    /** */
    @Test
    void testCreateChildInFamily2() {
        final String url = "http://localhost:" + port
            + "/gedbrowserng/v1/dbs/gl120368/families/F4/children";
        final ApiPerson reqBody = createAlexander();
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
    private ApiPerson createAlexander() {
        return ApiPerson.builder()
            .type("person")
            .string("")
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
            .type("person")
            .string("")
            .attribute(
                ApiAttribute.builder().type("name").string("Alexandra/Romanov/").tail("").build())
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("F").build())
            .surname("Romanov")
            .indexName("Romanov, Alexandra")
            .build();
    }

}
