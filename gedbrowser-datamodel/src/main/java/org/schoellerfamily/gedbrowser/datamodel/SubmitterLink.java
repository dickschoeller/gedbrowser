package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class SubmitterLink extends AbstractLink {
    /**
     * Default constructor.
     */
    public SubmitterLink() {
        super();
    }

    /**
     * @param parent parent object of this submitter link
     * @param tag long version of type string
     * @param xref the reference to a submitter object
     */
    public SubmitterLink(final GedObject parent, final String tag,
            final ObjectId xref) {
        super(parent, tag, xref);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
