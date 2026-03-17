package org.schoellerfamily.gedbrowser.datamodel;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents fam c in the domain model.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class FamC extends AbstractLink {

    /**
     * Creates a new FamC.
     *
     * @param parent the parent
     * @param string the string
     * @param xref the cross-reference identifier
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
