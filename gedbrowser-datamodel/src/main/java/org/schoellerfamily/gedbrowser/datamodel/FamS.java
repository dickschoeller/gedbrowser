package org.schoellerfamily.gedbrowser.datamodel;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents fam s in the domain model.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class FamS extends AbstractLink {

    /**
     * Creates a new FamS.
     *
     * @param parent the parent
     * @param string the string
     * @param xref the cross-reference identifier
     */
    public FamS(final GedObject parent, final String string,
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
