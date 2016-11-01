package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorLinkDocument;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkDocumentMongo extends GedDocumentMongo<SubmittorLink>
        implements SubmittorLinkDocument {
    /**
     * Constructor.
     */
    public SubmittorLinkDocumentMongo() {
        setType("submittorlink");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof SubmittorLink)) {
            throw new PersistenceException("Wrong type");
        }
        final SubmittorLink gedObject = (SubmittorLink) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
