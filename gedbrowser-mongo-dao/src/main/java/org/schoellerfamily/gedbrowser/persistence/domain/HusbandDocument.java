package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class HusbandDocument extends GedDocument<Husband> {
    /**
     * Constructor.
     */
    public HusbandDocument() {
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
