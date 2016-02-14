package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class PlaceDocument extends GedDocument<Place> {
    /**
     * Constructor.
     */
    public PlaceDocument() {
        setType("place");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Place)) {
            throw new PersistenceException("Wrong type");
        }
        final Place gedObject = (Place) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
