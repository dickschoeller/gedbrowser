package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.test.LoginTestHelper;
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
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    /**
     * Creates a login session for the test.
     */
    private LoginTestHelper helper;

    /**
     * Initialize the login helper.
     */
    @Before
    public void before() {
        helper = new LoginTestHelper(testRestTemplate, port);
    }

    /**
     * @throws RestClientException should be never
     * @throws URISyntaxException should be never
     */
    @Test
    public final void testGetPersonsGl120368()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.exchange(url, HttpMethod.GET, helper.adminEntity(), String.class);
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

    /**
     * @throws RestClientException should be never
     * @throws URISyntaxException should be never
     */
    @Test
    public final void testGetPersonsGl120368NotLoggedIn()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I6\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Reginald Amass /Williams/\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().substring(0, TRUNCATE_LENGTH))
                .startsWith(bodyFragment);
    }

    /**
     * @throws RestClientException should be never
     * @throws URISyntaxException should be never
     */
    @Test
    public final void testGetPersonsMiniSchoeller() throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.exchange(url, HttpMethod.GET, helper.adminEntity(), String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I1\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Melissa Robinson/Schoeller/\",\n"
                + "    \"attributes\" : [ {\n";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerNotLoggedIn() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String bodyFragment =
                "[ {\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I7\",\n"
                + "  \"attributes\" : [ {\n"
                + "    \"type\" : \"name\",\n"
                + "    \"string\" : \"Arnold/Robinson/\",\n"
                + "    \"attributes\" : [ ],\n"
                + "    \"tail\" : \"\"\n"
                + "  },";
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).startsWith(bodyFragment);
    }

    /**
     * @throws RestClientException should be never
     * @throws URISyntaxException should be never
     */
    @Test
    public final void testGetPersonsMiniSchoellerI2() throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I2";
        final ResponseEntity<String> entity =
                testRestTemplate.exchange(url, HttpMethod.GET, helper.adminEntity(), String.class);
        final String bodyFragment =
                "{\n"
                + "  \"type\" : \"person\",\n"
                + "  \"string\" : \"I2\",\n"
                + "  \"attributes\" : [ {\n"
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
    public final void testGetPersonsMiniSchoellerXyzzy() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller"
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
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final HttpHeaders headers = helper.adminLogin();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson.Builder builder = new ApiPerson.Builder().build();
        final ApiPerson reqBody = new ApiPerson(builder);
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
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final HttpHeaders headers = helper.adminLogin();
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
     * @return the newly created person
     */
    private ApiPerson createRJS() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("")
                .add(new ApiAttribute("name", "Richard/Schoeller/", ""))
                .add(new ApiAttribute("attribute", "Sex", "M"))
                .surname("Schoeller")
                .indexName("Schoeller, Richard")
                .build();
        return new ApiPerson(builder);
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createAlexander() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("")
                .add(new ApiAttribute("name", "Alexander/Romanov/", ""))
                .add(new ApiAttribute("attribute", "Sex", "M"))
                .surname("Romanov")
                .indexName("Romanov, Alexander")
                .build();
        return new ApiPerson(builder);
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createAlexandra() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("")
                .add(new ApiAttribute("name", "Alexandra/Romanov/", ""))
                .add(new ApiAttribute("attribute", "Sex", "F"))
                .surname("Romanov")
                .indexName("Romanov, Alexandra")
                .build();
        return new ApiPerson(builder);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeletePerson()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = helper.adminLogin();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
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
                .exchange(deleteUrl, HttpMethod.DELETE, helper.adminEntity(), String.class);
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
    public final void testDeletePersonNotAdmin()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = helper.adminLogin();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
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
                .exchange(deleteUrl, HttpMethod.DELETE, helper.userEntity(), String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteSpouseLinkedPerson()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = helper.adminLogin();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        then(personEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getBody();
        final String id = resBody.getString();

        final String childUrl = url + "/"
                + resBody.getString() + "/children";
        final ApiPerson childReqBody = createAlexander();
        final HttpEntity<ApiPerson> childReq =
                new HttpEntity<>(childReqBody, headers);
        final ResponseEntity<ApiPerson> childEntity = testRestTemplate
                .postForEntity(new URI(childUrl), childReq, ApiPerson.class);
        then(childEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ApiPerson child = childEntity.getBody();

        final String fam = child.getFamc().get(0).getString();
        logger.info(
                "The new child, " + child.getString() + ", in family " + fam);

        final ApiPerson p2 = createAlexandra();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p2,
                headers);
        final String familiesUrl = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/families/";
        final String fspUrl = familiesUrl + fam + "/spouses";
        logger.info("fspUrl: " + fspUrl);
        final ResponseEntity<ApiPerson> pe = testRestTemplate.exchange(
                new URI(fspUrl), HttpMethod.POST,
                personReq, ApiPerson.class);
        then(pe.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ApiPerson gotP2 = pe.getBody();
        then(fam).isEqualTo(gotP2.getFams().get(0).getString());

        final String deleteUrl = url + "/" + id;
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiPerson.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, helper.adminEntity(), String.class);
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
    public final void testDeleteChildLinkedPerson()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = helper.adminLogin();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // Create a person.
        // We want to be sure we know the structure of the person
        // we are modifying.
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final ApiPerson reqBody = createRJS();
        final HttpEntity<ApiPerson> req =
                new HttpEntity<>(reqBody, headers);
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .postForEntity(new URI(url), req, ApiPerson.class);
        then(personEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Capture information about new person.
        final ApiPerson resBody = personEntity.getBody();

        final String childUrl = url + "/"
                + resBody.getString() + "/children";
        final ApiPerson childReqBody = createAlexander();
        final HttpEntity<ApiPerson> childReq =
                new HttpEntity<>(childReqBody, headers);
        final ResponseEntity<ApiPerson> childEntity = testRestTemplate
                .postForEntity(new URI(childUrl), childReq, ApiPerson.class);
        then(childEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ApiPerson child = childEntity.getBody();

        final String fam = child.getFamc().get(0).getString();
        final String childId = child.getString();
        logger.info(
                "The new child, " + childId + ", in family " + fam);

        final ApiPerson p2 = createAlexandra();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p2,
                headers);
        final String familiesUrl = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/families/";
        final String fspUrl = familiesUrl + fam + "/spouses";
        logger.info("fspUrl: " + fspUrl);
        final ResponseEntity<ApiPerson> pe = testRestTemplate.exchange(
                new URI(fspUrl), HttpMethod.POST,
                personReq, ApiPerson.class);
        then(pe.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ApiPerson gotP2 = pe.getBody();
        then(fam).isEqualTo(gotP2.getFams().get(0).getString());

        final String deleteUrl = url + "/" + childId;
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(deleteUrl, ApiPerson.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(deleteUrl, HttpMethod.DELETE, helper.adminEntity(), String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ResponseEntity<ApiPerson> entityAfterDelete = testRestTemplate
                .getForEntity(deleteUrl, ApiPerson.class);
        then(entityAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeletePersonNotFound()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons/XXXXXXX";
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiPerson.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(url, HttpMethod.DELETE, helper.adminEntity(), String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testDeleteSubmitterDatabaseNotFound()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/XYZZY/persons/SUBM1";
        final ResponseEntity<ApiPerson> preDeleteEntity = testRestTemplate
                .getForEntity(url, ApiPerson.class);
        then(preDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        final ResponseEntity<String> deleteEntity = testRestTemplate
                .exchange(url, HttpMethod.DELETE, helper.adminEntity(), String.class);
        then(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testUpdatePersonWithNote()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/persons";
        final HttpHeaders headers = helper.adminLogin();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createRJS();
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
                new HttpEntity<ApiPerson>(resBody, helper.adminLogin());
        final ResponseEntity<ApiPerson> putResponseEntity =
                testRestTemplate.exchange(
                url + "/" + resBody.getString(),
                HttpMethod.PUT, putRequestEntity, ApiPerson.class);
        assertEquals("attribute should be present", aNote,
                putResponseEntity.getBody().getAttributes().get(2));
    }

    /**
     * @throws URISyntaxException if the URL syntax is hosed
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddSpouse()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I1/spouses";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createAlexander();
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
     * @throws URISyntaxException if the URL syntax is hosed
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddParent()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I1/parents";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createAlexander();
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
     * @throws URISyntaxException if the URL syntax is hosed
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddParent2()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I2/parents";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createAlexandra();
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
     * @throws URISyntaxException if the URL syntax is hosed
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddChild()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I9/children";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createAlexander();
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
     * @throws URISyntaxException if the URL syntax is hosed
     * @throws RestClientException if there is a problem with rest
     */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddChild2()
            throws RestClientException, URISyntaxException {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/mini-schoeller/persons/I10/children";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ApiPerson reqBody = createAlexandra();
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
}
