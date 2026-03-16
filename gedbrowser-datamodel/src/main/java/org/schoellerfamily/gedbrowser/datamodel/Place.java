package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents place in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Place extends AbstractAttribute
        implements Comparable<Place> {
    /**
     * Creates a new Place.
     */
    public Place() {
        super();
    }

    /**
     * Creates a new Place.
     *
     * @param parent the parent
     * @param string the string
     */
    public Place(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * Returns the int.
     *
     * @param other the other
     * @return the resulting int
     */
    @Override
    public int compareTo(final Place other) {
        return getString().compareTo(other.getString());
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
