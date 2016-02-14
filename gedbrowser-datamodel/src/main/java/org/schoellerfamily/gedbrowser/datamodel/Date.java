package org.schoellerfamily.gedbrowser.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Dick Schoeller
 */
public final class Date extends AbstractAttribute {
    /** */
    private enum Approximation {
        /** */
        EXACT,
        /** */
        BEFORE,
        /** */
        AFTER,
        /** */
        ABOUT,
        /** */
        BETWEEN,
        /** */
        ESTIMATED
    };

    /** */
    private Calendar sortDate = null;
    /** */
    private Calendar estimateDate = null;
    /** */
    private Approximation approximation = null;

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
        if (sortDate == null) {
            final String dateString = stripApproximationKeywords(getDate());
            sortDate = parseCalendar(dateString);
        }
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy",
                Locale.US);
        return format(dateFormatter, sortDate);
    }

    /**
     * @return the string in a sortable format.
     */
    public String getSortDate() {
        if (sortDate == null) {
            final String dateString = stripApproximationKeywords(getDate());
            sortDate = parseCalendar(dateString);
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, sortDate);
    }

    /**
     * Like sort date only we are starting in on correcting the problems with
     * approximations.
     *
     * @return string in the form yyyymmdd
     */
    public String getEstimateDate() {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, getEstimateCalendar());
    }

    /**
     * @return the calendar object representing the estimated date
     */
    public Calendar getEstimateCalendar() {
        if (estimateDate == null) {
            final String dateString = stripApproximationKeywords(getDate());
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
        final Calendar c = parseCalendar(dateString);
        if (approximation == Approximation.BEFORE) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        } else if (approximation == Approximation.AFTER) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return c;
    }

    /**
     * @param dateString input date string
     * @return adjusted date
     */
    private Calendar estimateMonth(final String dateString) {
        final Calendar c = parseCalendar(dateString);
        if (approximation == Approximation.BEFORE) {
            c.add(Calendar.MONTH, -1);
            c.set(Calendar.DAY_OF_MONTH,
                    c.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if (approximation == Approximation.AFTER) {
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH,
                    c.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        return c;
    }

    /**
     * @param dateString input date string
     * @return adjusted date
     */
    private Calendar estimateYear(final String dateString) {
        final Calendar c = parseCalendar(dateString);
        if (approximation == Approximation.BEFORE) {
            c.add(Calendar.YEAR, -1);
            c.set(Calendar.MONTH, c.getMaximum(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH,
                    c.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if (approximation == Approximation.AFTER) {
            c.add(Calendar.YEAR, 1);
            c.set(Calendar.MONTH, c.getMinimum(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH,
                    c.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        return c;
    }

    /**
     * @param dateString the input date string
     * @return the output date string
     */
    private String stripApproximationKeywords(final String dateString) {
        approximation = Approximation.EXACT;
        if (dateString.startsWith("ABT ")) {
            approximation = Approximation.ABOUT;
            return dateString.replace("ABT ", "").trim();
        }
        if (dateString.startsWith("BEF ")) {
            approximation = Approximation.BEFORE;
            return dateString.replace("BEF ", "").trim();
        }
        if (dateString.startsWith("AFT ")) {
            approximation = Approximation.AFTER;
            return dateString.replace("AFT ", "").trim();
        }
        if (dateString.startsWith("BETWEEN ")) {
            approximation = Approximation.BETWEEN;
            String outString = dateString.replace("BETWEEN ", "");
            final int i = outString.indexOf(" AND ");
            if (i != -1) {
                outString = outString.substring(0, i);
            }
            return outString.trim();
        } else {
            final StringTokenizer st = new StringTokenizer(dateString, " ");
            final int allTokens = 3;
            if (st.countTokens() < allTokens) {
                approximation = Approximation.ABOUT;
            }
            return dateString.trim();
        }
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
     * @param dateString clean input string ("dd MMM yyyy")
     * @return the output string
     */
    private Calendar parseCalendar(final String dateString) {
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
}
