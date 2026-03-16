package org.schoellerfamily.gedbrowser.api.test;

import org.schoellerfamily.gedbrowser.api.controller.ApplicationInfoImpl;
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
     * Creates and configures the builder bean.
     *
     * @return the configured builder bean
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Creates and configures the rest client bean.
     *
     * @param builder the builder
     * @return the configured rest client bean
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
