package org.schoellerfamily.gedbrowser.datamodel.visitor;

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
public final class NameableVisitor implements GedObjectVisitor {
    /**
     * Hold the name for the person.
     */
    private Name thename;

    /**
     * Hold the visited nameable if there is one.
     */
    private GedObject visitedNameable;

    /**
     * @return the name of the person
     */
    public Name getNameAttribute() {
        if (thename == null) {
            return new Name(visitedNameable);
        }
        return thename;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return getNameAttribute().getSurname();
    }

    /**
     * @return the name to use in indices
     */
    public String getIndexName() {
        return getNameAttribute().getIndexName();
    }

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
        // Type does not contribute to algorithm
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
        if (this.thename != null) {
            // We always take the first listed name.
            return;
        }
        this.thename = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        visitedNameable = person;
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
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
        // Type does not contribute to algorithm
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
        visitedNameable = submittor;
        for (final GedObject gob : submittor.getAttributes()) {
            gob.accept(this);
        }
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
