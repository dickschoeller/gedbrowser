package org.schoellerfamily.geoservice;

import java.net.UnknownHostException;

import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class MongoConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private final String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private final int port;

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    @Bean
    @NonNull
    public MongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        final String connectionString = "mongodb://" + host + ":" + port;
        final MongoClient client = MongoClients.create(connectionString);
        if (client == null) {
			throw new UnknownHostException("Could not connect to MongoDB at " + connectionString);
		}
        return new SimpleMongoClientDatabaseFactory(client, "geoservice");
    }

    /**
     * Make the DB access template for MongoDB.
     *
     * @return the template
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }
}
