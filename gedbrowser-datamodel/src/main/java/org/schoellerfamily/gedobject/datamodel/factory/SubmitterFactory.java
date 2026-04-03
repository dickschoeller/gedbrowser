package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * Factory for creating submitters.
 *
 * @author Richard Schoeller
 */
/* default */ class SubmitterFactory extends AbstractGedObjectFactory {
    /** */
    /* default */ static final SubmitterLinkFactory SUBMLINK_FACTORY = new SubmitterLinkFactory();

    static {
        put("SUBM", "Submitter", new SubmitterFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        if (parent.getParent() == null) {
            return new Submitter(parent, xref);
        } else {
            return SUBMLINK_FACTORY.create(parent, xref, tag, tail);
        }
    }
}
