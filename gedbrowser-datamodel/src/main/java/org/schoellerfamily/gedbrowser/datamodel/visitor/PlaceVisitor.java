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
 * Visits place elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class PlaceVisitor implements GedObjectVisitor {
    /** */
    private final Set<String> placeStrings = new TreeSet<>();

    /** */
    private final Set<Place> places = new TreeSet<>();

    /**
     * Gets the place strings.
     *
     * @return the place strings
     */
    public Set<String> getPlaceStrings() {
        return placeStrings;
    }

    /**
     * Gets the places.
     *
     * @return the places
     */
    public Set<Place> getPlaces() {
        return places;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final Attribute attribute) {
        for (final GedObject gob : attribute.getAttributes()) {
            gob.accept(this);
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
     * @param person the person
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
     * Executes visit.
     *
     * @param place the place
     */
    @Override
    public void visit(final Place place) {
        placeStrings.add(place.getString());
        places.add(place);
    }

    /**
     * Executes visit.
     *
     * @param root the root
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
