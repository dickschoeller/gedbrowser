package org.schoellerfamily.gedbrowser.api.controller.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Dick Schoeller
 */
public class ControllerTestHelper {
    /** */
    private final TestRestTemplate testRestTemplate;
    /** */
    private final String baseUrl;
    /** */
    private final String personsUrl;
    /** */
    private final String familiesUrl;
    /** */
    private final HttpHeaders headers;
    /** */
    private final ApiPerson.Builder builder;

    /**
     * @param port the port for this test
     */
    ControllerTestHelper(final int port, final TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
        baseUrl = "http://localhost:" + port + "/gedbrowserng/v1/dbs/gl120368/";
        personsUrl = baseUrl + "persons";
        familiesUrl = baseUrl + "families";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        builder = new ApiPerson.Builder().build();
    }

    /**
     * @return the base persons URL
     */
    public String getPersonsUrl() {
        return personsUrl;
    }

    /**
     * @return the base families URL
     */
    public String getFamiliesUrl() {
        return familiesUrl;
    }

    /**
     * @return the basic http headers
     */
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * @return the person builder
     */
    public ApiPerson.Builder getPersonBuilder() {
        return builder;
    }

    /**
     * @return a newly created, very simple person
     * @throws URISyntaxException if there is a problem with URL syntax
     */
    public ApiPerson createPerson() throws URISyntaxException {
        final ApiPerson person = buildPerson();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(person, headers);
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .postForEntity(new URI(personsUrl), personReq, ApiPerson.class);
        return personEntity.getBody();
    }

    /**
     * @return a new person object
     */
    public ApiPerson buildPerson() {
        return new ApiPerson(builder);
    }

    /**
     * @param person the person that we are "regetting"
     * @return the newly gotten person
     * @throws URISyntaxException if there is a problem with the URL
     */
    public ApiPerson getPerson(final ApiPerson person) throws URISyntaxException {
        final ResponseEntity<ApiPerson> entity = testRestTemplate.getForEntity(
                new URI(personsUrl + "/" + person.getString()),
                ApiPerson.class);
        return entity.getBody();
    }
}
