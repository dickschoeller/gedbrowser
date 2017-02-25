package org.schoellerfamily.gedbrowser.geographics;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * Report the places for a given person.
 *
 * @author Dick Schoeller
 */
public class PersonPlaces extends GedPlaces implements Places {
    /**
     * The person we are reporting on.
     */
    private final Person person;

    /**
     * Constructor.
     *
     * @param person the person we are reporting on
     */
    public PersonPlaces(final Person person) {
        this.person = person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<Place> getPlaces() {
        final Set<Place> places = new TreeSet<>();
        for (final GedObject attribute : person.getAttributes()) {
            places.addAll(getPlaces(attribute));
        }
        final PersonNavigator navigator = new PersonNavigator(person);
        final List<Family> families = navigator.getFamilies();
        for (final Family family : families) {
            for (final GedObject attribute : family.getAttributes()) {
                places.addAll(getPlaces(attribute));
            }
        }
        return places;
    }
}
