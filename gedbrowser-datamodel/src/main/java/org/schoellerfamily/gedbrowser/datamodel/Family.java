package org.schoellerfamily.gedbrowser.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class Family extends GedObject implements FamilyLinkage {
    /**
     * Default constructor.
     */
    public Family() {
        super();
    }

    /**
     * @param parent parent object of this family
     */
    public Family(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this family
     * @param xref cross reference to this family
     */
    public Family(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
    }

    /**
     * Find the person who is the mother in this family. That person is also the
     * wife. :)
     *
     * @return the mother
     */
    public Person getMother() {
        final Wife wife = findWife();
        return wife.getMother();
    }

    /**
     * Find the person who is the father in this family. That person is also the
     * husband. :)
     *
     * @return the father
     */
    public Person getFather() {
        final Husband husband = findHusband();
        return husband.getFather();
    }

    /**
     * Get the person in this family who is the spouse of the person passed in.
     *
     * @param person the person whose spouse we are looking for.
     * @return the spouse.
     */
    public Person getSpouse(final GedObject person) {
        if (!isSet()) {
            return new Person();
        }
        if (person != null) {
            final boolean isPersonASpouse = isASpouse(person);
            if (!isPersonASpouse) {
                return new Person();
            }
        }
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Spouse)) {
                continue;
            }
            final Spouse link = (Spouse) gob;
            final Person spouse = link.getSpouse();
            if (!spouse.equals(person)) {
                return spouse;
            }
        }
        return new Person();
    }

    /**
     * Check whether the provided person is one of the spouses in this family.
     *
     * @param person person object that we're trying to determine whether it
     *            is a spouse in this family.
     * @return whether the provided person is referred to by one of the FAMS
     *         tags in this family.
     */
    private boolean isASpouse(final GedObject person) {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Spouse)) {
                continue;
            }
            final Person spouse = ((Spouse) gob).getSpouse();
            if (spouse.equals(person)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the list people who are spouses in this family.
     *
     * @return the list of spouses
     */
    public List<Person> getSpouses() {
        final List<Person> retVal = new ArrayList<Person>();
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Spouse)) {
                continue;
            }
            final Person spouse = ((Spouse) gob).getSpouse();
            retVal.add(spouse);
        }
        return retVal;
    }

    /**
     * Get the list of persons who are children of this family.
     *
     * @return the list of children.
     */
    public List<Person> getChildren() {
        if (!isSet()) {
            return new ArrayList<Person>();
        }
        final List<Person> retVal = new ArrayList<Person>();
        for (final GedObject gob : getAttributes()) {
            if (gob instanceof Child) {
                final Person child = ((Child) gob).getChild();
                if (child.isSet()) {
                    retVal.add(child);
                }
            }
        }
        return retVal;
    }
}
