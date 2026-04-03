package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating concatenated date attributes.
 *
 * @author Richard Schoeller
 */
/* default */ class DateFactory extends AbstractGedObjectFactory {
    static {
        put("DATE", "Date", new DateFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Date(parent, tail);
    }
}
