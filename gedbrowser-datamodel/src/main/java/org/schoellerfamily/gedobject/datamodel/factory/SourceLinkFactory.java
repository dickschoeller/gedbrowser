package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * Factory for creating source links.
 *
 * @author Richard Schoeller
 */
/* default */ class SourceLinkFactory extends AbstractGedObjectFactory {
    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new SourceLink(parent, tag, new ObjectId(tail));
    }
}
