package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings({ "PMD.JUnitTestsShouldIncludeAssert" })
@TestMethodOrder(MethodOrderer.MethodName.class)
@AutoConfigureRestTestClient
public class BackupRestoreEndpointIT {
    /**
     * Management port.
     */
    @LocalManagementPort
    private int mgt;

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private RestTestClient restTestClient;

    /** */
    @Test
    void shouldReturn200WhenSendingRequestToBackupEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + mgt + "/actuator/backup")
                .exchange()
                .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getResponseBody()).contains("backup succeeded to/from")
            .contains("locations in the cache");
    }

    /** */
    @Test
    void shouldReturn200WhenSendingRequestToRestoreEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + this.mgt + "/actuator/restore")
                .exchange()
                .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getResponseBody()).contains("restore succeeded to/from")
            .contains("locations in the cache");
    }
}
