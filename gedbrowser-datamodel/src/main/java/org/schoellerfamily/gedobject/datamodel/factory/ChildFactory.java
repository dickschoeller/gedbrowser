package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating children.
 *
 * @author Richard Schoeller
 */
/* default */ class ChildFactory extends AbstractGedObjectFactory {
    static {
        put("CHIL", "Child", new ChildFactory());
    }
    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Child(parent, tag, new ObjectId(tail));
    }
}
