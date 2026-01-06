package org.schoellerfamily.gedbrowser.api.test;

import org.schoellerfamily.gedbrowser.api.controller.ApplicationInfoImpl;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * @author Dick Schoeller
 */
@Configuration
@ComponentScan(basePackageClasses = { GeoServiceClient.class, ApplicationInfoImpl.class })
public class TestConfiguration {
    /** */
    private static final int MINUTE_IN_MILLIS = 60000;

    /**
     * @return the RestClient builder
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * @param builder the RestClient builder
     * @return the RestClient
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
