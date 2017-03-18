package org.schoellerfamily.gedbrowser.datamodel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.GodClass", "PMD.TooManyMethods" })
public final class DateParser {
    /** */
    private static final String ABT = "ABT ";
    /** */
    private static final String BEF = "BEF ";
    /** */
    private static final String AFT = "AFT ";
    /** */
    private static final String EST = "EST";
    /** */
    private static final String FROM = "FROM";
    /** */
    private static final String BET = "BET";

    /** */
    private final String inputString;
    /** */
    private Approximation approximation;

    /** */
    public enum Approximation {
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

    /**
     * @param inputString the string that we are going to parse
     */
    public DateParser(final String inputString) {
        this.inputString = inputString;
        approximation = Approximation.EXACT;
    }

    /**
     * Returns a sortable version of this date with estimation rules applied.
     *
     * @return the calendar object representing the estimated date
     */
    public Calendar getEstimateCalendar() {
        final String dateString = stripApproximationKeywords();
        if (dateString.isEmpty()) {
            return null;
        }
        return applyEstimationRules(dateString);
    }

    /**
     * Returns the sort version of the date. Does not have
     * estimation rules applied.
     *
     * @return return the sort string for this date
     */
    public Calendar getSortCalendar() {
        final String dateString = stripApproximationKeywords();
        if (dateString.isEmpty()) {
            return null;
        }
        return parseCalendar(dateString);
    }

    /**
     * @return the output date string
     */
    private String stripApproximationKeywords() {
        final String dateString = inputString;

        approximation = Approximation.EXACT;
        if (startsWithIgnoreCase(dateString, ABT)) {
            approximation = Approximation.ABOUT;
            return stripPrefix(dateString, ABT);
        }
        if (startsWithIgnoreCase(dateString, EST)) {
            approximation = Approximation.ABOUT;
            return stripPrefix(dateString, EST);
        }
        if (startsWithIgnoreCase(dateString, BEF)) {
            approximation = Approximation.BEFORE;
            return stripPrefix(dateString, BEF);
        }
        if (startsWithIgnoreCase(dateString, AFT)) {
            approximation = Approximation.AFTER;
            return stripPrefix(dateString, AFT);
        }
        if (dateString.charAt(0) == '(') {
            approximation = Approximation.ABOUT;
            return handleFTMBizzareDateFormat(dateString);
        }
        if (startsWithIgnoreCase(dateString, BET)
                || startsWithIgnoreCase(dateString, FROM)) {
            approximation = Approximation.BETWEEN;
            final String outString = handleBetween(dateString);
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
     * Does startsWith but ignoring the case.
     *
     * @param dateString the input string
     * @param startString the prefix we are checking for
     * @return true if dateString starts with a case insensitive match to start
     */
    private boolean startsWithIgnoreCase(final String dateString,
            final String startString) {
        final String prefix = dateString.substring(0, startString.length());
        return prefix.equalsIgnoreCase(startString);
    }

    /**
     * Handle some odd dates from family tree maker.
     *
     * @param dateString the input string
     * @return the stripped string
     */
    private String handleFTMBizzareDateFormat(final String dateString) {
        approximation = DateParser.Approximation.BETWEEN;
        String string = stripPrefix(dateString, "(").trim();
        string = stripSuffix(string, ")").trim();
        if ("BIC".equals(string)) {
            // TODO WTF is BIC? I see it in the data and it means nothing to me
            return "";
        }
        if (startsWithIgnoreCase(string, ABT)) {
            string = stripPrefix(string, ABT).trim();
            approximation = DateParser.Approximation.ABOUT;
        }
        if (startsWithIgnoreCase(string, AFT)) {
            string = stripPrefix(string, AFT).trim();
            approximation = DateParser.Approximation.AFTER;
        }
        if (startsWithIgnoreCase(string, BEF)) {
            string = stripPrefix(string, BEF).trim();
            approximation = DateParser.Approximation.BEFORE;
        }
        if (startsWithIgnoreCase(string, FROM)) {
            string = stripPrefix(string, FROM).trim();
        }
        string = truncateAt(string, "-");
        string = truncateAt(string, " TO ");
        if (string.length() <= 2) {
            // Probably like 10-11 Nov 2017
            string = stripPrefix(dateString, "(").trim();
            string = stripSuffix(string, ")").trim();
            string = removeBeginningAt(string, "-");
            string = removeBeginningAt(string, " TO ");
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
        return input.substring(searchString.length(), input.length()).trim();
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
        return stripped.trim();
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
        return truncateAt(truncateAt(outString, " AND "), " TO ").trim();
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
     * @param dateString the string being parsed into a calendar
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
        if (approximation == DateParser.Approximation.BEFORE) {
            add(c, Calendar.DAY_OF_MONTH, -1);
        } else if (approximation == DateParser.Approximation.AFTER) {
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
        if (approximation == DateParser.Approximation.BEFORE) {
            add(c, Calendar.MONTH, -1);
            endOfMonth(c);
        } else if (approximation == DateParser.Approximation.AFTER) {
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
        if (approximation == DateParser.Approximation.BEFORE) {
            add(c, Calendar.YEAR, -1);
            lastMonth(c);
            endOfMonth(c);
        } else if (approximation == DateParser.Approximation.AFTER) {
            add(c, Calendar.YEAR, 1);
            firstMonth(c);
            beginOfMonth(c);
        }
        return c;
    }

    /**
     * Add or subtract the specified amount of time the given calendar field
     * based on the calendar's rules. For example, to subtract 5 days from the
     * current time of the calendar, call: add(c, Calendar.DAY_OF_MONTH, -5).
     * @param c the calendar
     * @param field the calendar field
     * @param amount the amount of date or time to be added to the field
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
}
