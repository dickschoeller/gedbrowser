package org.schoellerfamily.geoservice.persistence.mongo.test;

import java.net.UnknownHostException;

import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.schoellerfamily.geoservice.persistence.mongo.GeoCodeMongo;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
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
    /**
     * @return the persistence manager
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public GeoCode persistenceManager() {
        // CHECKSTYLE:ON
        return new GeoCodeMongo();
    }

    /**
     * @return the geocoder
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
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
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        // CHECKSTYLE:ON
        return new SimpleMongoDbFactory(new MongoClient(), "gedbrowserTest");
    }

    /**
     * Make the DB access template for MongoDB.
     *
     * @return the template
     * @throws UnknownHostException because it must
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        // CHECKSTYLE:ON
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }

    /**
     * Provide access to a test fixture that loads up database fresh for
     * testing.
     *
     * @return the fixture
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public GeoRepositoryFixture repositoryFixture() {
        // CHECKSTYLE:ON
        return new GeoRepositoryFixture();
    }

    /**
     * @return the geocodeloader
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public GeoCodeLoader loader() {
        return new GeoCodeLoader();
    }
}
