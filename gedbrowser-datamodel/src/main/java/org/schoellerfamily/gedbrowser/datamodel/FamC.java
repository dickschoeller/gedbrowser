package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class FamC extends AbstractLink {
    /**
     * Default constructor.
     */
    public FamC() {
        super();
    }

    /**
     * @param parent parent object of this link
     * @param string long version of type string
     */
    public FamC(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * @param parent person that is a child in the referred family
     * @param string long version of type string
     * @param xref the reference to a family object
     */
    public FamC(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
    }

    /**
     * Get the father of the family that this FamC refers to.
     *
     * @return the father
     */
    public Person getFather() {
        final Family toFamily = findFamily();
        return toFamily.getFather();
    }

    /**
     * Get the mother of the family that this FamC refers to.
     *
     * @return the mother
     */
    public Person getMother() {
        final Family toFamily = findFamily();
        return toFamily.getMother();
    }

    /**
     * Find the family that this FamC refers to. If not found, return an unset
     * Family.
     *
     * @return the family.
     */
    public Family findFamily() {
        final Family toFamily = (Family) find(getToString());
        if (toFamily == null) {
            return new Family();
        } else {
            return toFamily;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
