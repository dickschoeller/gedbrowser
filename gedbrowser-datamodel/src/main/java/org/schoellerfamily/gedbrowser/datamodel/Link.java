package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents link in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Link extends AbstractLink {
    /**
     * Creates a new Link.
     *
     * @param parent the parent
     */
    public Link(final GedObject parent) {
        super(parent);
    }

    /**
     * Creates a new Link.
     *
     * @param parent the parent
     * @param string the string
     */
    public Link(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * Creates a new Link.
     *
     * @param parent the parent
     * @param string the string
     */
    public Link(final GedObject parent, final String string,
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
