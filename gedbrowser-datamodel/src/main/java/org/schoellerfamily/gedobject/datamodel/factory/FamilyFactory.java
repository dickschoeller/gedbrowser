package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class FamilyFactory extends AbstractGedObjectFactory {
    static {
        put("FAM", "Family", new FamilyFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Family(parent, xref);
    }
}
