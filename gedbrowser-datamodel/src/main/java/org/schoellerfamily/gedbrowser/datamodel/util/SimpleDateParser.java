package org.schoellerfamily.gedbrowser.datamodel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lombok.NoArgsConstructor;

/**
 * Parses simple date values from textual input.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public class SimpleDateParser {
    /**
     * Parses the calendar.
     *
     * @param dateString the date string
     * @return the resulting calendar
     */
    protected final Calendar parseCalendar(final String dateString) {
        SimpleDateFormat dateParser = new SimpleDateFormat("dd MMM yyyy",
                Locale.US);
        final Calendar c = Calendar.getInstance(Locale.US);
        try {
            c.setTime(dateParser.parse(dateString));
            return c;
        } catch (ParseException _) {
            dateParser = new SimpleDateFormat("MMM yyyy", Locale.US);
            try {
                c.setTime(dateParser.parse(dateString));
                return c;
            } catch (ParseException _) {
                dateParser = new SimpleDateFormat("yyyy", Locale.US);
                try {
                    c.setTime(dateParser.parse(dateString));
                    return c;
                } catch (ParseException _) {
                    return null;
                }
            }
        }
    }

    /**
     * Executes add day.
     *
     * @param c the c
     */
    protected final void addDay(final Calendar c) {
        c.add(Calendar.DAY_OF_MONTH, 1);
    }

    /**
     * Executes subtract day.
     *
     * @param c the c
     */
    protected final void subtractDay(final Calendar c) {
        c.add(Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * Executes subtract month.
     *
     * @param c the c
     */
    protected final void subtractMonth(final Calendar c) {
        c.add(Calendar.MONTH, -1);
    }

    /**
     * Executes add month.
     *
     * @param c the c
     */
    protected final void addMonth(final Calendar c) {
        c.add(Calendar.MONTH, 1);
    }

    /**
     * Executes subtract year.
     *
     * @param c the c
     */
    protected final void subtractYear(final Calendar c) {
        c.add(Calendar.YEAR, 1);
    }

    /**
     * Executes add year.
     *
     * @param c the c
     */
    protected final void addYear(final Calendar c) {
        c.add(Calendar.YEAR, -1);
    }

    /**
     * Adjust calendar to the beginning of the current month.
     *
     * @param c the calendar
     */
    protected final void beginOfMonth(final Calendar c) {
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Adjust calendar to the end of the current month.
     *
     * @param c the calendar
     */
    protected final void endOfMonth(final Calendar c) {
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Adjust calendar to the first month of the year.
     *
     * @param c the calendar
     */
    protected final void firstMonth(final Calendar c) {
        c.set(Calendar.MONTH, c.getMinimum(Calendar.MONTH));
    }

    /**
     * Adjust calendar to the last month of the year.
     *
     * @param c the calendar
     */
    protected final void lastMonth(final Calendar c) {
        c.set(Calendar.MONTH, c.getMaximum(Calendar.MONTH));
    }
}
