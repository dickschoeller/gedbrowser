package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Child extends AbstractLink implements FamilyLinkage {
    /**
     * Default constructor.
     */
    public Child() {
        super();
    }

    /**
     * @param parent
     *            parent object of this child
     */
    public Child(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent
     *            parent object of this child
     * @param string
     *            long version of type string
     */
    public Child(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * @param parent
     *            parent object of this child
     * @param string
     *            long version of type string
     * @param xref
     *            the reference to a person object
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
     * @return the chile
     */
    public Person getChild() {
        final Person toPerson = (Person) find(getToString());
        if (toPerson == null) {
            return new Person();
        }
        return toPerson;
    }

    /**
     * Get the father from the family that this object comes from. If not found,
     * return an unset Person object.
     *
     * @return the father
     */
    public Person getFather() {
        if (!isSet()) {
            return new Person();
        }
        final Family family = (Family) find(getFromString());
        if (family == null) {
            return new Person();
        }
        return family.getFather();
    }

    /**
     * Get the mother from the family that this object comes from. If not found,
     * return an unset Person object.
     *
     * @return the mother
     */
    public Person getMother() {
        if (!isSet()) {
            return new Person();
        }
        final Family family = (Family) find(getFromString());
        if (family == null) {
            return new Person();
        }
        return family.getMother();
    }
}
