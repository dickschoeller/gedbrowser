package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Visitor intended to gather information about family structure.
 *
 * @author Dick Schoeller
 */
public final class FamilyVisitor implements GedObjectVisitor {
    /** */
    private Wife wifeFound;

    /** */
    private Person mother;

    /** */
    private Husband husbandFound;

    /** */
    private Person father;

    /** */
    private final List<Child> childList = new ArrayList<>();

    /** */
    private final List<Person> children = new ArrayList<>();

    /** */
    private final List<Person> spouses = new ArrayList<>();

    /**
     * Get the wife found in this scan. Can return an empty wife.
     *
     * @return the wife
     */
    public Wife getWife() {
        if (wifeFound == null) {
            return new Wife();
        }
        return wifeFound;
    }

    /**
     * Get the person who is the mother in this family. Can return an empty
     * person.
     *
     * @return the mother
     */
    public Person getMother() {
        if (mother == null) {
            return new Person();
        }
        return mother;
    }

    /**
     * Get the husband found in this scan. Can return an empty husband.
     *
     * @return the husband
     */
    public Husband getHusband() {
        if (husbandFound == null) {
            return new Husband();
        }
        return husbandFound;
    }

    /**
     * Get the person who is the father in this family. Can return an empty
     * person.
     *
     * @return the father
     */
    public Person getFather() {
        if (father == null) {
            return new Person();
        }
        return father;
    }

    /**
     * Get the list of children found in this scan.
     *
     * @return the list
     */
    public List<Child> getChildList() {
        return childList;
    }

    /**
     * Get the list of children found in this scan.
     *
     * @return the list
     */
    public List<Person> getChildren() {
        return children;
    }

    /**
     * Get the list of spouses found in this scan.
     *
     * @return the list
     */
    public List<Person> getSpouses() {
        return spouses;
    }

    /**
     * Executes visit.
     *
     * @param child the child
     */
    @Override
    public void visit(final Child child) {
        final Person person = child.getChild();
        if (child.isSet() && person.isSet()) {
            childList.add(child);
            children.add(person);
        }
    }

    /**
     * Executes visit.
     *
     * @param family the family
     */
    @Override
    public void visit(final Family family) {
        for (final GedObject gob : family.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * Executes visit.
     *
     * @param husband the husband
     */
    @Override
    public void visit(final Husband husband) {
        this.husbandFound = husband;
        if (husband.isSet()) {
            father = husband.getFather();
            spouses.add(father);
        }
    }

    /**
     * Executes visit.
     *
     * @param wife the wife
     */
    @Override
    public void visit(final Wife wife) {
        this.wifeFound = wife;
        if (wife.isSet()) {
            mother = wife.getMother();
            spouses.add(mother);
        }
    }
}
