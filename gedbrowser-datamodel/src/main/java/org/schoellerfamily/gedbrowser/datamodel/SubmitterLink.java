package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents submitter link in the domain model.
 *
 * @author Richard Schoeller
 */
public final class SubmitterLink extends AbstractLink {
    /**
     * Creates a new SubmitterLink.
     */
    public SubmitterLink() {
        super();
    }

    /**
     * Creates a new SubmitterLink.
     *
     * @param parent the parent
     * @param tag the tag
     * @param xref the xref
     */
    public SubmitterLink(final GedObject parent, final String tag,
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
