package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/* default */ class WifeFactory extends AbstractGedObjectFactory {
    static {
        put("WIFE", "Wife", new WifeFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Wife(parent, tag, new ObjectId(tail));
    }
}
