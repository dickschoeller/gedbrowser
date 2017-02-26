package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Source extends AbstractSource {
    /**
     * Default constructor.
     */
    public Source() {
        super();
    }

    /**
     * @param parent parent object of this source
     * @param xref cross reference to this source
     */
    public Source(final GedObject parent, final ObjectId xref) {
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
