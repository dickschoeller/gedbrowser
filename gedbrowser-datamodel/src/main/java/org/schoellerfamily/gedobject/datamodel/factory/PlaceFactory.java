package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * Factory for creating place attributes.
 *
 * @author Richard Schoeller
 */
/* default */ class PlaceFactory extends AbstractGedObjectFactory {
    static {
        put("PLAC", "Place", new PlaceFactory());
        put("PLACE", "Place", new PlaceFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Place(parent, tail);
    }
}
