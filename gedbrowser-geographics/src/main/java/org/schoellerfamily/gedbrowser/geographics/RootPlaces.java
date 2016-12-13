package org.schoellerfamily.gedbrowser.geographics;

import java.util.Collection;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Report the places for a data set.
 *
 * @author Dick Schoeller
 */
public class RootPlaces extends GedPlaces implements Places {
    /**
     * The root object of the data set to report.
     */
    private final Root root;

    /**
     * Constructor.
     *
     * @param root the root object of the data set to report
     */
    public RootPlaces(final Root root) {
        this.root = root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<Place> getPlaces() {
        final Collection<Place> placeset = new TreeSet<>();
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    final Places places = personPlaces(person);
                    placeset.addAll(places.getPlaces());
                }
            }
        }
        return placeset;
    }

    /**
     * Get a Places object for this person.
     *
     * @param person the person to report on
     * @return the places object to do the reporting
     */
    private Places personPlaces(final Person person) {
        return new PersonPlaces(person);
    }
}
