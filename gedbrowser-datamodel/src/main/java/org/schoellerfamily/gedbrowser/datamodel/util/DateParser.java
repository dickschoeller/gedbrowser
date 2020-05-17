package org.schoellerfamily.gedbrowser.datamodel.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Dick Schoeller
 */
public final class DateParser extends SimpleDateParser {
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

    /**
     * List of prefixes handled by strip and simple approximation.
     */
    private static final List<Pair<String, Approximation>> LIST = new ArrayList<>();
    static {
        LIST.add(new ImmutablePair<>(ABT, Approximation.ABOUT));
        LIST.add(new ImmutablePair<>(EST, Approximation.ABOUT));
        LIST.add(new ImmutablePair<>(BEF, Approximation.BEFORE));
        LIST.add(new ImmutablePair<>(AFT, Approximation.AFTER));
    }

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
        for (final Pair<String, Approximation> pair : LIST) {
            if (startsWithIgnoreCase(dateString, pair.getLeft())) {
                approximation = pair.getRight();
                return stripPrefix(dateString, pair.getLeft());
            }
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
            // BIC refers to LDS status "born in the covenant". Such a date can
            // be treated as a plain string with no approximation semantics.
            return "";
        }
        string = handleBizzareApproximations(string);
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

    private String handleBizzareApproximations(final String inString) {
        String string = inString;
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
            subtractDay(c);
        } else if (approximation == DateParser.Approximation.AFTER) {
            addDay(c);
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
            subtractMonth(c);
            endOfMonth(c);
        } else if (approximation == DateParser.Approximation.AFTER) {
            addMonth(c);
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
            addYear(c);
            lastMonth(c);
            endOfMonth(c);
        } else if (approximation == DateParser.Approximation.AFTER) {
            subtractYear(c);
            firstMonth(c);
            beginOfMonth(c);
        }
        return c;
    }
}
