package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(
    classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
class LoginControllerTest {

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private RestTestClient restTestClient;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    void testLoginEndpointOK() {
        final String referer = "/gedbrowser/living?db=gl120368";
        final String refererUrl = "http://localhost:" + port + referer;
        final String url = "http://localhost:" + port + "/gedbrowser/login";
        final RestTemplate restTemplate = new RestTemplate();
        setReferer(restTemplate, refererUrl);
        final ResponseEntity<String> entity =
                restTemplate.getForEntity(url, String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, ResponseEntity::getStatusCode)
            .extracting(ResponseEntity::getBody)
                .asString().contains(
                    "<title>Login to GedBrowser</title>",
                    "<input type=\"hidden\" name=\"targetUrl\" value=\""
                        + refererUrl + "\"/>");
    }

    /** */
    @Test
    void testLogoutEndpointOK() {
        final String referer = "/gedbrowser/living?db=gl120368";
        final String refererUrl = "http://localhost:" + port + referer;
        final String url = "http://localhost:" + port + "/gedbrowser/logout";
        final RestTemplate restTemplate = new RestTemplate();
        setReferer(restTemplate, refererUrl);
        final ResponseEntity<String> entity =
                restTemplate.getForEntity(url, String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, ResponseEntity::getStatusCode)
            .extracting(ResponseEntity::getBody)
                .asString().contains(
                    "<title>Login to GedBrowser</title>",
                    "<input type=\"hidden\" name=\"targetUrl\" value=\""
                        + refererUrl + "\"/>");
    }

    /** */
    @Test
    void testLoginEndpointNoReferrer() {
        final String refererUrl = "/gedbrowser/person?db=schoeller&amp;id=I1";
        final String url = "http://localhost:" + port + "/gedbrowser/login";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Login to GedBrowser</title>",
                    "<input type=\"hidden\" name=\"targetUrl\" value=\""
                        + refererUrl + "\"/>");
    }

    /** */
    @Test
    void testLogoutEndpointNoReferrer() {
        final String refererUrl = "/gedbrowser/person?db=schoeller&amp;id=I1";
        final String url = "http://localhost:" + port + "/gedbrowser/logout";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK, EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Login to GedBrowser</title>",
                    "<input type=\"hidden\" name=\"targetUrl\" value=\""
                        + refererUrl + "\"/>");
    }

    /**
     * Add an interceptor to insert the referer header.
     *
     * @param restTemplate the rest template
     * @param refererUrl the URL to add
     */
    private void setReferer(final RestTemplate restTemplate,
            final String refererUrl) {
        restTemplate.setInterceptors(
                List.of(new RefererInterceptor(refererUrl)));
    }
}
