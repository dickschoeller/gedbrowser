package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Wife extends AbstractLink implements Spouse {
    /**
     * Default constructor.
     */
    public Wife() {
        super();
    }

    /**
     * @param parent family that refers to this wife
     * @param string long version of type string
     * @param xref the reference to a person object
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
     * {@inheritDoc}
     */
    @Override
    public Person getSpouse() {
        return (Person) find(getToString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
