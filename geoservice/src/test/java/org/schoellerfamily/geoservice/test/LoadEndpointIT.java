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
@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert"})
@TestMethodOrder(MethodOrderer.MethodName.class)
@AutoConfigureRestTestClient
class LoadEndpointIT {
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
    void testAReturn200WhenSendingRequestToClearEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + this.mgt + "/actuator/clear")
                .exchange()
                .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getResponseBody()).contains("Load complete")
                .contains("0 locations in the cache");
    }

    /** */
    @Test
    void testBReturn200WhenSendingRequestToLoadEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + this.mgt + "/actuator/load")
                .exchange()
                .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getResponseBody()).contains("Load complete")
                .contains("917 locations in the cache");
    }

    /** */
    @Test
    void testCReturn200WhenSendingRequestToClearEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + this.mgt + "/actuator/clear")
                .exchange()
                .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getResponseBody()).contains("Load complete")
                .contains("0 locations in the cache");
    }

    /** */
    @Test
    void testDReturn200WhenSendingRequestToLoadAndFindEndpoint() {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + this.mgt + "/actuator/loadAndFind")
                .exchange()
                .returnResult(String.class);

        assertThat(entity.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getResponseBody()).contains("Load complete")
                .contains("917 locations in the cache");
    }
}
