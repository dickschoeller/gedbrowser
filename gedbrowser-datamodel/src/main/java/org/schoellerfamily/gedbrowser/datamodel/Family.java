package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Family extends GedObject {
    /**
     * Creates a new Family.
     *
     */
    public Family() {
        super();
    }

    /**
     * Creates a new Family.
     *
     * @param parent the parent
     * @param xref the xref
     */
    public Family(final GedObject parent, final ObjectId xref) {
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
