package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class LoginControllerTest {

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
    public final void testLoginEndpointOK() {
        final String referer = "/gedbrowser/living?db=gl120368";
        final String refererUrl = "http://localhost:" + port + referer;
        final String url = "http://localhost:" + port + "/gedbrowser/login";
        final RestTemplate restTemplate = new RestTemplate();
        setReferer(restTemplate, refererUrl);
        final ResponseEntity<String> entity =
                restTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Login to GedBrowser</title>")
            .contains("<input type=\"hidden\" name=\"targetUrl\" value=\""
                    + refererUrl + "\" />");
    }

    /** */
    @Test
    public final void testLogoutEndpointOK() {
        final String referer = "/gedbrowser/living?db=gl120368";
        final String refererUrl = "http://localhost:" + port + referer;
        final String url = "http://localhost:" + port + "/gedbrowser/logout";
        final RestTemplate restTemplate = new RestTemplate();
        setReferer(restTemplate, refererUrl);
        final ResponseEntity<String> entity =
                restTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Login to GedBrowser</title>")
            .contains("<input type=\"hidden\" name=\"targetUrl\" value=\""
                    + refererUrl + "\" />");
    }

    /** */
    @Test
    public final void testLoginEndpointNoReferrer() {
        final String refererUrl = "/gedbrowser/person?db=schoeller&amp;id=I1";
        final String url = "http://localhost:" + port + "/gedbrowser/login";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Login to GedBrowser</title>")
            .contains("<input type=\"hidden\" name=\"targetUrl\" value=\""
                    + refererUrl + "\" />");
    }

    /** */
    @Test
    public final void testLogoutEndpointNoReferrer() {
        final String refererUrl = "/gedbrowser/person?db=schoeller&amp;id=I1";
        final String url = "http://localhost:" + port + "/gedbrowser/logout";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Login to GedBrowser</title>")
            .contains("<input type=\"hidden\" name=\"targetUrl\" value=\""
                    + refererUrl + "\" />");
    }

    /**
     * Add an interceptor to insert the referer header.
     *
     * @param restTemplate the rest template
     * @param refererUrl the URL to add
     */
    private void setReferer(final RestTemplate restTemplate,
            final String refererUrl) {
        final List<ClientHttpRequestInterceptor> interceptors =
                new ArrayList<>();
        interceptors.add(new RefererInterceptor(refererUrl));
        restTemplate.setInterceptors(interceptors);
    }
}
