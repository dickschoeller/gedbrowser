package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.HusbandDocument;

/**
 * @author Dick Schoeller
 */
public class HusbandDocumentMongo extends GedDocumentMongo<Husband>
        implements HusbandDocument {
    /**
     * Constructor.
     */
    public HusbandDocumentMongo() {
        setType("husband");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Husband)) {
            throw new PersistenceException("Wrong type");
        }
        final Husband gedObject = (Husband) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
