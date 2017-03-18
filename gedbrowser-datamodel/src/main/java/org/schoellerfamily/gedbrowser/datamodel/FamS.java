package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class FamS extends AbstractLink {
    /**
     * Default constructor.
     */
    public FamS() {
        super();
    }

    /**
     * @param parent person who is a spouse in the referred family
     * @param string long version of type string
     * @param xref the reference to a family object
     */
    public FamS(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
