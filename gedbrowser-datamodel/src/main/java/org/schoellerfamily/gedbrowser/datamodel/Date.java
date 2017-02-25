package org.schoellerfamily.gedbrowser.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Dick Schoeller
 */
public final class Date extends AbstractAttribute {

    /** */
    private Calendar sortDate;
    /** */
    private Calendar estimateDate;


    /**
     * @param parent parent object of this date
     */
    public Date(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this date
     * @param dateString date as a string
     */
    public Date(final GedObject parent, final String dateString) {
        super(parent, dateString);
    }

    /**
     * Get the date as a string.
     *
     * @return the date string
     */
    public String getDate() {
        return getString();
    }

    /**
     * @return the year as a string.
     */
    public String getYear() {
        if (getString() == null || getString().isEmpty()) {
            return "";
        }
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy",
                Locale.US);
        return format(dateFormatter, getSortCalendar());
    }

    /**
     * @return the string in a sortable format.
     */
    public String getSortDate() {
        if (getString() == null || getString().isEmpty()) {
            return "";
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, getSortCalendar());
    }

    /**
     * Return the sortable date in the form of a Calendar.
     *
     * @return the calendar
     */
    private Calendar getSortCalendar() {
        if (sortDate == null) {
            final DateParser parser = new DateParser(getDate());
            sortDate = parser.getSortCalendar();
        }
        return sortDate;
    }

    /**
     * Like sort date only we are starting in on correcting the problems with
     * approximations.
     *
     * @return string in the form yyyymmdd
     */
    public String getEstimateDate() {
        final DateParser parser = new DateParser(getDate());
        if (estimateDate == null) {
            estimateDate = parser.getEstimateCalendar();
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, estimateDate);
    }

    /**
     * @param formatter formatter to use
     * @param c calendar to format
     * @return string from formatting (can be "Unknown")
     */
    private String format(final SimpleDateFormat formatter, final Calendar c) {
        if (c == null) {
            return "Unknown";
        }
        return formatter.format(c.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
