package org.schoellerfamily.gedbrowser.loader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;

/**
 * @author Dick Schoeller
 */
public final class GedObjectFileLoader extends GedDocumentFileLoader {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Constructor.
     */
    public GedObjectFileLoader() {
        // Empty constructor.
    }

    /**
     * @param dbName the name of the database to load
     * @return the root object of the database
     */
    public GedObject load(final String dbName) {
        logger.info("entering load(" + dbName + ")");
        final RootDocument rootDocument = loadDocument(dbName);
        if (rootDocument == null) {
            return null;
        }
        return rootDocument.getGedObject();
    }
}
