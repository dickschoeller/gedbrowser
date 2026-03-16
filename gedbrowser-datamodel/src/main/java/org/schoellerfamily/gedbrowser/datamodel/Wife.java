package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents wife in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Wife extends AbstractLink implements Spouse {
    /**
     * Creates a new Wife.
     */
    public Wife() {
        super();
    }

    /**
     * Creates a new Wife.
     *
     * @param parent the parent
     * @param string the string
     * @param xref the xref
     */
    public Wife(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
    }

    /**
     * Get the person that this object points to. If not found return an unset
     * Person.
     *
     * @return the mother.
     */
    public Person getMother() {
        if (!isSet()) {
            return new Person();
        }
        final Person mother = (Person) find(getToString());
        if (mother == null) {
            return new Person();
        } else {
            return mother;
        }
    }

    /**
     * Gets the spouse.
     *
     * @return the spouse
     */
    @Override
    public Person getSpouse() {
        return (Person) find(getToString());
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
