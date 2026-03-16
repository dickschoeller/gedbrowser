package org.schoellerfamily.gedbrowser.datamodel.util;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Parses date values from textual input.
 *
 * @author Richard Schoeller
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
    /** */
    private static final List<Pair<String, Approximation>> LIST = List.of(
        new ImmutablePair<>(ABT, Approximation.ABOUT),
        new ImmutablePair<>(EST, Approximation.ABOUT),
        new ImmutablePair<>(BEF, Approximation.BEFORE),
        new ImmutablePair<>(AFT, Approximation.AFTER));

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
     * Creates a new DateParser.
     *
     * @param inputString the input string
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

    private boolean startsWithIgnoreCase(final String dateString,
            final String startString) {
        final String prefix = dateString.substring(0, startString.length());
        return prefix.equalsIgnoreCase(startString);
    }

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

    private String stripPrefix(final String input, final String searchString) {
        return input.substring(searchString.length(), input.length()).trim();
    }

    private String stripSuffix(final String input, final String searchString) {
        final String stripped = input.replace(searchString, "");
        return stripped.trim();
    }

    private String handleBetween(final String input) {
        final String outString = input.replace("BETWEEN ", "")
                .replace("BET ", "").replace("FROM ", "");
        return truncateAt(truncateAt(outString, " AND "), " TO ").trim();
    }

    private String truncateAt(final String input, final String searchString) {
        final int i = input.indexOf(searchString);
        if (i != -1) {
            return input.substring(0, i);
        }
        return input;
    }

    private String removeBeginningAt(final String input,
            final String searchString) {
        final int i = input.indexOf(searchString);
        if (i != -1) {
            return input.substring(i + 1, input.length());
        }
        return input;
    }

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

    private Calendar estimateDay(final String dateString) {
        final Calendar c = parseCalendar(dateString);
        if (approximation == DateParser.Approximation.BEFORE) {
            subtractDay(c);
        } else if (approximation == DateParser.Approximation.AFTER) {
            addDay(c);
        }
        return c;
    }

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
