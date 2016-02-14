package org.schoellerfamily.gedbrowser.datamodel;

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
     * @param parent parent object of this wife
     */
    public Wife(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this wife
     * @param string long version of type string
     */
    public Wife(final GedObject parent, final String string) {
        super(parent, string);
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
}
