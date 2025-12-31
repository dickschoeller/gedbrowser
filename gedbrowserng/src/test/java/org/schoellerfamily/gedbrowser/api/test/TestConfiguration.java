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
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(final RestClient.Builder builder) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(60000);
        factory.setReadTimeout(60000);
        return builder
            .requestFactory(factory)
            .build();
    }
}
