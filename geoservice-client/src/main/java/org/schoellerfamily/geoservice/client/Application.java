package org.schoellerfamily.geoservice.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Application {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            GeoServiceItem item = restTemplate.getForObject(
                    "http://localhost:8088/geocode?name=Bethlhem,%20PA", GeoServiceItem.class);
            ObjectMapper mapper = new ObjectMapper();
            logger.info("found item:" + mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(item));
        };
    }
}
