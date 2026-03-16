package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Visits get date elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
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
     * Creates a new GetDateVisitor.
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
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {
        return dateString;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public String getYear() {
        return yearString;
    }

    /**
     * Gets the sort date.
     *
     * @return the sort date
     */
    public String getSortDate() {
        return sortDateString;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final Attribute attribute) {
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
     * Executes visit.
     *
     * @param date the date
     */
    @Override
    public void visit(final Date date) {
        dateString = date.getDate();
        final StringBuilder sb = new StringBuilder(dateString);
        dateString = date.getDate();
        yearString = date.getYear();
        sortDateString = date.getSortDate();
        final GetTimeVisitor timeVisitor = new GetTimeVisitor();
        for (final GedObject gob : date.getAttributes()) {
            gob.accept(timeVisitor);
            if (!timeVisitor.getTimeString().isEmpty()) {
                sb.append(" ").append(timeVisitor.getTimeString());
                break;
            }
        }
        dateString = sb.toString();
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
            if (!dateString.isEmpty()) {
                break;
            }
        }
    }
}
