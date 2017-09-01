package org.schoellerfamily.gedbrowser.writer;

import java.util.Comparator;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Trying to sort IDs naturally. That is, I1, I2, I3... I10, I11, etc.
 *
 * @author Dick Schoeller
 */
public class IdComparator<T extends GedObject> implements Comparator<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(final T arg0, final T arg1) {
        if (arg0 == null || arg1 == null) {
            return 0;
        }

        final String s1 = arg0.getString();
        final String s2 = arg1.getString();
        if ((s1 == null) || (s2 == null)) 
        {
            return 0;
        }

        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();

        while (thisMarker < s1Length && thatMarker < s2Length) {
            String thisChunk = getChunk(s1, s1Length, thisMarker);
            thisMarker += thisChunk.length();

            String thatChunk = getChunk(s2, s2Length, thatMarker);
            thatMarker += thatChunk.length();

            int result = 0;
            if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
                result = compareNumericChunks(thisChunk, thatChunk);
            } else {
                result = thisChunk.compareTo(thatChunk);
            }

            if (result != 0) {
                return result;
            }
        }

        return s1Length - s2Length;
    }

    /**
     * @param thisChunk string 1
     * @param thatChunk string 2
     * @return the difference (0 if the same)
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
     * @param thisChunk string one
     * @param thatChunk string two
     * @return the difference in length (l1 - l2)
     */
    private int lengthDifference(final String thisChunk,
            final String thatChunk) {
        return thisChunk.length() - thatChunk.length();
    }

    /**
     * Compare two chunks based on assumed same length.
     *
     * @param thisChunk
     * @param thatChunk
     * @return
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
     * Get a chunk of the string that is consistent about being either a number
     * or not a number. Length of string is passed in for improved efficiency
     * (only need to calculate it once).
     *
     * @param string the string being chunked
     * @param slength the length of the string
     * @param marker the starting point for processing
     * @return the chunk
     */
    private final String getChunk(final String string, final int slength,
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
    private final boolean isDigit(final char ch) {
        return ((ch >= '0') && (ch <= '9'));
    }

    /**
     * Get a chunk of the string that is consistent about being not a number.
     * Length of string is passed in for improved efficiency (only need to
     * calculate it once).
     *
     * @param string the string being chunked
     * @param slength the length of the string
     * @param marker the starting point for processing
     * @return the chunk
     */
    private String getTextChunk(final String string, final int slength,
            final int marker) {
        final StringBuilder chunk = new StringBuilder();
        for (int index = marker; index < slength; index++) {
            final char c = string.charAt(index);
            if (isDigit(c))
                break;
            chunk.append(c);
        }
        return chunk.toString();
    }

    /**
     * Get a chunk of the string that is consistent about being either a number.
     * Length of string is passed in for improved efficiency (only need to
     * calculate it once).
     *
     * @param string the string being chunked
     * @param slength the length of the string
     * @param marker the starting point for processing
     * @return the chunk
     */
    private String getNumericChunk(final String string, final int slength,
            final int marker) {
        final StringBuilder chunk = new StringBuilder();
        for (int index = marker; index < slength; index++) {
            final char c = string.charAt(index);
            if (!isDigit(c))
                break;
            chunk.append(c);
        }
        return chunk.toString();
    }
}
