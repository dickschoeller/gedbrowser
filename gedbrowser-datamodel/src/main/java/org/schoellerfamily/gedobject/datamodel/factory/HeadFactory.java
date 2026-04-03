package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating heads.
 *
 * @author Richard Schoeller
 */
/* default */ class HeadFactory extends AbstractGedObjectFactory {
    static {
        put("HEAD", "Header", new HeadFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Head(parent, tag, tail);
    }
}
