package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class ConcatenationFactory extends AbstractGedObjectFactory {
    static {
        put("CONC", "Concatenate", new ConcatenationFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        parent.appendString(tail);
        return null;
    }
}
