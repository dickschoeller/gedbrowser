package org.schoellerfamily.gedbrowser.test;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Richard Schoeller
 */
@Configuration
public class TestConfiguration {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder()
            .connectTimeout(Duration.ofMillis(60000))
            .readTimeout(Duration.ofMillis(120000));
    }
}
