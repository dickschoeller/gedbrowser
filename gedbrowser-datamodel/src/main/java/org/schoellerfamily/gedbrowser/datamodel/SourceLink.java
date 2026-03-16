package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class SourceLink extends AbstractLink {
    /**
     * Creates a new SourceLink.
     *
     */
    public SourceLink() {
        super();
    }

    /**
     * Creates a new SourceLink.
     *
     * @param parent the parent
     * @param tag the tag
     */
    public SourceLink(final GedObject parent, final String tag) {
        super(parent, tag);
    }

    /**
     * Creates a new SourceLink.
     *
     * @param parent the parent
     * @param tag the tag
     * @param xref the xref
     */
    public SourceLink(final GedObject parent, final String tag,
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
