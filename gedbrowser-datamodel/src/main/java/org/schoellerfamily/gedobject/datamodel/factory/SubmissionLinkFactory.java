package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;

/**
 * Factory for creating submission links.
 *
 * @author Richard Schoeller
 */
/* default */ class SubmissionLinkFactory extends AbstractGedObjectFactory {
    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new SubmissionLink(parent, tag, new ObjectId(tail));
    }
}
