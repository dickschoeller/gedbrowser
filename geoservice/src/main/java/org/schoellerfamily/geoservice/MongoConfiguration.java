package org.schoellerfamily.geoservice;

import java.net.UnknownHostException;

import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;



/**
 * Configures components related to mongo.
 *
 * @author Richard Schoeller
 */
@Factory
public class MongoConfiguration {
    /** */
    @Value("${spring.data.mongodb.host:localhost}")
    private String host;

    /** */
    @Value("${spring.data.mongodb.port:27017}")
    private int port;

    /**
     * Get a MongoDB client connected to the configured host and port.
     *
     * @return the MongoDB client
     * @throws UnknownHostException because it must
     */
    @Singleton
    public MongoClient mongoClient() throws UnknownHostException {
        final String connectionString = "mongodb://" + host + ":" + port;
        return MongoClients.create(connectionString);
    }

    /**
     * Get the geoservice MongoDB database.
     *
     * @param client the mongo client
     * @return the mongo database
     * @throws UnknownHostException because it must
     */
    @Singleton
    public MongoDatabase mongoDatabase(final MongoClient client) throws UnknownHostException {
        return client.getDatabase("geoservice");
    }

    /**
     * Create the mongo repository facade used by the cache implementation.
     *
     * @param database the mongo database
     * @return repository facade
     */
    @Singleton
    public GeoDocumentRepositoryMongo geoDocumentRepositoryMongo(final MongoDatabase database) {
        return new GeoDocumentRepositoryMongo(database);
    }
}
