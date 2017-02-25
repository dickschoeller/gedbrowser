package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Place extends AbstractAttribute
        implements Comparable<Place> {
    /**
     * Default constructor.
     */
    public Place() {
        super();
    }

    /**
     * @param parent parent object of this child
     * @param string place name
     */
    public Place(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Place other) {
        return getString().compareTo(other.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
