package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
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
     * Has a death attribute.
     */
    private boolean hasDeathAttribute;

    /**
     * Has a confidential setting.
     */
    private boolean isConfidential;

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
        final List<Person> spouses = new ArrayList<>();
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
        final List<Family> families = new ArrayList<>();
        for (final FamilyNavigator nav : familyCNavigators) {
            final Family family = nav.getFamily();
            if (family.isSet()) {
                families.add(family);
            }
        }
        return families;
    }

    /**
     * @return true if a death attribute is found
     */
    public boolean hasDeathAttribute() {
        return hasDeathAttribute;
    }

    /**
     * @return true if this person is confidential
     */
    public boolean isConfidential() {
        return isConfidential;
    }

    /**
     * Visit an Attribute. Certain Attributes contribute interest data.
     *
     * @see GedObjectVisitor#visit(Attribute)
     */
    @Override
    public void visit(final Attribute attribute) {
        if ("Death".equals(attribute.getString())) {
            hasDeathAttribute = true;
        }
        if ("Restriction".equals(attribute.getString())
                && "confidential".equals(attribute.getTail())) {
            isConfidential = true;
        }
    }

    /**
     * Visit a FamC. We will build up a collection of Navigators to the FamCs.
     *
     * @see GedObjectVisitor#visit(FamC)
     */
    @Override
    public void visit(final FamC famc) {
        familyCNavigators.add(new FamilyNavigator(famc));
    }

    /**
     * Visit a FamS. We will build up a collection of Navigators to the FamSs.
     *
     * @see GedObjectVisitor#visit(FamS)
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
     * Visit a Person. Look through the attributes of a Person for
     * relationships (FamC and FamS) and interesting Attributes.
     *
     * @see GedObjectVisitor#visit(Person)
     */
    @Override
    public void visit(final Person person) {
        visitedPerson = person;
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }
}
