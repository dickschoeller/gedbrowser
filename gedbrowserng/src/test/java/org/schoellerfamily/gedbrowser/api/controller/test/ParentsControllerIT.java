package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.UnitTestContainsTooManyAsserts" })
class ParentsControllerIT {

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

    @BeforeEach
    void setUp() {
        helper = new ControllerTestHelper(port, restTestClient);
    }

    @Test
    void testCreateParent() throws RestClientException {
        final ApiPerson child = helper.createPerson();
        final ApiPerson parent = createParentOfChild(child);
        log.info("fams: {}", parent.getFamss().get(0).getString());
        final ApiPerson gotChild = helper.getPerson(child);
        log.info("famc: {}", gotChild.getFamcs().get(0).getString());

        assertEquals(gotChild.getFamcs().get(0).getString(), parent.getFamss().get(0).getString(),
            "Child should be in family");
    }

    private ApiPerson createParentOfChild(final ApiPerson child) {
        final String childUrl = helper.getPersonsUrl() + "/" + child.getString() + "/parents";
        final ApiPerson childReqBody = helper.buildPerson();
        final EntityExchangeResult<ApiPerson> childEntity = restTestClient.post()
            .uri(URI.create(childUrl))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(childReqBody)
            .exchange()
            .returnResult(ApiPerson.class);
        assertThat(childEntity.getStatus())
            .isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        return childEntity.getResponseBody();
    }

    @Test
    void testLinkParent() throws RestClientException {
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson gotParent = linkParentOfChild(parent, child);
        assertThat(gotParent.getString()).isEqualTo(parent.getString());
        assertThat(gotParent.getFamss().size()).isEqualTo(1);
        final ApiPerson gotChild = helper.getPerson(child);
        assertThat(gotParent.getFamss().size()).isEqualTo(1);
        assertEquals(gotParent.getFamss().get(0).getString(),
            gotChild.getFamcs().get(0).getString(), "check ids");
    }

    private ApiPerson linkParentOfChild(final ApiPerson parent, final ApiPerson child) {
        final EntityExchangeResult<ApiPerson> parentEntity = restTestClient.put()
            .uri(URI.create(helper.getPersonsUrl() + "/" + child.getString() + "/parents"))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(parent)
            .exchange()
            .returnResult(ApiPerson.class);
        return parentEntity.getResponseBody();
    }
}
