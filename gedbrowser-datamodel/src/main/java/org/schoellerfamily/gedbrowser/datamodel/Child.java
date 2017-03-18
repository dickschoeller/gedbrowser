package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Child extends AbstractLink {
    /**
     * Default constructor.
     */
    public Child() {
        super();
    }

    /**
     * @param parent parent object of this child
     * @param string long version of type string
     * @param xref the reference to a person object
     */
    public Child(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string);
        initLink(xref);
    }

    /**
     * Get the person that this object points to. If not found, return an unset
     * Person object.
     *
     * @return the child
     */
    public Person getChild() {
        final Person toPerson = (Person) find(getToString());
        if (toPerson == null) {
            return new Person();
        }
        return toPerson;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
