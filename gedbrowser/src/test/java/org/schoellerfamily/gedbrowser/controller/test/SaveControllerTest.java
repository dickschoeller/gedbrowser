package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
public class SaveControllerTest {
    /**
     * Not sure what this is good for.
     */
    @Autowired
    private RestTestClient restTestClient;

    /**
     * The data of known users.
     */
    @Autowired
    private Users<UserImpl> users;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    void testSaveControllerOK() {
        // This makes it so anonymous access has admin. That allows testing
        // stuff that only works for admin.
        final UserImpl user = new UserImpl();
        user.setUsername("anonymousUser");
        user.setPassword("guest");
        user.addRole("ADMIN");
        users.add(user);

        final String url = "http://localhost:" + port
                + "/gedbrowser/save?db=gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains(
                    "0 HEAD",
                    "1 SOUR FAMILY_HISTORIAN",
                    "2 VERS 3.1",
                    "2 NAME Family Historian",
                    "2 CORP Calico Pie Limited",
                    "1 FILE C:\\Users\\Phil\\Documents\\W0803.GED",
                    "1 GEDC",
                    "2 VERS 5.5",
                    "2 FORM LINEAGE-LINKED",
                    "1 CHAR ANSI",
                    "1 DEST FTM");

        // Turn off anonymous admin.
        users.remove(user);
    }

    /** */
    @Test
    void testSaveControllerDatasetNotFound() {
        // This makes it so anonymous access has admin. That allows testing
        // stuff that only works for admin.
        final UserImpl user = new UserImpl();
        user.setUsername("anonymousUser");
        user.setPassword("guest");
        user.addRole("ADMIN");
        users.add(user);

        final String url = "http://localhost:" + port
                + "/gedbrowser/save?db=XYZZY";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.NOT_FOUND.value(), EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Data set not found");

        // Turn off anonymous admin.
        users.remove(user);
    }

    /** */
    @Test
    void testSaveControllerNotAdmin() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/save?db=gl120368";
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri(URI.create(url))
                .exchange()
                .returnResult(String.class);

        assertThat(entity)
            .returns(HttpStatus.OK.value(), EntityExchangeResult::getStatus)
            .extracting(EntityExchangeResult::getResponseBody)
                .asString().contains("Sorry, you aren't authorized to do that!");
    }
}
