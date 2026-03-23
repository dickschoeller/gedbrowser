package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating multimedia attributes.
 *
 * @author Richard Schoeller
 */
/* default */ class MultimediaFactory extends AbstractGedObjectFactory {
    static {
        put("OBJE", "Multimedia", new MultimediaFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Multimedia(parent, tag, tail);
    }
}
