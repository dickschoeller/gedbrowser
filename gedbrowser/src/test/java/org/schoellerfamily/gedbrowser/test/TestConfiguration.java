package org.schoellerfamily.gedbrowser.test;

import org.schoellerfamily.gedbrowser.controller.ApplicationInfoImpl;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;



/**
 * Configures components related to test.
 *
 * @author Richard Schoeller
 */
@Configuration
@ComponentScan(basePackageClasses = { GeoServiceClient.class, ApplicationInfoImpl.class })
public class TestConfiguration {
    /** */
    private static final int MINUTE_IN_MILLIS = 60000;

    /**
     * Create a RestClient builder.
     *
     * @return the builder
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Create a RestClient with timeouts suitable for testing.
     *
     * @param builder the builder
     * @return the rest client
     */
    @Bean
    public RestClient restClient(final RestClient.Builder builder) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(MINUTE_IN_MILLIS);
        factory.setReadTimeout(MINUTE_IN_MILLIS);
        return builder
            .requestFactory(factory)
            .build();
    }
}
