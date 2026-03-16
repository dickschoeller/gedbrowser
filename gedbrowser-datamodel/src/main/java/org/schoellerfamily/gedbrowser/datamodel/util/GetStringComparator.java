package org.schoellerfamily.gedbrowser.datamodel.util;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;

import org.schoellerfamily.gedbrowser.datamodel.GetString;



/**
 * Compares get string values.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
@NoArgsConstructor
public final class GetStringComparator
        implements Comparator<GetString>, Serializable {

    /**
     * The serial version u i d value.
     */
    private static final long serialVersionUID = 2L;

    /**
     * Executes compare.
     *
     * @param arg0 the arg0
     * @param arg1 the arg1
     * @return the resulting int
     */
    @Override
    @SuppressWarnings("PMD.NPathComplexity")
    public int compare(final GetString arg0, final GetString arg1) {
        if (arg0 == null && arg1 == null) {
            return 0;
        }
        if (arg0 == null) {
            return -1;
        }
        if (arg1 == null) {
            return 1;
        }

        final String s1 = arg0.getString();
        final String s2 = arg1.getString();
        if (s1 == null && s2 == null) {
            return 0;
        }
        if (s1 == null) {
            return -1;
        }
        if (s2 == null) {
            return 1;
        }

        int thisMarker = 0;
        int thatMarker = 0;
        final int s1Length = s1.length();
        final int s2Length = s2.length();

        while (thisMarker < s1Length && thatMarker < s2Length) {
            final String thisChunk = getChunk(s1, s1Length, thisMarker);
            thisMarker += thisChunk.length();

            final String thatChunk = getChunk(s2, s2Length, thatMarker);
            thatMarker += thatChunk.length();

            final int result = compareChunksOfEitherType(thisChunk, thatChunk);
            if (result != 0) {
                return result;
            }
        }

        return s1Length - s2Length;
    }

    /**
     * argument is less than, equal to, or greater than the second.
     *
     * @param thisChunk the first chunk to be compared
     * @param thatChunk the second chunk to be compared
     * @return a negative integer, zero, or a positive integer as the first
     */
    private int compareChunksOfEitherType(final String thisChunk,
            final String thatChunk) {
        int result = 0;
        if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
            result = compareNumericChunks(thisChunk, thatChunk);
        } else {
            result = thisChunk.compareTo(thatChunk);
        }
        return result;
    }

    /**
     * argument is less than, equal to, or greater than the second.
     *
     * @param thisChunk the first chunk to be compared
     * @param thatChunk the second chunk to be compared
     * @return a negative integer, zero, or a positive integer as the first
     */
    private int compareNumericChunks(final String thisChunk,
            final String thatChunk) {
        final int diff = lengthDifference(thisChunk, thatChunk);
        if (diff != 0) {
            return diff;
        }
        return compareChunks(thisChunk, thatChunk);
    }

    /**
     * Performs length difference.
     *
     * @param thisChunk the first chunk to be compared
     * @param thatChunk the second chunk to be compared
     * @return the difference in length (l1 - l2)
     */
    private int lengthDifference(final String thisChunk,
            final String thatChunk) {
        return thisChunk.length() - thatChunk.length();
    }

    /**
     * Compare two chunks based on assumed same length. 0 if the same.
     *
     * @param thisChunk the first chunk to be compared
     * @param thatChunk the second chunk to be compared
     * @return the difference in length (l1 - l2)
     */
    private int compareChunks(final String thisChunk, final String thatChunk) {
        final int thisChunkLength = thisChunk.length();
        for (int i = 0; i < thisChunkLength; i++) {
            final int result = thisChunk.charAt(i) - thatChunk.charAt(i);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    /**
     * Get a chunk of the string that is all digits or all not digits. Length of
     * chunk is the longest same-type run.
     *
     * @param string  the string being chunked
     * @param slength the length of the string
     * @param marker  the starting index of the chunk
     * @return the chunk
     */
    private String getChunk(final String string, final int slength,
            final int marker) {
        if (isDigit(string.charAt(marker))) {
            return getNumericChunk(string, slength, marker);
        } else {
            return getTextChunk(string, slength, marker);
        }
    }

    /**
     * Check if character is a numeric digit.
     *
     * @param ch the character to check
     * @return true if it's a digit
     */
    private boolean isDigit(final char ch) {
        return ((ch >= '0') && (ch <= '9'));
    }

    /**
     * Get a chunk of the string that is consistently not digits. Length of chunk
     * stops at the first digit.
     *
     * @param string  the string being chunked
     * @param slength the length of the string
     * @param marker  the starting index of the chunk
     * @return the chunk
     */
    private String getTextChunk(final String string, final int slength,
            final int marker) {
        final StringBuilder chunk = new StringBuilder();
        for (int index = marker; index < slength; index++) {
            final char c = string.charAt(index);
            if (isDigit(c)) {
                break;
            }
            chunk.append(c);
        }
        return chunk.toString();
    }

    /**
     * Get a chunk of the string that is consistently digits. Length of chunk stops
     * at the first non-digit.
     *
     * @param string  the string being chunked
     * @param slength the length of the string
     * @param marker  the starting index of the chunk
     * @return the chunk
     */
    private String getNumericChunk(final String string, final int slength,
            final int marker) {
        final StringBuilder chunk = new StringBuilder();
        for (int index = marker; index < slength; index++) {
            final char c = string.charAt(index);
            if (!isDigit(c)) {
                break;
            }
            chunk.append(c);
        }
        return chunk.toString();
    }
}
