package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/* default */ class SubmitterLinkFactory extends AbstractGedObjectFactory {
    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new SubmitterLink(parent, tag, new ObjectId(tail));
    }
}
