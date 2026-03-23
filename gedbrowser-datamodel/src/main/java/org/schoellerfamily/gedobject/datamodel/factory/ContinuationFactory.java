package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class ContinuationFactory extends AbstractGedObjectFactory {
    static {
        put("CONT", "Continuation", new ContinuationFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        parent.appendString("\n" + tail);
        return null;
    }
}
