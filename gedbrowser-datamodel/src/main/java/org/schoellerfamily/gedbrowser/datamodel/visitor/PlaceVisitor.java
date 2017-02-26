package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.Set;
import java.util.TreeSet;

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
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
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
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        for (final GedObject gob : attribute.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        // Type does not contribute to algorithm
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
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        placeStrings.add(place.getString());
        places.add(place);
    }

    /**
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        // Type does not contribute to algorithm
    }
}
