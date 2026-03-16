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
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;



/**
 * Configures components related to mongo test.
 *
 * @author Richard Schoeller
 */
@Configuration
@EnableMongoRepositories(
        basePackages =
                "org.schoellerfamily.geoservice.persistence.mongo.repository",
        includeFilters = @ComponentScan.Filter(
                value = { GeoDocumentRepositoryMongo.class },
                type = FilterType.ASSIGNABLE_TYPE))
@RequiredArgsConstructor
public class MongoTestConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private final String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private final int port;

    /** */
    private transient GeoCode gcc = null;

    /** */
    @Autowired
    private transient GeoDocumentRepositoryMongo repository;

    /** */
    private transient MongoTemplate mongoTemplate = null;

    /** Lazy initialization. */
    private transient GeoCoder geoCoder = null;

    /**
     * Creates and configures the geo code bean.
     *
     * @return the configured geo code bean
     */
    @Bean
    public GeoCode persistenceManager() {
        if (gcc == null) {
            gcc = new GeoCodeMongo(geoCoder(), repository);
        }
        return gcc;
    }

    /**
     * Creates and configures the geo coder bean.
     *
     * @return the configured geo coder bean
     */
    @Bean
    public GeoCoder geoCoder() {
        if (geoCoder == null) {
            geoCoder = new StubGeoCoder(new GeoCodeTestFixture().expectedNotFound());
        }
        return geoCoder;
    }

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        final String databaseName = "geoserviceTest_" + UUID.randomUUID().toString();
        final String connectionString = "mongodb://" + host + ":" + port;
        final MongoClient client = MongoClients.create(connectionString);
        return new SimpleMongoClientDatabaseFactory(client, databaseName);
    }

    /**
     * Make the DB access template for MongoDB.
     *
     * @return the template
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        if (mongoTemplate == null) {
            mongoTemplate = new MongoTemplate(mongoDbFactory());
        }
        return mongoTemplate;
    }

    /**
     * Provide access to a test fixture that loads up database fresh for
     * testing.
     *
     * @return the fixture
     * @throws UnknownHostException because it must
     */
    @Bean
    public GeoRepositoryFixture repositoryFixture() throws UnknownHostException {
        return new GeoRepositoryFixture(repository, mongoTemplate());
    }

    /**
     * Creates and configures the geo code loader bean.
     *
     * @return the configured geo code loader bean
     */
    @Bean
    public GeoCodeLoader loader() {
        return new GeoCodeLoader(persistenceManager());
    }

    /**
     * Creates and configures the geo document repository mongo impl bean.
     *
     * @return the configured geo document repository mongo impl bean
     */
    @Bean
    public GeoDocumentRepositoryMongoImpl geoDocumentRepositoryMongo() throws UnknownHostException {
        return new GeoDocumentRepositoryMongoImpl(mongoTemplate());
    }
}
