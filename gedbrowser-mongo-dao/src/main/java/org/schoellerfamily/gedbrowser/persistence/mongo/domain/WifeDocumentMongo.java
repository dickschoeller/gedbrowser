package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.WifeDocument;

/**
 * @author Dick Schoeller
 */
public class WifeDocumentMongo extends GedDocumentMongo<Wife>
        implements WifeDocument {
    /**
     * Constructor.
     */
    public WifeDocumentMongo() {
        setType("wife");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Wife)) {
            throw new PersistenceException("Wrong type");
        }
        final Wife gedObject = (Wife) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
