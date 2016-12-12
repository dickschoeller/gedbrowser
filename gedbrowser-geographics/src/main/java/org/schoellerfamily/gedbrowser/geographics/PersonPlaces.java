package org.schoellerfamily.gedbrowser.geographics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;

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
        final ArrayList<Family> families = new ArrayList<Family>();
        for (final Family family : person.getFamilies(families)) {
            for (final GedObject attribute : family.getAttributes()) {
                places.addAll(getPlaces(attribute));
            }
        }
        return places;
    }
}