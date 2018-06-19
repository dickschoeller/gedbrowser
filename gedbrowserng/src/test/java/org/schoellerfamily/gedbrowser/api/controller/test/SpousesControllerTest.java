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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
public class SpousesControllerTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    @Before
    public void setUp() {
        helper = new ControllerTestHelper(port, testRestTemplate);
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testLinkSpouse()
            throws RestClientException, URISyntaxException {
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson p2 = helper.createPerson();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p1,
                helper.getHeaders());
        final ResponseEntity<ApiPerson> parentEntity = testRestTemplate
                .exchange(
                        new URI(helper.getPersonsUrl() + "/" + p2.getString()
                                + "/spouses"),
                        HttpMethod.PUT, personReq, ApiPerson.class);
        final ApiPerson gotP1 = parentEntity.getBody();
        then(gotP1.getString()).isEqualTo(p1.getString());
        then(gotP1.getFams().size()).isEqualTo(1);
        final ApiPerson gotP2 = helper.getPerson(p2);
        then(gotP2.getFams().size()).isEqualTo(1);
        assertEquals("check ids", gotP1.getFams().get(0).getString(),
                gotP2.getFams().get(0).getString());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testLinkSpouseInFamily()
            throws RestClientException, URISyntaxException {
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamc().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p2,
                helper.getHeaders());
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .exchange(
                        new URI(helper.getFamiliesUrl() + "/" + fam
                                + "/spouses"),
                        HttpMethod.PUT, personReq, ApiPerson.class);
        final ApiPerson gotP2 = personEntity.getBody();
        then(gotP2.getString()).isEqualTo(p2.getString());
        then(gotP2.getFams().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        then(gotP1.getFams().size()).isEqualTo(1);
        assertEquals("check ids", gotP1.getFams().get(0).getString(),
                gotP2.getFams().get(0).getString());
    }

    /**
     * @throws RestClientException if we can't talk to rest server
     * @throws URISyntaxException if there is a problem with the URL
     */
    @Test
    public final void testUnlinkSpouseInFamily()
            throws RestClientException, URISyntaxException {
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamc().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final HttpEntity<ApiPerson> personReq = new HttpEntity<>(p2,
                helper.getHeaders());
        final ResponseEntity<ApiPerson> personEntity = testRestTemplate
                .exchange(
                        new URI(helper.getFamiliesUrl() + "/" + fam
                                + "/spouses"),
                        HttpMethod.PUT, personReq, ApiPerson.class);
        final ApiPerson gotP2 = personEntity.getBody();
        then(gotP2.getString()).isEqualTo(p2.getString());
        then(gotP2.getFams().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        then(gotP1.getFams().size()).isEqualTo(1);

        testRestTemplate.delete(new URI(helper.getFamiliesUrl() + "/" + fam
                + "/spouses/" + gotP1.getString()));
        final ApiPerson gotP1again = helper.getPerson(gotP1);
        final ApiPerson gotP2again = helper.getPerson(gotP2);
        then(gotP1again.getFams().size()).isEqualTo(0);
        assertEquals("check ids", gotP2again.getFams().get(0).getString(), fam);
    }

    private ApiPerson createChildOfParent(final ApiPerson parent)
            throws URISyntaxException {
        final String childUrl = helper.getPersonsUrl() + "/"
                + parent.getString()
                + "/children";
        logger.info("childUrl: " + childUrl);
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
