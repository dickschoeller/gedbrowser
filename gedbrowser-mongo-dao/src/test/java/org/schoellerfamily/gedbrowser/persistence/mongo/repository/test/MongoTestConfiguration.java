package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import java.net.UnknownHostException;
import java.util.UUID;

import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryFinderMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmittorDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
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
 * @author Dick Schoeller
 */
@Configuration
@EnableMongoRepositories(
        basePackages =
            "org.schoellerfamily.gedbrowser.persistence.mongo.repository",
        includeFilters = @ComponentScan.Filter(
                value = {
                        FamilyDocumentRepositoryMongo.class,
                        HeadDocumentRepositoryMongo.class,
                        PersonDocumentRepositoryMongo.class,
                        RootDocumentRepositoryMongo.class,
                        SourceDocumentRepositoryMongo.class,
                        SubmittorDocumentRepositoryMongo.class,
                        TrailerDocumentRepositoryMongo.class
                },
                type = FilterType.ASSIGNABLE_TYPE))
public class MongoTestConfiguration {
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
    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        final String databaseName =
                "gebrowserTest_" + UUID.randomUUID().toString();
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
    public RepositoryFixture repositoryFixture() {
        return new RepositoryFixture();
    }

    /**
     * @return the finder
     */
    @Bean
    public FinderStrategy finder() {
        return new RepositoryFinderMongo();
    }
}
