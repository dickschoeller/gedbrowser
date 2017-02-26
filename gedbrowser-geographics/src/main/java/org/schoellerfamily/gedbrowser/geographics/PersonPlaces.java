package org.schoellerfamily.gedbrowser.geographics;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PlaceVisitor;

/**
 * Report the places for a given person.
 *
 * @author Dick Schoeller
 */
public class PersonPlaces implements Places {
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
        final PlaceVisitor visitor = new PlaceVisitor();
        person.accept(visitor);
        return visitor.getPlaces();
    }
}
