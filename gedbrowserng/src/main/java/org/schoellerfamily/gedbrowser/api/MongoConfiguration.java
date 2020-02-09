package org.schoellerfamily.gedbrowser.api;

import java.net.UnknownHostException;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderImpl;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.NoteDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    RepositoryFinderMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    SubmitterDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    TrailerDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.UserProvider;
import org.schoellerfamily.gedbrowser.security.util.UserProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.
    EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

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
                        NoteDocumentRepositoryMongo.class,
                        PersonDocumentRepositoryMongo.class,
                        RootDocumentRepositoryMongo.class,
                        SourceDocumentRepositoryMongo.class,
                        SubmissionDocumentRepositoryMongo.class,
                        SubmitterDocumentRepositoryMongo.class,
                        TrailerDocumentRepositoryMongo.class
                },
                type = FilterType.ASSIGNABLE_TYPE))
@SuppressWarnings("PMD.ExcessiveImports")
public class MongoConfiguration {
    /** */
    @Autowired
    private PropertiesConfigurationService config;

    /** */
    @Autowired
    private UserService service;

    /** */
    @Value("${user.management:real")
    private transient String userManagement;

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(
                new MongoClient(config.mongoHost(), config.mongoPort()),
                "gedbrowser-1_2_2");
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
     * @return the finder
     */
    @Bean
    public FinderStrategy finder() {
        return new RepositoryFinderMongo();
    }

    /**
     * @return the loader
     */
    @Bean
    public GedDocumentFileLoader loader() {
        return new GedDocumentFileLoader();
    }

    /**
     * @param builder the rest template builder that Spring provides
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * @return the repository manager
     */
    @Bean
    public RepositoryManagerMongo repositoryManager() {
        return new RepositoryManagerMongo();
    }

    /**
     * @return convert for AbstractGedLine hierarchy to GedObject hierarchy
     */
    @Bean
    public GedLineToGedObjectTransformer g2g() {
        return new GedLineToGedObjectTransformer();
    }

    /**
     * @return the converter
     */
    @Bean
    public GedDocumentMongoToGedObjectConverter toGedObjectConverter() {
        return new GedDocumentMongoToGedObjectConverter();
    }

    /**
     * @return the converter
     */
    @Bean
    public GedObjectToGedDocumentMongoConverter toGedDocumentConverter() {
        return new GedObjectToGedDocumentMongoConverter();
    }

    @Bean
    public UserProvider userProvider() {
        return new UserProviderImpl(service);
    }

    /**
     * @return a calendar provider of REAL today
     */
    @Bean
    public CalendarProvider calendarProvider() {
        return new CalendarProviderImpl();
    }
}
