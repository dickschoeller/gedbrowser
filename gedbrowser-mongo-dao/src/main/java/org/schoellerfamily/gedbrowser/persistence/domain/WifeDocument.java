package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class WifeDocument extends GedDocument<Wife> {
    /**
     * Constructor.
     */
    public WifeDocument() {
        setType("wife");
    }

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
