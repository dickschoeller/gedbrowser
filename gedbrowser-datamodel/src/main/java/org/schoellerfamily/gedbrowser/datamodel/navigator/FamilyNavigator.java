package org.schoellerfamily.gedbrowser.datamodel.navigator;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.FamilyVisitor;

/**
 * @author Dick Schoeller
 */
public final class FamilyNavigator {
    /** */
    private final Family family;

    /** */
    private final FamilyVisitor visitor;

    /**
     * Constructor takes a family and visits it in preparation for navigation.
     *
     * @param family the family we are navigating
     */
    public FamilyNavigator(final Family family) {
        this.family = family;
        this.visitor = new FamilyVisitor();
        this.family.accept(visitor);
    }

    /**
     * Constructor takes a child, finds the family and visits it in preparation
     * for navigation.
     *
     * @param child the child whose family we are navigating
     */
    public FamilyNavigator(final Child child) {
        this(findFamily(child));
    }

    /**
     * Get the family for this child to build the navigator with.
     *
     * @param child the child
     * @return the family
     */
    private static Family findFamily(final Child child) {
        if (!child.isSet()) {
            return new Family();
        }
        final Family foundFamily = (Family) child.find(child.getFromString());
        if (foundFamily == null) {
            return new Family();
        }
        return foundFamily;
    }

    /**
     * Constructor takes a famc, finds the family and visits it in preparation
     * for navigation.
     *
     * @param famc the famc whose family we are navigating
     */
    public FamilyNavigator(final FamC famc) {
        this(findFamily(famc));
    }

    /**
     * Get the family for this famc to build the navigator with.
     *
     * @param famc the famc
     * @return the family
     */
    private static Family findFamily(final FamC famc) {
        if (!famc.isSet()) {
            return new Family();
        }
        final Family foundFamily = (Family) famc.find(famc.getToString());
        if (foundFamily == null) {
            return new Family();
        }
        return foundFamily;
    }

    /**
     * Constructor takes a fams, finds the family and visits it in preparation
     * for navigation.
     *
     * @param fams the fams whose family we are navigating
     */
    public FamilyNavigator(final FamS fams) {
        this(findFamily(fams));
    }

    /**
     * Get the family for this fams to build the navigator with.
     *
     * @param fams the fams
     * @return the family
     */
    private static Family findFamily(final FamS fams) {
        if (!fams.isSet()) {
            return new Family();
        }
        final Family foundFamily = (Family) fams.find(fams.getToString());
        if (foundFamily == null) {
            return new Family();
        }
        return foundFamily;
    }

    /**
     * Get the family that is in this navigator. This can be useful when the
     * navigator is built from one of the links to/from it.
     *
     * @return the family.
     */
    public Family getFamily() {
        return family;
    }

    /**
     * Get the person in this family who is the spouse of the person passed in.
     *
     * @param person the person whose spouse we are looking for.
     * @return the spouse.
     */
    public Person getSpouse(final GedObject person) {
        final List<Person> spouses = visitor.getSpouses();
        if (person != null && !spouses.contains(person)) {
            return new Person();
        }
        for (final Person spouse : spouses) {
            if (!spouse.equals(person)) {
                return spouse;
            }
        }
        return new Person();
    }

    /**
     * Get the list people who are spouses in this family.
     *
     * @return the list of spouses
     */
    public List<Person> getSpouses() {
        return visitor.getSpouses();
    }

    /**
     * Get the husband found in this scan. Can return an empty husband.
     *
     * @return the husband
     */
    public Husband getHusband() {
        return visitor.getHusband();
    }

    /**
     * Get the person who is the father in this family. Can return an empty
     * person.
     *
     * @return the father
     */
    public Person getFather() {
        return visitor.getFather();
    }

    /**
     * Get the wife found in this scan. Can return an empty husband.
     *
     * @return the wife
     */
    public Wife getWife() {
        return visitor.getWife();
    }

    /**
     * Get the person who is the mother in this family. Can return an empty
     * person.
     *
     * @return the mother
     */
    public Person getMother() {
        return visitor.getMother();
    }

    /**
     * Get the list of children found in this scan.
     *
     * @return the list
     */
    public List<Person> getChildren() {
        return visitor.getChildren();
    }
}
