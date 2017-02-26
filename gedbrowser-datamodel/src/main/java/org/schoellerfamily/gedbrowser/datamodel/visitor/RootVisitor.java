package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class RootVisitor implements GedObjectVisitor {
    /** */
    private final List<Person> persons = new ArrayList<>();

//    /** */
//    private final List<Family> families = new ArrayList<>();

    /**
     * @return the persons found in the scanned root
     */
    public List<Person> getPersons() {
        return persons;
    }

//    /**
//     * @return the families found in the scanned root
//     */
//    public List<Family> getFamilies() {
//        return families;
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        // Type does not contribute to algorithm
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
//        families.add(family);
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
        persons.add(person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        final Map<String, GedObject> objectMap = root.getObjects();
        final Collection<GedObject> objects = objectMap.values();
        for (final GedObject gob : objects) {
            gob.accept(this);
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
