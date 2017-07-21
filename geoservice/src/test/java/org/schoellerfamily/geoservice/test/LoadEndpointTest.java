package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoadEndpointTest {
    // The assert check is suppressed because using BDD assertions, which don't
    // match the PMD check.
    /** */
    private static final int FIFTEEN_MINUTES = 15 * 60 * 1000;

    /** */
    private static final int THIRTY_SECONDS = 30 * 1000;

    /** */
    private static final int TWO_SECONDS = 2 * 1000;

    /**
     * Management port.
     */
    @Value("${local.management.port}")
    private int mgt;

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /** */
    @Test
    public final void testAReturn200WhenSendingRequestToClearEndpoint() {
        final ClientHttpRequestFactory requestFactory = testRestTemplate
                .getRestTemplate().getRequestFactory();
        final OkHttpClientHttpRequestFactory rf =
                (OkHttpClientHttpRequestFactory) requestFactory;
        rf.setConnectTimeout(TWO_SECONDS);
        rf.setReadTimeout(THIRTY_SECONDS);
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/clear", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("0 locations in the cache");
    }

    /** */
    @Test
    public final void testBReturn200WhenSendingRequestToLoadEndpoint() {
        final ClientHttpRequestFactory requestFactory = testRestTemplate
                .getRestTemplate().getRequestFactory();
        final OkHttpClientHttpRequestFactory rf =
                (OkHttpClientHttpRequestFactory) requestFactory;
        rf.setConnectTimeout(TWO_SECONDS);
        rf.setReadTimeout(THIRTY_SECONDS);
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/load", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("917 locations in the cache");
    }

    /** */
    @Test
    public final void testCReturn200WhenSendingRequestToClearEndpoint() {
        final ClientHttpRequestFactory requestFactory = testRestTemplate
                .getRestTemplate().getRequestFactory();
        final OkHttpClientHttpRequestFactory rf =
                (OkHttpClientHttpRequestFactory) requestFactory;
        rf.setConnectTimeout(TWO_SECONDS);
        rf.setReadTimeout(THIRTY_SECONDS);
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/clear", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("0 locations in the cache");
    }

    /** */
    @Test
    public final void testDReturn200WhenSendingRequestToLoadAndFindEndpoint() {
        final ClientHttpRequestFactory requestFactory = testRestTemplate
                .getRestTemplate().getRequestFactory();
        final OkHttpClientHttpRequestFactory rf =
                (OkHttpClientHttpRequestFactory) requestFactory;
        rf.setConnectTimeout(TWO_SECONDS);
        rf.setReadTimeout(FIFTEEN_MINUTES);
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/loadAndFind", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Load complete")
                .contains("917 locations in the cache");
    }
}
