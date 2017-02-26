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
 * Visitor to get the "best" date from a GedObject.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class GetDateVisitor implements GedObjectVisitor {
    /**
     * Accumulates the best date information.
     */
    private String dateString = "";

    /**
     * Accumulates the best year information.
     */
    private String yearString = "";

    /**
     * Accumulates the best sort date information.
     */
    private String sortDateString = "";

    /**
     * Identifies the type we are looking for.
     */
    private final String type;

    /**
     * Default constructor.
     */
    public GetDateVisitor() {
        type = "";
    }

    /**
     * Constructor identify a date type to get (Birth, Death, etc.).
     *
     * @param type the type of date to get
     */
    public GetDateVisitor(final String type) {
        this.type = type;
    }

    /**
     * @return the accumulated date string
     */
    public String getDate() {
        return dateString;
    }

    /**
     * @return the accumulated year string
     */
    public String getYear() {
        return yearString;
    }

    /**
     * @return the accumulated date string
     */
    public String getSortDate() {
        return sortDateString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        if (!dateString.isEmpty()) {
            return;
        }
        if (type.isEmpty() || type.equals(attribute.getString())) {
            for (final GedObject gedObject : attribute.getAttributes()) {
                gedObject.accept(this);
                if (!dateString.isEmpty()) {
                    break;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        if (!dateString.isEmpty()) {
            return;
        }
        dateString = date.getDate();
        yearString = date.getYear();
        sortDateString = date.getSortDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
            if (!dateString.isEmpty()) {
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        // Does not contribute to the process
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        // Does not contribute to the process
    }
}
