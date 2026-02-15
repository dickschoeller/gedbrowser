package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

    /**
     * Provides test parameters for actuator endpoints.
     *
     * @return stream of arguments containing endpoint path and expected cache message
     */
    private static Stream<Arguments> actuatorEndpointProvider() {
        return Stream.of(
            Arguments.of("clear", "0 locations in the cache"),
            Arguments.of("load", "917 locations in the cache"),
            Arguments.of("clear", "0 locations in the cache"),
            Arguments.of("loadAndFind", "917 locations in the cache")
        );
    }

    /**
     * Test that actuator endpoints return 200 and expected cache messages.
     *
     * @param endpoint the actuator endpoint to test
     * @param expectedCacheMessage the expected cache message in response
     */
    @ParameterizedTest
    @MethodSource("actuatorEndpointProvider")
    void testActuatorEndpointReturns200AndExpectedMessage(
            final String endpoint, final String expectedCacheMessage) {
        final EntityExchangeResult<String> entity = restTestClient.get()
                .uri("http://localhost:" + this.mgt + "/actuator/" + endpoint)
                .exchange()
                .returnResult(String.class);

        assertThat(entity).satisfies(e -> {
            assertThat(e.getStatus()).isEqualTo(HttpStatus.OK);
            assertThat(e.getResponseBody()).contains("Load complete", expectedCacheMessage);
        });
    }
}
