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
    static {
        put("SUBM", "Submitter", new SubmitterFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        if (parent.getParent() == null) {
            return new Submitter(parent, xref);
        } else {
            return new SubmitterLinkFactory().create(parent, xref, tag, tail);
        }
    }
}
