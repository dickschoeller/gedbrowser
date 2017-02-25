package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Visitor intended to gather information about family structure.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
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
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        for (final GedObject gob : family.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        // This type doesn't contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        this.wifeFound = wife;
        if (wife.isSet()) {
            mother = wife.getMother();
            spouses.add(mother);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        // This type doesn't contribute to the process
    }
}
