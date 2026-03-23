package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class FamSFactory extends AbstractGedObjectFactory {
    static {
        put("FAMS", "Spouse of Family", new FamSFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new FamS(parent, tag, new ObjectId(tail));
    }
}
