package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating links.
 *
 * @author Richard Schoeller
 */
/* default */ class LinkFactory extends AbstractGedObjectFactory {
    static {
        put("LINK", "Link", new LinkFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new Link(parent, tag, new ObjectId(tail));
    }
}
