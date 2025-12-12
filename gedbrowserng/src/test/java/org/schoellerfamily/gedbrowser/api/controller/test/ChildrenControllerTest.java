package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class ChildrenControllerTest {
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
    private ControllerTestHelper helper;

    /**
     * Set up some base objects.
     */
    @BeforeEach
    public void setUp() {
        helper = new ControllerTestHelper(port, testRestTemplate);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testCreateChild()
            throws RestClientException, URISyntaxException {
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = createChildOfParent(parent);
        log.info("famc: {}", child.getFamc().get(0).getString());

        final ApiPerson gotParent = helper.getPerson(parent);
        assertEquals(child.getFamc().get(0).getString(),
                gotParent.getFams().get(0).getString(), "Child should be in family");
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testLinkChildInFamily()
            throws RestClientException, URISyntaxException {
        final ApiPerson parent = helper.createPerson();

        final ApiPerson child = createChildOfParent(parent);
        String famID = child.getFamc().get(0).getString();
        log.info("famc: {}", famID);

        final ApiPerson secondChild = helper.createPerson();
        final String familiesUrl = helper.getFamiliesUrl() + "/" + famID;
        final HttpEntity<ApiPerson> personReq =
                new HttpEntity<>(secondChild, helper.getHeaders());
        testRestTemplate.put(new URI(familiesUrl + "/children"), personReq);
        final ResponseEntity<ApiFamily> familyEntity = testRestTemplate
                .getForEntity(new URI(familiesUrl), ApiFamily.class);
        assertEquals(secondChild.getString(),
                java.util.Optional.ofNullable(familyEntity.getBody())
                        .map(b -> b.getChildren().get(1).getString()).orElse(null),
                "check ID");

    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testLinkChild()
            throws RestClientException, URISyntaxException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final HttpEntity<ApiPerson> personReq =
                new HttpEntity<>(child, headers);
        final ResponseEntity<ApiPerson> childEntity = testRestTemplate.exchange(
                new URI(helper.getPersonsUrl() + "/" + parent.getString()
                        + "/children"),
                HttpMethod.PUT, personReq, ApiPerson.class);
        final ApiPerson gotChild = childEntity.getBody();
        then(gotChild.getString()).isEqualTo(child.getString());
        then(gotChild.getFamc().size()).isEqualTo(1);
        final ApiPerson gotParent = helper.getPerson(parent);
        then(gotParent.getFams().size()).isEqualTo(1);
        assertEquals(gotParent.getFams().get(0).getString(),
                gotChild.getFamc().get(0).getString(), "check ids");
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testUnlinkChild()
            throws RestClientException, URISyntaxException {
        final String familiesUrl = helper.getFamiliesUrl();
        final ApiPerson parent = helper.createPerson();

        final ApiPerson child = createChildOfParent(parent);
        log.info("famc: {}", child.getFamc().get(0).getString());


        testRestTemplate.delete(
                new URI(familiesUrl + "/" + child.getFamc().get(0).getString()
                        + "/children/" + child.getString()));
        final ApiPerson gotChild = helper.getPerson(child);
        assertEquals(0, gotChild.getFamc().size(), "not in family");
    }

    /**
     * @param parent the parent
     * @return the child
     * @throws URISyntaxException if there is a problem with the URL
     */
    private ApiPerson createChildOfParent(final ApiPerson parent)
            throws URISyntaxException {
        final String childUrl = helper.getPersonsUrl() + "/"
                + parent.getString() + "/children";
        log.info("childUrl: {}", childUrl);
        final ApiPerson childReqBody = helper.buildPerson();
        final HttpEntity<ApiPerson> childReq =
                new HttpEntity<>(childReqBody, helper.getHeaders());
        final ResponseEntity<ApiPerson> childEntity = testRestTemplate
                .postForEntity(new URI(childUrl), childReq, ApiPerson.class);
        then(childEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ApiPerson child = childEntity.getBody();
        return child;
    }
}