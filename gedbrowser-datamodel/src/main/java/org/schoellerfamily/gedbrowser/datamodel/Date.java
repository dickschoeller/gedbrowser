package org.schoellerfamily.gedbrowser.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import org.schoellerfamily.gedbrowser.datamodel.util.DateParser;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents date in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Date extends AbstractAttribute {

    /**
     * The sort date value.
     */
    private Calendar sortDate;
    /**
     * The estimate date value.
     */
    private Calendar estimateDate;


    /**
     * Creates a new Date.
     *
     * @param parent the parent
     */
    public Date(final GedObject parent) {
        super(parent);
    }

    /**
     * Creates a new Date.
     *
     * @param parent the parent
     * @param dateString the date string
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
     * Gets the year.
     *
     * @return the year
     */
    public String getYear() {
        if (StringUtils.isEmpty(getString())) {
            return "";
        }
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy",
                Locale.US);
        return format(dateFormatter, getSortCalendar());
    }

    /**
     * Gets the sort date.
     *
     * @return the sort date
     */
    public String getSortDate() {
        if (StringUtils.isEmpty(getString())) {
            return "";
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, getSortCalendar());
    }

    private Calendar getSortCalendar() {
        if (sortDate == null) {
            final DateParser parser = new DateParser(getDate());
            sortDate = parser.getSortCalendar();
        }
        return sortDate;
    }

    /**
     * Like sort date only we are starting in on correcting the problems with
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

    private String format(final SimpleDateFormat formatter, final Calendar c) {
        if (c == null) {
            return "Unknown";
        }
        return formatter.format(c.getTime());
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
