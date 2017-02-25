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
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;

/**
 * Visitor for determining a person's relationships.
 *
 * @author Dick Schoeller
 */
public final class PersonVisitor implements GedObjectVisitor {
    /**
     * The person that we seem to be visiting.
     */
    private Person visitedPerson;

    /**
     * The list of families that visited person is a child of.
     */
    private final List<FamilyNavigator> familyCNavigators = new ArrayList<>();

    /**
     * The list of families that the person is a spouse of.
     */
    private final List<FamilyNavigator> familySNavigators = new ArrayList<>();

    /**
     * Get the family that the person is a child of.
     *
     * @return the family
     */
    public Family getFamily() {
        if (familyCNavigators.isEmpty()) {
            return new Family();
        }
        return familyCNavigators.get(0).getFamily();
    }

    /**
     * Get the families that the person is a spouse of.
     *
     * @return the list of families
     */
    public List<Family> getFamilies() {
        final List<Family> families = new ArrayList<>();
        for (final FamilyNavigator nav : familySNavigators) {
            families.add(nav.getFamily());
        }
        return families;
    }

    /**
     * Find the father of this person.  If not found, return an unset Person.
     *
     * @return the father.
     */
    public Person getFather() {
        if (familyCNavigators == null || familyCNavigators.isEmpty()) {
            return new Person();
        }
        return familyCNavigators.get(0).getFather();
    }

    /**
     * Find the mother of this person.  If not found, return an unset Person.
     *
     * @return the mother.
     */
    public Person getMother() {
        if (familyCNavigators == null || familyCNavigators.isEmpty()) {
            return new Person();
        }
        return familyCNavigators.get(0).getMother();
    }

    /**
     * Get the list of all of children of this person.
     *
     * @return the list of children
     */
    public List<Person> getChildren() {
        final List<Person> children = new ArrayList<>();
        for (final FamilyNavigator nav : familySNavigators) {
            children.addAll(nav.getChildren());
        }
        return children;
    }

    /**
     * Get the list of all of the spouses of this person.
     *
     * @return the list of spouses.
     */
    public List<Person> getSpouses() {
        List<Person> spouses = new ArrayList<>();
        for (final FamilyNavigator nav : familySNavigators) {
            final Person spouse = nav.getSpouse(visitedPerson);
            if (spouse.isSet()) {
                spouses.add(spouse);
            }
        }
        return spouses;
    }

    /**
     * @return list of all families that this person is a child of
     */
    public List<Family> getFamiliesC() {
        List<Family> families = new ArrayList<>();
        for (final FamilyNavigator nav: familyCNavigators) {
            final Family family = nav.getFamily();
            if (family.isSet()) {
                families.add(family);
            }
        }
        return families;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        familyCNavigators.add(new FamilyNavigator(famc));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        final FamilyNavigator navigator = new FamilyNavigator(fams);
        final Family family = navigator.getFamily();
        if (family.isSet()) {
            familySNavigators.add(navigator);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        visitedPerson = person;
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        // TODO Auto-generated method stub
    }

}
