package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.PlaceDocument;

/**
 * @author Dick Schoeller
 */
public class PlaceDocumentMongo extends GedDocumentMongo<Place>
        implements PlaceDocument {
    /**
     * Constructor.
     */
    public PlaceDocumentMongo() {
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