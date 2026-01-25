package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@AutoConfigureRestTestClient
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
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
        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody())
            .contains("0 HEAD")
            .contains("1 SOUR FAMILY_HISTORIAN")
            .contains("2 VERS 3.1")
            .contains("2 NAME Family Historian")
            .contains("2 CORP Calico Pie Limited")
            .contains("1 FILE C:\\Users\\Phil\\Documents\\W0803.GED")
            .contains("1 GEDC")
            .contains("2 VERS 5.5")
            .contains("2 FORM LINEAGE-LINKED")
            .contains("1 CHAR ANSI")
            .contains("1 DEST FTM");

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

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        assertThat(entity.getResponseBody()).contains("Data set not found");

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

        final HttpStatusCode status = entity.getStatus();
        assertThat(status).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertThat(entity.getResponseBody())
            .contains("Sorry, you aren't authorized to do that!");
    }
}
