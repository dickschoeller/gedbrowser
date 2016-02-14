package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class NameDocument extends GedDocument<Name> {
    /**
     * Constructor.
     */
    public NameDocument() {
        setType("name");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Name)) {
            throw new PersistenceException("Wrong type");
        }
        final Name gedObject = (Name) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
