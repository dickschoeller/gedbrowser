package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class RootFactory extends AbstractGedObjectFactory {
    static {
        put("ROOT", "Root", new RootFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Root(tag);
    }
}
