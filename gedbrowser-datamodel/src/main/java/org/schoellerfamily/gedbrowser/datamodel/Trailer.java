package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents trailer in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Trailer extends AbstractSpecialObject {
    /**
     * Creates a new Trailer.
     */
    public Trailer() {
        super();
    }

    /**
     * Creates a new Trailer.
     *
     * @param parent the parent
     * @param string the string
     */
    public Trailer(final GedObject parent, final String string) {
        super(parent, string);
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
