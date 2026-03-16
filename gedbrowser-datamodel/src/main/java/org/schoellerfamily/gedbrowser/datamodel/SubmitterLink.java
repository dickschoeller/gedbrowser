package org.schoellerfamily.gedbrowser.datamodel;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents submitter link in the domain model.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class SubmitterLink extends AbstractLink {

    /**
     * Creates a new SubmitterLink.
     *
     * @param parent the parent
     * @param tag the tag
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
