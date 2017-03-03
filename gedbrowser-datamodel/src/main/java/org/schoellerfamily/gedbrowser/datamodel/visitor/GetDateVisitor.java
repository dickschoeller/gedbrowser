package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

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
     * Visit an Attribute. We will look at the attributes of this Attribute
     * for Dates. Once one is found, quit.
     *
     * @see GedObjectVisitor#visit(Attribute)
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
     * Visit a Date. Record the interesting information from that date.
     *
     * @see GedObjectVisitor#visit(Date)
     */
    @Override
    public void visit(final Date date) {
        dateString = date.getDate();
        yearString = date.getYear();
        sortDateString = date.getSortDate();
    }

    /**
     * Visit a Person. This is the primary focus of the visitation. From
     * here, interesting information is gathered from the attributes. Once a
     * date string is found, quit.
     *
     * @see GedObjectVisitor#visit(Person)
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
