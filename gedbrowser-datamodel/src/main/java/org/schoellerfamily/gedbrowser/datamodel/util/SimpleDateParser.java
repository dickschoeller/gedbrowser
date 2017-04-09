package org.schoellerfamily.gedbrowser.datamodel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Dick Schoeller
 */
public class SimpleDateParser {
    /**
     * Constructor.
     */
    public SimpleDateParser() {
    }

    /**
     * @param dateString the string being parsed into a calendar
     * @return the output string
     */
    protected Calendar parseCalendar(final String dateString) {
        SimpleDateFormat dateParser = new SimpleDateFormat("dd MMM yyyy",
                Locale.US);
        final Calendar c = Calendar.getInstance(Locale.US);
        try {
            c.setTime(dateParser.parse(dateString));
            return c;
        } catch (ParseException e) {
            dateParser = new SimpleDateFormat("MMM yyyy", Locale.US);
            try {
                c.setTime(dateParser.parse(dateString));
                return c;
            } catch (ParseException e1) {
                dateParser = new SimpleDateFormat("yyyy", Locale.US);
                try {
                    c.setTime(dateParser.parse(dateString));
                    return c;
                } catch (ParseException e2) {
                    return null;
                }
            }
        }
    }

    /**
     * @param c the calendar to manipulate
     */
    protected void addDay(final Calendar c) {
        c.add(Calendar.DAY_OF_MONTH, 1);
    }

    /**
     * @param c the calendar to manipulate
     */
    protected void subtractDay(final Calendar c) {
        c.add(Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * @param c the calendar to manipulate
     */
    protected void subtractMonth(final Calendar c) {
        c.add(Calendar.MONTH, -1);
    }

    /**
     * @param c the calendar to manipulate
     */
    protected void addMonth(final Calendar c) {
        c.add(Calendar.MONTH, 1);
    }

    /**
     * @param c the calendar to manipulate
     */
    protected void subtractYear(final Calendar c) {
        c.add(Calendar.YEAR, 1);
    }

    /**
     * @param c the calendar to manipulate
     */
    protected void addYear(final Calendar c) {
        c.add(Calendar.YEAR, -1);
    }

    /**
     * Adjust calendar to the beginning of the current month.
     *
     * @param c the calendar
     */
    protected void beginOfMonth(final Calendar c) {
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Adjust calendar to the end of the current month.
     *
     * @param c the calendar
     */
    protected void endOfMonth(final Calendar c) {
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Adjust calendar to the first month of the year.
     *
     * @param c the calendar
     */
    protected void firstMonth(final Calendar c) {
        c.set(Calendar.MONTH, c.getMinimum(Calendar.MONTH));
    }

    /**
     * Adjust calendar to the last month of the year.
     *
     * @param c the calendar
     */
    protected void lastMonth(final Calendar c) {
        c.set(Calendar.MONTH, c.getMaximum(Calendar.MONTH));
    }
}
