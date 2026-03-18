package org.schoellerfamily.geoservice.client;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

/**
 * Test-only bean configuration for {@link GeoServiceClientTest}.
 */
@TestConfiguration
public class GeoServiceClientTestConfig {
    /** Shared builder so both beans are wired to the same request factory. */
    private final RestClient.Builder builder = RestClient.builder();

    /**
     * Creates the mock server and binds its interceptor to the shared builder.
     *
     * @return the mock server
     */
    @Bean
    public MockRestServiceServer mockRestServiceServer() {
        return MockRestServiceServer.bindTo(builder).build();
    }

    /**
     * Builds the {@link RestClient} from the already-intercepted builder.
     *
     * @param server the mock server, used only to enforce bean creation order
     * @return the rest client
     */
    @Bean
    public RestClient restClient(final MockRestServiceServer server) {
        return builder.build();
    }
}
