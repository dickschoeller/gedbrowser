package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * Factory for creating sources.
 *
 * @author Richard Schoeller
 */
/* default */ class SourceFactory extends AbstractGedObjectFactory {
    /** */
    /* default */ static final SourceLinkFactory SOURLINK_FACTORY = new SourceLinkFactory();

    static {
        put("SOUR", "Source", new SourceFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        if (parent.getParent() == null) {
            return new Source(parent, xref);
        } else {
            if (tail.contains("@")) {
                return SOURLINK_FACTORY.create(parent, xref, tag, tail);
            } else {
                return AttributeFactory.ATTR_FACTORY.create(parent, xref, tag, tail);
            }
        }
    }
}
