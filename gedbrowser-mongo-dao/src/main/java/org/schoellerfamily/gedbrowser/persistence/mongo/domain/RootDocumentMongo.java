package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "roots")
public class RootDocumentMongo extends GedDocumentMongo<Root>
        implements RootDocument {
    /**
     * Constructor.
     */
    public RootDocumentMongo() {
        setType("root");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Root)) {
            throw new PersistenceException("Wrong type");
        }
        final Root gedObject = (Root) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.setDbName(gedObject.getDbName());
//        this.loadAttributes(gedObject.getAttributes());
    }

}
