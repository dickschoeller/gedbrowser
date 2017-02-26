package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Link extends AbstractLink {
    /**
     * @param parent parent object of this link
     */
    public Link(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this link
     * @param string long version of type string
     */
    public Link(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * @param parent parent object of this link
     * @param string long version of type string
     * @param xref the reference to a GED object
     */
    public Link(final GedObject parent, final String string,
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
