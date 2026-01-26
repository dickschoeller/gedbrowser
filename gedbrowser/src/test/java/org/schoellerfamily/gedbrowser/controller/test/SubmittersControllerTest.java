package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class SubmittersControllerTest implements MenuTestHelper {
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
    void testSubmittersControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submitters?db=gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "<title>Submitters - gl120368</title>",
                    "Submitters for dataset: gl120368</h2>",
                    "href=\"submitter?db=gl120368&amp;id=U1\">Phil Williams",
                    "href=\"submitter?db=gl120368&amp;id=U3\"> ",
                    "href=\"submitter?db=gl120368&amp;id=U14\">A K Robinson",
                    "href=\"submitter?db=gl120368&amp;id=U15\">Michael ",
                    "href=\"submitter?db=gl120368&amp;id=U2\">Arthur PUNCHA",
                    "href=\"submitter?db=gl120368&amp;id=U4\">Created by Fa",
                    "href=\"submitter?db=gl120368&amp;id=U5\">Paul Alger",
                    "href=\"submitter?db=gl120368&amp;id=U6\">Jim Beecroft",
                    "href=\"submitter?db=gl120368&amp;id=U7\">ken stel",
                    "href=\"submitter?db=gl120368&amp;id=U8\">Mark Willis B",
                    "href=\"submitter?db=gl120368&amp;id=U9\">Patricia Fisc",
                    "href=\"submitter?db=gl120368&amp;id=U10\">Hirt-Klooze",
                    "href=\"submitter?db=gl120368&amp;id=U11\">Lester LeMay",
                    "href=\"submitter?db=gl120368&amp;id=U12\">David A. Blo",
                    "href=\"submitter?db=gl120368&amp;id=U13\">Dave Morris",
                    getMenu("A"));
    }

    /** */
    @Test
    void testSubmittersControllerBadDataSet() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create("http://localhost:" + port + "/gedbrowser/submitters?db=XYZZY"))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");
    }
}
