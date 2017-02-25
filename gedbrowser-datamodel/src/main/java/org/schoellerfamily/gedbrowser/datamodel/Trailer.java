package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Trailer extends AbstractSpecialObject {
    /**
     * Default constructor.
     */
    public Trailer() {
        super();
    }

    /**
     * @param parent parent object of this object
     * @param string long version of type string
     */
    public Trailer(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
