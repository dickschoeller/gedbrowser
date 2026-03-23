package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * Factory for creating trailers.
 *
 * @author Richard Schoeller
 */
/* default */ class TrailerFactory extends AbstractGedObjectFactory {
    static {
        put("TRLR", "Trailer", new TrailerFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Trailer(parent, tag);
    }
}
