package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceLinkDocument;

/**
 * @author Dick Schoeller
 */
public class SourceLinkDocumentMongo extends GedDocumentMongo<SourceLink>
        implements SourceLinkDocument {
    /**
     * Constructor.
     */
    public SourceLinkDocumentMongo() {
        setType("sourcelink");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof SourceLink)) {
            throw new PersistenceException("Wrong type");
        }
        final SourceLink gedObject = (SourceLink) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
