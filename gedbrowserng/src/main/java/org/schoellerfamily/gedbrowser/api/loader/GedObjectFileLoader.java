package org.schoellerfamily.gedbrowser.api.loader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Loads a GedObject from a file into a MongoDB database. Returns the root as a GedObject.
 *
 * @author Dick Schoeller
 */
@Component
@Slf4j
public final class GedObjectFileLoader extends GedDocumentFileLoader {
    /**
     * Creates a new GedObjectFileLoader.
     *
     * @param finder the finder
     * @param g2g the g2g
     * @param toDocConverter the to doc converter
     * @param rootDocumentRepository the root document repository
     */
    public GedObjectFileLoader(final FinderStrategy finder,
            final GedLineToGedObjectTransformer g2g,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RootDocumentRepositoryMongo rootDocumentRepository,
            @Value("${gedbrowser.home:/var/lib/gedbrowser}")
            final String gedbrowserHome,
            final RepositoryManagerMongo repositoryManagerMongo) {
        super(finder, g2g, toDocConverter, rootDocumentRepository, gedbrowserHome);
        repositoryManagerMongo.getMap().put(Root.class, rootDocumentRepository);
    }

    /**
     * Load the requested db.
     *
     * @param repositoryManagerMongo the repository manager to use
     * @param dbName the name of the database to load
     * @return the root object of the database
     */
    public GedObject load(final RepositoryManagerMongo repositoryManagerMongo,
        final String dbName) {
        log.info("entering load({})", dbName);
        final RootDocument rootDocument = loadDocument(repositoryManagerMongo, dbName);
        if (rootDocument == null) {
            return null;
        }
        return rootDocument.getGedObject();
    }
}
