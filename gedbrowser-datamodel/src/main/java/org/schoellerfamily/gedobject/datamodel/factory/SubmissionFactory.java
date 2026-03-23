package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Submission;

/* default */ class SubmissionFactory extends AbstractGedObjectFactory {
    /** */
    /* default */ static final SubmissionLinkFactory SUBNLINK_FACTORY = new SubmissionLinkFactory();

    static {
        put("SUBN", "Submission", new SubmissionFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        if (parent.getParent() == null) {
            return new Submission(parent, xref);
        } else {
            return SUBNLINK_FACTORY.create(parent, xref, tag, tail);
        }
    }
}
