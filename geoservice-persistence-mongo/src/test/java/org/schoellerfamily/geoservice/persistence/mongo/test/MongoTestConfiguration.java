package org.schoellerfamily.geoservice.persistence.mongo.test;

import java.net.UnknownHostException;
import java.util.UUID;

import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.schoellerfamily.geoservice.persistence.mongo.GeoCodeMongo;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.
    EnableMongoRepositories;

import com.mongodb.MongoClient;

/**
 * Standard Spring configuration for tests of the MongoDB persistence layer.
 *
 * @author Dick Schoeller
 */
@Configuration
@EnableMongoRepositories(
        basePackages =
                "org.schoellerfamily.geoservice.persistence.mongo.repository",
        includeFilters = @ComponentScan.Filter(
                value = { GeoDocumentRepositoryMongo.class },
                type = FilterType.ASSIGNABLE_TYPE))
public class MongoTestConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private transient String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private transient int port;

    /**
     * @return the persistence manager
     */
    @Bean
    public GeoCode persistenceManager() {
        return new GeoCodeMongo();
    }

    /**
     * @return the geocoder
     */
    @Bean
    public GeoCoder geoCoder() {
        final GeoCodeTestFixture tempFixture = new GeoCodeTestFixture();
        return new StubGeoCoder(tempFixture.expectedNotFound());
    }

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        final String databaseName =
                "geoserviceTest_" + UUID.randomUUID().toString();
        return new SimpleMongoDbFactory(
                new MongoClient(host, port), databaseName);
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

    /**
     * Provide access to a test fixture that loads up database fresh for
     * testing.
     *
     * @return the fixture
     */
    @Bean
    public GeoRepositoryFixture repositoryFixture() {
        return new GeoRepositoryFixture();
    }

    /**
     * @return the geocodeloader
     */
    @Bean
    public GeoCodeLoader loader() {
        return new GeoCodeLoader();
    }
}
