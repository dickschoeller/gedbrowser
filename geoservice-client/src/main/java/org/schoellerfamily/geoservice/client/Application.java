package org.schoellerfamily.geoservice.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main program for playing with the GeoService client class.
 *
 * @author Dick Schoeller
 */
@SpringBootApplication
public class Application {
    /** Logger. */
    private final transient Log logger =
            LogFactory.getLog(Application.class);

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
    // We turn off checkstyle because bean methods must not be final
    // This seems to vary with checkstyle version
    // CHECKSTYLE:OFF
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        // CHECKSTYLE:ON
        return builder.build();
    }

    /**
     * @return the command line runner
     * @throws Exception because things can blow up
     */
    // We turn off checkstyle because bean methods must not be final
    // This seems to vary with checkstyle version
    // CHECKSTYLE:OFF
    @Bean
    public CommandLineRunner run() throws Exception {
        // CHECKSTYLE:ON
        return args -> {
            for (final String arg : args) {
                logger.info("Get geocode for: " + arg);
                final GeoServiceItem item =
                        client.get(arg);
                logger.info(
                        "found item:" + mapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(item));
            }
        };
    }
}
