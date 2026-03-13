package org.schoellerfamily.gedbrowser.api;

import java.net.UnknownHostException;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderImpl;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.NoteDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryFinderMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmitterDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.schoellerfamily.geoservice.keys.KeyManagerImpl;
import org.schoellerfamily.geoservice.keys.KeyManagerStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestClient;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableMongoRepositories(
    basePackages = "org.schoellerfamily.gedbrowser.persistence.mongo.repository",
    includeFilters = @ComponentScan.Filter(
        value = {
            FamilyDocumentRepositoryMongo.class, HeadDocumentRepositoryMongo.class,
            NoteDocumentRepositoryMongo.class, PersonDocumentRepositoryMongo.class,
            RootDocumentRepositoryMongo.class, SourceDocumentRepositoryMongo.class,
            SubmissionDocumentRepositoryMongo.class, SubmitterDocumentRepositoryMongo.class,
            TrailerDocumentRepositoryMongo.class },
        type = FilterType.ASSIGNABLE_TYPE))
@SuppressWarnings("PMD.ExcessiveImports")
@RequiredArgsConstructor
public class MongoConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private final String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private final int port;

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private final String gedbrowserHome;

    /** */
    @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
    private final String keyfile;

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        final String databaseName = "gebrowser-1_2_2";
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
        return new MongoTemplate(mongoDbFactory());
    }

    /**
     * @return the RestClient builder
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * @param builder the rest client builder that Spring provides
     * @return the rest client
     */
    @Bean
    public RestClient restClient(final RestClient.Builder builder) {
        return builder.build();
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

    /**
     * @param repositoryManager the repository manager
     * @param toDocConverter    the converter to ged document
     * @return the finder
     */
    @Bean
    public FinderStrategy finder(final RepositoryManagerMongo repositoryManager,
        final GedObjectToGedDocumentMongoConverter toDocConverter) {
        return new RepositoryFinderMongo(repositoryManager, toDocConverter);
    }

    /**
     * @return the repository manager
     */
    @Bean
    public RepositoryManagerMongo repositoryManager() {
        return new RepositoryManagerMongo();
    }

    /**
     * @return a calendar provider of REAL today
     */
    @Bean
    public CalendarProvider calendarProvider() {
        return new CalendarProviderImpl();
    }

    /**
     * Create the key manager bean.
     *
     * @return manager of Google geocoding/maps keys
     */
    @Bean
    public KeyManager keyManager() {
        if ("stub".equals(keyfile)) {
            return new KeyManagerStub();
        }
        return new KeyManagerImpl(keyfile);
    }
}
