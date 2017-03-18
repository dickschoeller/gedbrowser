package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Family extends GedObject {
    /**
     * Default constructor.
     */
    public Family() {
        super();
    }

    /**
     * @param parent parent object of this family
     * @param xref cross reference to this family
     */
    public Family(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
