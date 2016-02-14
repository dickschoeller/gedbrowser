package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class FamCDocument extends GedDocument<FamC> {
    /**
     * Constructor.
     */
    public FamCDocument() {
        setType("famc");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof FamC)) {
            throw new PersistenceException("Wrong type");
        }
        final FamC gedObject = (FamC) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
