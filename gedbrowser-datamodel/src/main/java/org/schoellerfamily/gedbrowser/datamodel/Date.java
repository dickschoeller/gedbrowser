package org.schoellerfamily.gedbrowser.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods" })
public final class Date extends AbstractAttribute {

    /** */
    private Calendar sortDate;
    /** */
    private Calendar estimateDate;
    /** */
    private DateParsingUtil.Approximation approximation;


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
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy",
                Locale.US);
        return format(dateFormatter, getSortCalendar());
    }

    /**
     * @return the string in a sortable format.
     */
    public String getSortDate() {
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
            final DateParsingUtil parser = new DateParsingUtil(getDate());
            final String dateString = parser.stripApproximationKeywords();
            approximation = parser.getApproximation();
            if (dateString.isEmpty()) {
                return null;
            }
            sortDate = DateParsingUtil.parseCalendar(dateString);
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
        final Calendar estimateCalendar = getEstimateCalendar();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, estimateCalendar);
    }

    /**
     * @return the calendar object representing the estimated date
     */
    public Calendar getEstimateCalendar() {
        if (estimateDate == null) {
            final DateParsingUtil parser = new DateParsingUtil(getDate());
            final String dateString = parser.stripApproximationKeywords();
            approximation = parser.getApproximation();
            if (dateString.isEmpty()) {
                return null;
            }
            estimateDate = applyEstimationRules(dateString);
        }
        return estimateDate;
    }

    /**
     * @param dateString input date string
     * @return the adjusted string
     */
    private Calendar applyEstimationRules(final String dateString) {
        switch (new StringTokenizer(dateString, " ").countTokens()) {
        case 0:
            return null;
        case 1:
            return estimateYear(dateString);
        case 2:
            return estimateMonth(dateString);
        default:
            return estimateDay(dateString);
        }
    }

    /**
     * @param dateString input date string
     * @return adjusted date
     */
    private Calendar estimateDay(final String dateString) {
        final Calendar c = DateParsingUtil.parseCalendar(dateString);
        if (approximation == DateParsingUtil.Approximation.BEFORE) {
            add(c, Calendar.DAY_OF_MONTH, -1);
        } else if (approximation == DateParsingUtil.Approximation.AFTER) {
            add(c, Calendar.DAY_OF_MONTH, 1);
        }
        return c;
    }

    /**
     * @param dateString input date string
     * @return adjusted date
     */
    private Calendar estimateMonth(final String dateString) {
        final Calendar c = DateParsingUtil.parseCalendar(dateString);
        if (approximation == DateParsingUtil.Approximation.BEFORE) {
            add(c, Calendar.MONTH, -1);
            endOfMonth(c);
        } else if (approximation == DateParsingUtil.Approximation.AFTER) {
            add(c, Calendar.MONTH, 1);
            beginOfMonth(c);
        }
        return c;
    }

    /**
     * @param dateString input date string
     * @return adjusted date
     */
    private Calendar estimateYear(final String dateString) {
        final Calendar c = DateParsingUtil.parseCalendar(dateString);
        if (approximation == DateParsingUtil.Approximation.BEFORE) {
            add(c, Calendar.YEAR, -1);
            lastMonth(c);
            endOfMonth(c);
        } else if (approximation == DateParsingUtil.Approximation.AFTER) {
            add(c, Calendar.YEAR, 1);
            firstMonth(c);
            beginOfMonth(c);
        }
        return c;
    }

    /**
     * Adds or subtracts the specified amount of time to the given calendar
     * field, based on the calendar's rules. For example, to subtract 5 days
     * from the current time of the calendar, you can achieve it by calling:
     * add(c, Calendar.DAY_OF_MONTH, -5).
     *
     * @param c the calendar
     * @param field the calendar field
     * @param amount the amount of date or time to be added to the field.
     */
    private void add(final Calendar c, final int field, final int amount) {
        c.add(field, amount);
    }

    /**
     * Adjust calendar to the beginning of the current month.
     *
     * @param c the calendar
     */
    private void beginOfMonth(final Calendar c) {
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Adjust calendar to the end of the current month.
     *
     * @param c the calendar
     */
    private void endOfMonth(final Calendar c) {
        c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Adjust calendar to the first month of the year.
     *
     * @param c the calendar
     */
    private void firstMonth(final Calendar c) {
        c.set(Calendar.MONTH, c.getMinimum(Calendar.MONTH));
    }

    /**
     * Adjust calendar to the last month of the year.
     *
     * @param c the calendar
     */
    private void lastMonth(final Calendar c) {
        c.set(Calendar.MONTH, c.getMaximum(Calendar.MONTH));
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
}
