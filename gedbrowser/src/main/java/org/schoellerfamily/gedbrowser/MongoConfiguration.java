package org.schoellerfamily.gedbrowser;

import java.net.UnknownHostException;

import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FamilyDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    HeadDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    PersonDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    RepositoryFinder;
import org.schoellerfamily.gedbrowser.persistence.repository.
    RootDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SourceDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SubmittorDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    TrailerDocumentRepository;
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
    // CHECKSTYLE:OFF
    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        // CHECKSTYLE:ON
        return new SimpleMongoDbFactory(new MongoClient(host, port),
                "gedbrowser");
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
        return new MongoTemplate(mongoDbFactory());
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

    /**
     * @return the loader
     */
    // We turn off checkstyle because bean methods must not be final
    // CHECKSTYLE:OFF
    @Bean
    public GedFileLoader loader() {
        // CHECKSTYLE:ON
        return new GedFileLoader();
    }
}
