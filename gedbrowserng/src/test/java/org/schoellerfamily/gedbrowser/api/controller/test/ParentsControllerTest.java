package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class ParentsControllerTest {

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
     * @throws RestClientException
     *             if we can't talk to rest server
     * @throws URISyntaxException
     *             if there is a problem with the URL
     */
    @Test
    public final void testCreateParent()
            throws RestClientException, URISyntaxException {
        final ApiPerson child = helper.createPerson();
        final ApiPerson parent = createParentOfChild(child);
        log.info("fams: {}", parent.getFams().get(0).getString());
        final ApiPerson gotChild = helper.getPerson(child);
        log.info("famc: {}", gotChild.getFamc().get(0).getString());

        assertEquals(gotChild.getFamc().get(0).getString(),
            parent.getFams().get(0).getString(),
            "Child should be in family");
    }

    /**
     * @param child the child that's getting a parent
     * @return the parent
     * @throws URISyntaxException if there is a problem with the URL
     */
    private ApiPerson createParentOfChild(final ApiPerson child)
            throws URISyntaxException {
        final String childUrl = helper.getPersonsUrl() + "/" + child.getString()
                + "/parents";
        final ApiPerson childReqBody = helper.buildPerson();
        final HttpEntity<ApiPerson> childReq = new HttpEntity<>(childReqBody,
                helper.getHeaders());
        final ResponseEntity<ApiPerson> childEntity = testRestTemplate
                .postForEntity(new URI(childUrl), childReq, ApiPerson.class);
        then(childEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        return childEntity.getBody();
    }

    /**
     * @throws RestClientException
     *             if we can't talk to rest server
     * @throws URISyntaxException
     *             if there is a problem with the URL
     */
    @Test
    public final void testLinkParent()
            throws RestClientException, URISyntaxException {
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson gotParent = linkParentOfChild(parent, child);
        then(gotParent.getString()).isEqualTo(parent.getString());
        then(gotParent.getFams().size()).isEqualTo(1);
        final ApiPerson gotChild = helper.getPerson(child);
        then(gotParent.getFams().size()).isEqualTo(1);
        assertEquals(gotParent.getFams().get(0).getString(),
            gotChild.getFamc().get(0).getString(),
            "check ids");
    }

    /**
     * @param parent the parent
     * @param child the child
     * @return the parent
     * @throws URISyntaxException if there is a URL problem
     */
    private ApiPerson linkParentOfChild(final ApiPerson parent,
            final ApiPerson child) throws URISyntaxException {
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(parent,
                helper.getHeaders());
        final ResponseEntity<ApiPerson> parentEntity = testRestTemplate
                .exchange(
                        new URI(helper.getPersonsUrl() + "/" + child.getString()
                                + "/parents"),
                        HttpMethod.PUT, personReq, ApiPerson.class);
        final ApiPerson gotParent = parentEntity.getBody();
        return gotParent;
    }
}