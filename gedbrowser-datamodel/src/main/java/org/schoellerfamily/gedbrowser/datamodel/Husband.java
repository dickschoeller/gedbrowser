package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Husband extends AbstractLink implements Spouse {
    /**
     * Default constructor.
     */
    public Husband() {
        super();
    }

    /**
     * @param parent parent object of this husband
     * @param string long version of type string
     */
    public Husband(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * @param parent family that refers to this husband
     * @param string long version of type string
     * @param xref the reference to a person object
     */
    public Husband(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
    }

    /**
     * Get the person that this object refers to. If not found return an unset
     * Person.
     *
     * @return the father
     */
    public Person getFather() {
        if (!isSet()) {
            return new Person();
        }
        final Person father = (Person) find(getToString());
        if (father == null) {
            return new Person();
        } else {
            return father;
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
