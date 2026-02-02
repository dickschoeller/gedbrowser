package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import java.net.UnknownHostException;
import java.util.UUID;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
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
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
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

/**
 * @author Dick Schoeller
 */
@Configuration
@EnableMongoRepositories(
    basePackages = "org.schoellerfamily.gedbrowser.persistence.mongo.repository",
    includeFilters = @ComponentScan.Filter(value = {
        FamilyDocumentRepositoryMongo.class, HeadDocumentRepositoryMongo.class,
        NoteDocumentRepositoryMongo.class, PersonDocumentRepositoryMongo.class,
        RootDocumentRepositoryMongo.class, SourceDocumentRepositoryMongo.class,
        SubmissionDocumentRepositoryMongo.class, SubmitterDocumentRepositoryMongo.class,
        TrailerDocumentRepositoryMongo.class },
    type = FilterType.ASSIGNABLE_TYPE))
@SuppressWarnings("PMD.ExcessiveImports")
public class MongoTestConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private transient String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private transient int port;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private transient String gedbrowserHome;

    /**
     * Get a MongoDbFactory for accessing the gedbrowser database.
     *
     * @return the MongoDbFactory
     * @throws UnknownHostException because it must
     */
    @Bean
    public MongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        final String databaseName = "gebrowserTest_" + UUID.randomUUID().toString();
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
     * Provide access to a test fixture that loads up database fresh for testing.
     *
     * @param repositoryManager the repository manager
     * @param mongoTemplate the mongo template
     * @param reader the data reader
     * @param toDocConverter the converter
     * @param rootDocumentRepository the root document repository
     * @return the fixture
     */
    @Bean
    public RepositoryFixture repositoryFixture(final RepositoryManagerMongo repositoryManager,
        final MongoTemplate mongoTemplate, final TestDataReader reader,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RootDocumentRepositoryMongo rootDocumentRepository) {
        return new RepositoryFixture(repositoryManager, mongoTemplate, reader, toDocConverter,
            rootDocumentRepository);
    }

    /**
     * Create the finder.
     *
     * @param repositoryManager the repository manager
     * @param toDocConverter the converter
     * @return the finder
     */
    @Bean
    public FinderStrategy finder(final RepositoryManagerMongo repositoryManager,
        final GedObjectToGedDocumentMongoConverter toDocConverter) {
        return new RepositoryFinderMongo(repositoryManager, toDocConverter);
    }

    /**
     * Create the repository manager.
     *
     * @return the repository manager
     */
    @Bean
    public RepositoryManagerMongo repositoryManager() {
        return new RepositoryManagerMongo();
    }

    /**
     * Create the transformer from AbstractGedLine to GedObject.
     *
     * @return convert for AbstractGedLine hierarchy to GedObject hierarchy
     */
    @Bean
    public GedLineToGedObjectTransformer g2g() {
        return new GedLineToGedObjectTransformer();
    }

    /**
     * Create the data reader.
     *
     * @param g2g the transformer
     * @return the data reader
     */
    @Bean
    public TestDataReader reader(final GedLineToGedObjectTransformer g2g) {
        return new TestDataReader(g2g);
    }

    /**
     * Create the converter from GedDocument to GedObject.
     *
     * @return the converter
     */
    @Bean
    public GedDocumentMongoToGedObjectConverter toGedObjectConverter() {
        return new GedDocumentMongoToGedObjectConverter();
    }

    /**
     * Create the converter from GedObject to GedDocument.
     *
     * @return the converter
     */
    @Bean
    public GedObjectToGedDocumentMongoConverter toGedDocumentConverter() {
        return new GedObjectToGedDocumentMongoConverter();
    }

    /**
     * Create the loader.
     *
     * @param finder the finder
     * @param g2g the transformer
     * @param toDocConverter the converter
     * @param rootDocumentRepository the root document repository
     * @return the loader
     */
    @Bean
    public GedDocumentFileLoader gedDocumentFileLoader(final FinderStrategy finder,
        final GedLineToGedObjectTransformer g2g,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RootDocumentRepositoryMongo rootDocumentRepository) {
        return new GedDocumentFileLoader(finder, g2g, toDocConverter, rootDocumentRepository,
            gedbrowserHome);
    }
}
