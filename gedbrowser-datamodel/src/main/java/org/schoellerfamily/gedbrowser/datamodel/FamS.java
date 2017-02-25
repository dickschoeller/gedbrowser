package org.schoellerfamily.gedbrowser.datamodel;

import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class FamS extends AbstractLink implements FamilyLinkage {
    /**
     * Default constructor.
     */
    public FamS() {
        super();
    }

    /**
     * @param parent parent object of this link
     * @param string long version of type string
     */
    public FamS(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * @param parent person who is a spouse in the referred family
     * @param string long version of type string
     * @param xref the reference to a family object
     */
    public FamS(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
    }

    /**
     * Get the person who is the spouse of the person passed in. A FamS points
     * to a family, which has 2 spouses. The person passed in should be one.
     * This method returns the other.
     *
     * @param person whose spouse we are looking for
     * @return the spouse
     */
    public Person getSpouse(final Person person) {
        final Family family = getFamily();
        return family.getSpouse(person);
    }

    /**
     * Get the family that this object points to.
     *
     * @return the family
     */
    public Family getFamily() {
        final Family family = (Family) find(getToString());
        if (family == null) {
            return new Family();
        } else {
            return family;
        }
    }

    /**
     * Get the children of the family that this FamS points to.
     *
     * @return the list of children.
     */
    public List<Person> getChildren() {
        final Family family = getFamily();
        return family.getChildren();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
