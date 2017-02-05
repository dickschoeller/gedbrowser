package org.schoellerfamily.geoservice;

import java.net.UnknownHostException;

import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableMongoRepositories(
        basePackages =
            "org.schoellerfamily.geoservice.persistence.mongo.repository",
        includeFilters = @ComponentScan.Filter(value = {
                GeoDocumentRepositoryMongo.class
        },
        type = FilterType.ASSIGNABLE_TYPE))
public class MongoConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private transient String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private transient int port;

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    // We turn off checkstyle because bean methods must not be final
    // This seems to vary with checkstyle version
    // CHECKSTYLE:OFF
    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        // CHECKSTYLE:ON
        return new SimpleMongoDbFactory(new MongoClient(host, port),
                "geoservice");
    }

    /**
     * Make the DB access template for MongoDB.
     *
     * @return the template
     * @throws UnknownHostException because it must
     */
    // We turn off checkstyle because bean methods must not be final
    // This seems to vary with checkstyle version
    // CHECKSTYLE:OFF
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        // CHECKSTYLE:ON
        return new MongoTemplate(mongoDbFactory());
    }
}
