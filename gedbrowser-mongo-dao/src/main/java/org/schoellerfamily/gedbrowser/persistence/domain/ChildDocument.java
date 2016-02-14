package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class ChildDocument extends GedDocument<Child> {
    /**
     * Constructor.
     */
    public ChildDocument() {
        setType("child");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Child)) {
            throw new PersistenceException("Wrong type");
        }
        final Child gedObject = (Child) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
