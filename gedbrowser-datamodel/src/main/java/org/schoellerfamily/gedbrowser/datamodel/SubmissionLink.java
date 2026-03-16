package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class SubmissionLink extends AbstractLink {
    /**
     * Creates a new SubmissionLink.
     *
     */
    public SubmissionLink() {
        super();
    }

    /**
     * Creates a new SubmissionLink.
     *
     * @param parent the parent
     * @param tag the tag
     * @param xref the xref
     */
    public SubmissionLink(final GedObject parent, final String tag,
            final ObjectId xref) {
        super(parent, tag, xref);
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
