package org.schoellerfamily.gedbrowser.persistence.repository.test;

import java.net.UnknownHostException;

import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FamilyDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    HeadDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    PersonDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.RepositoryFinder;
import org.schoellerfamily.gedbrowser.persistence.repository.
    RootDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SourceDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SubmittorDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    TrailerDocumentRepository;
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
        basePackages = "org.schoellerfamily.gedbrowser.persistence.repository",
        includeFilters = @ComponentScan.Filter(
                value = {
                        FamilyDocumentRepository.class,
                        HeadDocumentRepository.class,
                        PersonDocumentRepository.class,
                        RootDocumentRepository.class,
                        SourceDocumentRepository.class,
                        SubmittorDocumentRepository.class,
                        TrailerDocumentRepository.class
                },
                type = FilterType.ASSIGNABLE_TYPE))
public class MongoTestConfiguration {
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
    public RepositoryFixture repositoryFixture() {
        // CHECKSTYLE:ON
        return new RepositoryFixture();
    }

    /**
     * @return the finder
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public FinderStrategy finder() {
        // CHECKSTYLE:ON
        return new RepositoryFinder();
    }
}
