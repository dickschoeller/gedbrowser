package org.schoellerfamily.gedbrowser.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods", "PMD.GodClass" })
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
    private Calendar sortDate;
    /** */
    private Calendar estimateDate;
    /** */
    private Approximation approximation;

    /** */
    private static final String ABT = "ABT ";
    /** */
    private static final String BEF = "BEF ";
    /** */
    private static final String AFT = "AFT ";

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
            if (dateString.isEmpty()) {
                return "";
            }
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
            if (dateString.isEmpty()) {
                return "";
            }
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
        final Calendar estimateCalendar = getEstimateCalendar();
        if (estimateCalendar == null) {
            return "";
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.US);
        return format(formatter, estimateCalendar);
    }

    /**
     * @return the calendar object representing the estimated date
     */
    public Calendar getEstimateCalendar() {
        if (estimateDate == null) {
            final String dateString = stripApproximationKeywords(getDate());
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
        final Calendar c = parseCalendar(dateString);
        if (approximation == Approximation.BEFORE) {
            add(c, Calendar.DAY_OF_MONTH, -1);
        } else if (approximation == Approximation.AFTER) {
            add(c, Calendar.DAY_OF_MONTH, 1);
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
            add(c, Calendar.MONTH, -1);
            endOfMonth(c);
        } else if (approximation == Approximation.AFTER) {
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
        final Calendar c = parseCalendar(dateString);
        if (approximation == Approximation.BEFORE) {
            add(c, Calendar.YEAR, -1);
            lastMonth(c);
            endOfMonth(c);
        } else if (approximation == Approximation.AFTER) {
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
     * @param dateString the input date string
     * @return the output date string
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity",
            "PMD.ModifiedCyclomaticComplexity", "PMD.NPathComplexity",
            "PMD.StdCyclomaticComplexity" })
    private String stripApproximationKeywords(final String dateString) {
        approximation = Approximation.EXACT;
        if (dateString.contains("UNKNOWN")) {
            return "";
        }
        if (dateString.startsWith(ABT)) {
            approximation = Approximation.ABOUT;
            return stripPrefix(dateString, ABT);
        }
        if (dateString.startsWith("EST")) {
            approximation = Approximation.ABOUT;
            return stripPrefix(dateString, "EST");
        }
        if (dateString.startsWith("Abt")) {
            approximation = Approximation.ABOUT;
            return stripPrefix(dateString, "Abt");
        }
        if (dateString.startsWith(BEF)) {
            approximation = Approximation.BEFORE;
            return stripPrefix(dateString, BEF);
        }
        if (dateString.startsWith("Bef")) {
            approximation = Approximation.BEFORE;
            return stripPrefix(dateString, "Bef");
        }
        if (dateString.startsWith(AFT)) {
            approximation = Approximation.AFTER;
            return stripPrefix(dateString, AFT);
        }
        if (dateString.startsWith("Aft")) {
            approximation = Approximation.AFTER;
            return stripPrefix(dateString, "Aft");
        }
        if (dateString.charAt(0) == '(') {
            approximation = Approximation.ABOUT;
            return handleFTMBizzareDateFormat(dateString);
        }
        if (dateString.startsWith("BET") || dateString.startsWith("FROM")) {
            approximation = Approximation.BETWEEN;
            final String outString = handleBetween(dateString);
            return trim(outString);
        } else {
            final StringTokenizer st = new StringTokenizer(dateString, " ");
            final int allTokens = 3;
            if (st.countTokens() < allTokens) {
                approximation = Approximation.ABOUT;
            }
            return trim(dateString);
        }
    }

    /**
     * Handle some odd dates from family tree maker.
     *
     * @param dateString the input string
     * @return the stripped string
     */
    private String handleFTMBizzareDateFormat(final String dateString) {
        approximation = Approximation.BETWEEN;
        String string = stripPrefix(dateString, "(");
        string = stripSuffix(string, ")");
        string = stripSuffix(string, "Abt");
        string = stripSuffix(string, "Bef");
        string = truncateAt(string, "-");
        if (string.length() <= 2) {
            // Probably like 10-11 Nov 2017
            string = stripPrefix(dateString, "(");
            string = stripSuffix(string, ")");
            string = removeBeginningAt(string, "-");
        }
        return string;
    }

    /**
     * Strip the searchString with and trim the result.
     *
     * @param input the source string
     * @param searchString the string to look for in the source
     * @return the stripped string
     */
    private String stripPrefix(final String input, final String searchString) {
        final String stripped = input.replace(searchString, "");
        return trim(stripped);
    }

    /**
     * Strip the searchString with and trim the result.
     *
     * @param input the source string
     * @param searchString the string to look for in the source
     * @return the stripped string
     */
    private String stripSuffix(final String input, final String searchString) {
        final String stripped = input.replace(searchString, "");
        return trim(stripped);
    }

    /**
     * Trim the string.
     *
     * @param input the source string
     * @return trimmed string
     */
    private String trim(final String input) {
        return input.trim();
    }

    /**
     * Process between syntax. Just leave the beginning date.
     *
     * @param input the source string
     * @return the stripped result
     */
    private String handleBetween(final String input) {
        final String outString = input.replace("BETWEEN ", "")
                .replace("BET ", "").replace("FROM ", "");
        return truncateAt(truncateAt(outString, " AND "), " TO ");
    }

    /**
     * Truncate the input string after the first occurrence of the
     * searchString.
     *
     * @param input the source string
     * @param searchString the string to look for in the source
     * @return the truncated string
     */
    private String truncateAt(final String input, final String searchString) {
        final int i = input.indexOf(searchString);
        if (i != -1) {
            return input.substring(0, i);
        }
        return input;
    }

    /**
     * Lop off the beginning of the string up through the searchString.
     *
     * @param input the source string
     * @param searchString the string to look for in the source
     * @return the truncated string
     */
    private String removeBeginningAt(final String input,
            final String searchString) {
        final int i = input.indexOf(searchString);
        if (i != -1) {
            return input.substring(i + 1, input.length());
        }
        return input;
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
