package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main program for playing with the GeoService client class.
 *
 * @author Dick Schoeller
 */
@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class Application {

    /** */
    private final ObjectMapper mapper;

    /** */
    private final GeoServiceClient client;

    /**
     * @param args standard main program argument handling
     */
    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @param builder the rest client builder that Spring provides
     * @return the rest client
     */
    @Bean
    public RestClient restClient(final RestClient.Builder builder) {
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
                final GeoServiceItem item = client.get(arg);
                if (log.isInfoEnabled()) {
                    log.info("found item:{}",
                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item));
                }
            }
        };
    }
}
