package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Main program for playing with the GeoService client class.
 *
 * @author Dick Schoeller
 */
@SpringBootApplication
@Slf4j
public class Application {

    /** */
    @Autowired
    private transient ObjectMapper mapper;

    /** */
    @Autowired
    private transient GeoServiceClient client;

    /**
     * @param args standard main program argument handling
     */
    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @param builder the rest template builder that Spring provides
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * @return the command line runner
     * @throws Exception because things can blow up
     */
    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {
            for (final String arg : args) {
                log.info("Get geocode for: {}", arg);
                final GeoServiceItem item =
                        client.get(arg);
                log.info("found item:{}", mapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(item));
            }
        };
    }
}
