package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Source extends AbstractSource {
    /**
     * Creates a new Source.
     *
     */
    public Source() {
        super();
    }

    /**
     * Creates a new Source.
     *
     * @param parent the parent
     * @param xref the xref
     */
    public Source(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
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
