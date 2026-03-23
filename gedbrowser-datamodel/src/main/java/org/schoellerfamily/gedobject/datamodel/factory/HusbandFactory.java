package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating husband links.
 *
 * @author Richard Schoeller
 */
/* default */ class HusbandFactory extends AbstractGedObjectFactory {
    static {
        put("HUSB", "Husband", new HusbandFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Husband(parent, tag, new ObjectId(tail));
    }
}
