package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class FamC extends AbstractLink {
    /**
     * Creates a new FamC.
     *
     */
    public FamC() {
        super();
    }

    /**
     * Creates a new FamC.
     *
     * @param parent the parent
     * @param string the string
     * @param xref the xref
     */
    public FamC(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
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
