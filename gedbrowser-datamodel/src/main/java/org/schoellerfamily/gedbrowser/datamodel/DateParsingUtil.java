package org.schoellerfamily.gedbrowser.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Dick Schoeller
 */
public class DateParsingUtil {
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
    public DateParsingUtil(final String inputString) {
        this.inputString = inputString;
        approximation = Approximation.EXACT;
    }

    /**
     * @return the approximation level we interpret from the string
     */
    public Approximation getApproximation() {
        return approximation;
    }

    /**
     * @return the output date string
     */
    public String stripApproximationKeywords() {
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
        approximation = DateParsingUtil.Approximation.BETWEEN;
        String string = stripPrefix(dateString, "(").trim();
        string = stripSuffix(string, ")").trim();
        if (startsWithIgnoreCase(string, ABT)) {
            string = stripPrefix(string, ABT).trim();
            approximation = DateParsingUtil.Approximation.ABOUT;
        }
        if (startsWithIgnoreCase(string, AFT)) {
            string = stripPrefix(string, AFT).trim();
            approximation = DateParsingUtil.Approximation.AFTER;
        }
        if (startsWithIgnoreCase(string, BEF)) {
            string = stripPrefix(string, BEF).trim();
            approximation = DateParsingUtil.Approximation.BEFORE;
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
     * @return the output string
     */
    public Calendar parseCalendar() {
        return parseCalendar(inputString);
    }

    /**
     * @param dateString the string being parsed into a calendar
     * @return the output string
     */
    public static Calendar parseCalendar(final String dateString) {
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
