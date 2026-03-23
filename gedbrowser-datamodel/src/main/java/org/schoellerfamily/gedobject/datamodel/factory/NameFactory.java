package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating name links.
 *
 * @author Richard Schoeller
 */
/* default */ class NameFactory extends AbstractGedObjectFactory {
    static {
        put("NAME", "Name", new NameFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Name(parent, tail);
    }
}
