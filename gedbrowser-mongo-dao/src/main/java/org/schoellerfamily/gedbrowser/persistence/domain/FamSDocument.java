package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class FamSDocument extends GedDocument<FamS> {
    /**
     * Constructor.
     */
    public FamSDocument() {
        setType("fams");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof FamS)) {
            throw new PersistenceException("Wrong type");
        }
        final FamS gedObject = (FamS) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
