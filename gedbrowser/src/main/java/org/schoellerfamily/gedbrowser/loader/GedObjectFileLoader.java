package org.schoellerfamily.gedbrowser.loader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;

import lombok.extern.slf4j.Slf4j;

/**
 * Loads a GedObject from a file into a MongoDB database. Returns the root as a
 * GedObject.
 *
 * @author Dick Schoeller
 */
@Slf4j
public final class GedObjectFileLoader extends GedDocumentFileLoader {
    /**
     * Constructor.
     *
     * @param finder                 finds objects in the database
     * @param g2g                    convert GedLine to a GedObject
     * @param toDocConverter         convert a GedObject to a GedDocumentMongo
     * @param rootDocumentRepository access root objects in the database
     * @param gedbrowserHome         location of files for initialization
     */
    public GedObjectFileLoader(final FinderStrategy finder, final GedLineToGedObjectTransformer g2g,
        final GedObjectToGedDocumentMongoConverter toDocConverter,
        final RootDocumentRepositoryMongo rootDocumentRepository, final String gedbrowserHome) {
        super(finder, g2g, toDocConverter, rootDocumentRepository, gedbrowserHome);
    }

    /**
     * Load the requested db.
     *
     * @param repositoryManagerMongo the repository manager to use
     * @param dbName                 the name of the database to load
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
