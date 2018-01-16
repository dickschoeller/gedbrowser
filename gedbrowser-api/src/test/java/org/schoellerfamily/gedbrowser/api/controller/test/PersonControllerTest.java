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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiLifespan;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
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
public class PersonControllerTest {
    /** */
    private static final int TRUNCATE_LENGTH = 500;

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
    public final void testGetPersonsGl120368() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Living /Williams/\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().substring(0, TRUNCATE_LENGTH))
                .startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Reference Number\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"1\"\n"
                + "  }, {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Melissa Robinson/Schoeller/\",\n"
                + "    \"attributes\" : [ {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I2\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"attribute\",\n"
                + "    \"string\" : \"Reference Number\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"2\"\n"
                + "  }, {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Richard John/Schoeller/\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Attributes() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/attributes";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Reference Number\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"2\"\n"
                + "}, {\n"
                + "  \"type\" : \"name\",\n"
                + "  \"string\" : \"Richard John/Schoeller/\",\n"
                + "  \"attributes\" : [ ],\n"
                + "  \"tail\" : \"\"\n"
                + "}, {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Attributes5() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/attributes/5";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Education\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"17 MAY 1980\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Rensselaer Polytechnic Institute,"
                + " Troy, New York, USA\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S4\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ],\n"
                + "  \"tail\" : \"BSEE\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Attributes99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/persons/I2/attributes/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Birth() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/Birth";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Birth\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"14 DEC 1958\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Womack Army Hospital, Fort Bragg,"
                + " Manchester, Cumberland County, North Carolina, USA\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S3\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ],\n"
                + "  \"tail\" : \"\"\n"
                + "} ]";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Birth0() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller/persons/I2/Birth/0";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"attribute\",\n"
                + "  \"string\" : \"Birth\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"date\",\n"
                + "    \"string\" : \"14 DEC 1958\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"place\",\n"
                + "    \"string\" : \"Womack Army Hospital, Fort Bragg,"
                + " Manchester, Cumberland County, North Carolina, USA\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  }, {\n"
                + "    \"type\" : \"sourcelink\",\n"
                + "    \"string\" : \"S3\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  } ],\n"
                + "  \"tail\" : \"\"\n"
                + "}";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2Birth99() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/persons/I2/Birth/99";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/mini-schoeller"
                + "/persons/Xyzzy";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreatePersonsSimple()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = new ApiPerson("person", "", "?, ?", "?",
                new ApiLifespan("", ""));
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        final ApiPerson resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
        then(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        then(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreatePersonsWithName()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createRJS();
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        final ApiPerson resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());
        then(resBody.getSurname()).isEqualTo(reqBody.getSurname());
        then(resBody.getIndexName()).isEqualTo(reqBody.getIndexName());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreatePersonsBirthAttribute()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final HttpEntity<ApiPerson> req = new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        then(personEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getBody();
        final String id = resBody.getString();

        // Create a new attribute for the person.
        // This is the real step being tested.
        final String attrUrl = url + "/" + id + "/attributes/0";
        final List<ApiAttribute> attrs = new ArrayList<>();
        attrs.add(new ApiAttribute("attribute", "Date", "1 JAN 1950"));
        final ApiAttribute attr =
                new ApiAttribute("Birth", "attribute", attrs, "");
        final HttpEntity<ApiAttribute> attrReq =
                new HttpEntity<>(attr, headers);
        final ResponseEntity<ApiAttribute> attrEntity = testRestTemplate
                .postForEntity(new URI(attrUrl), attrReq, ApiAttribute.class);
        then(attrEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check the contents of the new attribute
        final ApiAttribute newBody = attrEntity.getBody();
        then(newBody.getType()).isEqualTo("attribute");
        then(newBody.getString()).isEqualTo("Birth");
        then(newBody.getAttributes())
                .contains(new ApiAttribute("attribute", "Date", "1 JAN 1950"));

        // Now fetch the person again
        final String checkUrl = url + "/" + id;
        final ResponseEntity<ApiPerson> checkEntity =
                testRestTemplate.getForEntity(checkUrl, ApiPerson.class);
        final ApiPerson checkPerson = checkEntity.getBody();
        // Do some checks.
        then(checkPerson.getString()).isEqualTo(id);
        then(checkPerson.getIndexName()).isEqualTo("Schoeller, Richard");
        final List<ApiAttribute> attributes = checkPerson.getAttributes();
        final ApiAttribute attr1 = attributes.get(1);
        then(attr1)
                .isEqualTo(new ApiAttribute("name", "Richard/Schoeller/", ""));
        assertMatch(attributes.get(0), attr);
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createRJS() {
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("name", "Richard/Schoeller/", ""));
        return new ApiPerson("person", "", attributes, "Schoeller, Richard",
                "Schoeller", new ApiLifespan("", ""));
    }

    /**
     * @param o1 object 1
     * @param o2 object 2
     */
    private void assertMatch(final ApiObject o1, final ApiObject o2) {
        assertEquals("types don't match", o1.getType(), o2.getType());
        assertEquals("strings don't match", o1.getString(), o2.getString());
        assertEquals("attributes don't match",
                o1.getAttributes(), o2.getAttributes());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeletePerson()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("name", "Richard/Schoeller/", ""));
        final ApiPerson reqBody = new ApiPerson("person", "", attributes,
                "Richard/Schoeller/", "Schoeller", new ApiLifespan("", ""));
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        then(personEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiPerson.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiPerson> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiPerson.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeletePersonNotFound()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons/XXXXXXX";
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiPerson.class);
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
                + "/gedbrowser-api/dbs/XYZZY/persons/SUBM1";
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiPerson.class);
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
    public final void testDeletePersonAttribute()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("name", "Richard/Schoeller/", ""));
        attributes.add(new ApiAttribute("attribute", "Birth", ""));
        final ApiPerson reqBody = new ApiPerson("person", "", attributes,
                "Richard/Schoeller/", "Schoeller", new ApiLifespan("", ""));
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        then(personEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getBody();
        final String id = resBody.getString();

        final String deleteUrl = url + "/" + id + "/attributes/1";
        final ResponseEntity<ApiAttribute> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiAttribute.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiAttribute> postDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiAttribute.class);
        then(postDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        testRestTemplate.exchange(
                url + "/" + id, HttpMethod.DELETE, null, String.class);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testUpdatePersonWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowser-api/dbs/gl120368/persons";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("name", "Richard/Schoeller/", ""));
        final ApiPerson reqBody = new ApiPerson("person", "", attributes,
                "Richard/Schoeller/", "Schoeller", new ApiLifespan("", ""));
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> entity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        final ApiPerson resBody = entity.getBody();
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resBody.getType()).isEqualTo(reqBody.getType());

        final ApiAttribute aNote =
                new ApiAttribute("attribute", "Note", "this is a note");
        resBody.getAttributes().add(
                aNote);
        final HttpEntity<ApiPerson> putRequestEntity =
                new HttpEntity<ApiPerson>(resBody);
        final ResponseEntity<ApiPerson> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiPerson.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(1));
    }
}
