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
@SpringBootTest(
    classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
@AutoConfigureRestTestClient
class SpousesControllerIT {
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
    void testLinkSpouse() throws RestClientException {
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson p2 = helper.createPerson();
        final EntityExchangeResult<ApiPerson> parentEntity = restTestClient.put()
            .uri(URI.create(helper.getPersonsUrl() + "/" + p2.getString() + "/spouses"))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(p1)
            .exchange()
            .returnResult(ApiPerson.class);
        final ApiPerson gotP1 = parentEntity.getResponseBody();
        assertThat(gotP1.getString()).isEqualTo(p1.getString());
        assertThat(gotP1.getFamss().size()).isEqualTo(1);
        final ApiPerson gotP2 = helper.getPerson(p2);
        assertThat(gotP2.getFamss().size()).isEqualTo(1);
        assertEquals(gotP1.getFamss().get(0).getString(), gotP2.getFamss().get(0).getString(),
            "check ids");
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testLinkSpouseInFamily() throws RestClientException {
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamcs().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.put()
            .uri(URI.create(helper.getFamiliesUrl() + "/" + fam + "/spouses"))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(p2)
            .exchange()
            .returnResult(ApiPerson.class);
        final ApiPerson gotP2 = personEntity.getResponseBody();
        assertThat(gotP2.getString()).isEqualTo(p2.getString());
        assertThat(gotP2.getFamss().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        assertThat(gotP1.getFamss().size()).isEqualTo(1);
        assertEquals(gotP1.getFamss().get(0).getString(), gotP2.getFamss().get(0).getString(),
            "check ids");
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     */
    @Test
    void testUnlinkSpouseInFamily() throws RestClientException {
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamcs().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final EntityExchangeResult<ApiPerson> personEntity = restTestClient.put()
            .uri(URI.create(helper.getFamiliesUrl() + "/" + fam + "/spouses"))
            .headers(h -> h.addAll(helper.getHeaders()))
            .body(p2)
            .exchange()
            .returnResult(ApiPerson.class);
        final ApiPerson gotP2 = personEntity.getResponseBody();
        assertThat(gotP2.getString()).isEqualTo(p2.getString());
        assertThat(gotP2.getFamss().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        assertThat(gotP1.getFamss().size()).isEqualTo(1);

        restTestClient.delete()
            .uri(URI.create(helper.getFamiliesUrl() + "/" + fam + "/spouses/" + gotP1.getString()))
            .exchange();
        final ApiPerson gotP1again = helper.getPerson(gotP1);
        final ApiPerson gotP2again = helper.getPerson(gotP2);
        assertThat(gotP1again.getFamss().size()).isEqualTo(0);
        assertEquals(gotP2again.getFamss().get(0).getString(), fam, "check ids");
    }

    /**
     * @param parent the parent
     * @return the child
     * @throws RestClientException if we can't talk to rest server
     */
    private ApiPerson createChildOfParent(final ApiPerson parent) throws RestClientException {
        final String childUrl = helper.getPersonsUrl() + "/" + parent.getString() + "/children";
        log.info("childUrl: {}", childUrl);
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
}
