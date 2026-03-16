package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents child in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Child extends AbstractLink {
    /**
     * Creates a new Child.
     */
    public Child() {
        super();
    }

    /**
     * Creates a new Child.
     *
     * @param parent the parent
     * @param string the string
     */
    public Child(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string);
        initLink(xref);
    }

    /**
     * Get the person that this object points to. If not found, return an unset
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
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
