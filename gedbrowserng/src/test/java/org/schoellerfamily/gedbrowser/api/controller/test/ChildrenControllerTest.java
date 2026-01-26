package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
@AutoConfigureRestTestClient
class ChildrenControllerTest {
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

    /**
     * Set up some base objects.
     */
    @BeforeEach
    void setUp() {
        helper = new ControllerTestHelper(port, restTestClient);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testCreateChild() throws RestClientException {
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = createChildOfParent(parent);
        log.info("famc: {}", child.getFamcs().get(0).getString());

        final ApiPerson gotParent = helper.getPerson(parent);
        assertEquals(child.getFamcs().get(0).getString(), gotParent.getFamss().get(0).getString(),
            "Child should be in family");
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testLinkChildInFamily() throws RestClientException {
        final ApiPerson parent = helper.createPerson();

        final ApiPerson child = createChildOfParent(parent);
        final String famID = child.getFamcs().get(0).getString();
        log.info("famc: {}", famID);

        final ApiPerson secondChild = helper.createPerson();
        final String familiesUrl = helper.getFamiliesUrl() + "/" + famID;
        // Use RestTestClient to put the child into the family
        restTestClient.put()
            .uri(URI.create(familiesUrl + "/children"))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(secondChild)
            .exchange()
            .returnResult(ApiFamily.class);
        final EntityExchangeResult<ApiFamily> familyEntity = restTestClient.get()
            .uri(URI.create(familiesUrl))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiFamily.class);
        assertEquals(secondChild.getString(),
            java.util.Optional.ofNullable(familyEntity.getResponseBody())
                .map(b -> b.getChildren().get(1).getString())
                .orElse(null),
            "check ID");

    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testLinkChild() throws RestClientException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final EntityExchangeResult<ApiPerson> childEntity = restTestClient.put()
            .uri(URI.create(helper.getPersonsUrl() + "/" + parent.getString() + "/children"))
            .headers(h -> h.addAll(headers))
            .body(child)
            .exchange()
            .returnResult(ApiPerson.class);
        final ApiPerson gotChild = childEntity.getResponseBody();
        assertThat(gotChild.getString()).isEqualTo(child.getString());
        assertThat(gotChild.getFamcs().size()).isEqualTo(1);
        final ApiPerson gotParent = helper.getPerson(parent);
        assertThat(gotParent.getFamss().size()).isEqualTo(1);
        assertEquals(gotParent.getFamss().get(0).getString(),
            gotChild.getFamcs().get(0).getString(), "check ids");
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testUnlinkChild() throws RestClientException {
        final String familiesUrl = helper.getFamiliesUrl();
        final ApiPerson parent = helper.createPerson();

        final ApiPerson child = createChildOfParent(parent);
        log.info("famc: {}", child.getFamcs().get(0).getString());

        restTestClient.delete()
            .uri(URI.create(familiesUrl + "/" + child.getFamcs().get(0).getString() + "/children/"
                + child.getString()))
            .exchange();
        final ApiPerson gotChild = helper.getPerson(child);
        assertEquals(0, gotChild.getFamcs().size(), "not in family");
    }

    /**
     * @param parent the parent
     * @return the child
     */
    private ApiPerson createChildOfParent(final ApiPerson parent) {
        final String childUrl = helper.getPersonsUrl() + "/" + parent.getString() + "/children";
        log.info("childUrl: {}", childUrl);
        final ApiPerson childReqBody = helper.buildPerson();
        final EntityExchangeResult<ApiPerson> childEntity = restTestClient.post()
            .uri(URI.create(childUrl))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(childReqBody)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(childEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        return childEntity.getResponseBody();
    }
}
