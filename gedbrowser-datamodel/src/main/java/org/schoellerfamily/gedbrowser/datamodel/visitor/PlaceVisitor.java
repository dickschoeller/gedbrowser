package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.Set;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * @author Dick Schoeller
 */
public final class PlaceVisitor implements GedObjectVisitor {
    /** */
    private final Set<String> placeStrings = new TreeSet<>();

    /** */
    private final Set<Place> places = new TreeSet<>();

    /**
     * @return the places found in the visit
     */
    public Set<String> getPlaceStrings() {
        return placeStrings;
    }

    /**
     * @return the places found in the visit
     */
    public Set<Place> getPlaces() {
        return places;
    }

    /**
     * Visit an Attributes. Look at Attributes to find Places.
     *
     * @see GedObjectVisitor#visit(Attribute)
     */
    @Override
    public void visit(final Attribute attribute) {
        for (final GedObject gob : attribute.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * Visit a Family. Look at Attributes to find Places.
     *
     * @see GedObjectVisitor#visit(Family)
     */
    @Override
    public void visit(final Family family) {
        for (final GedObject gob : family.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * Visit a Person. Look at Attributes and Families to find Places.
     *
     * @see GedObjectVisitor#visit(Person)
     */
    @Override
    public void visit(final Person person) {
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
        final PersonNavigator navigator = new PersonNavigator(person);
        for (final Family family : navigator.getFamilies()) {
            family.accept(this);
        }
    }

    /**
     * Visit a Place. The names of Places are collected.
     *
     * @see GedObjectVisitor#visit(Place)
     */
    @Override
    public void visit(final Place place) {
        placeStrings.add(place.getString());
        places.add(place);
    }

    /**
     * Visit the Root. From here we will look through the top level objects for
     * Persons. Persons direct to Places and Places are what we really want.
     *
     * @see GedObjectVisitor#visit(Root)
     */
    @Override
    public void visit(final Root root) {
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    person.accept(this);
                }
            }
        }
    }
}
