package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class FamCFactory extends AbstractGedObjectFactory {
    static {
        put("FAMC", "Child of Family", new FamCFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new FamC(parent, tag, new ObjectId(tail));
    }
}
