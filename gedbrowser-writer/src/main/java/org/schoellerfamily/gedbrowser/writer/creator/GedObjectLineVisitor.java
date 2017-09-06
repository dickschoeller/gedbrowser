package org.schoellerfamily.gedbrowser.writer.creator;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Tail;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;

/**
 * @author Dick Schoeller
 */
public interface GedObjectLineVisitor extends GedObjectVisitor {
    /** The maximum length of line content. */
    int MAX_LINE_LENGTH = 200;

    /**
     * @return the current level being processed
     */
    int initLevel();

    /**
     * @return the current level being processed
     */
    int getLevel();

    /**
     * Increment the current level being processed.
     *
     * @return the new level
     */
    int incrementLevel();

    /**
     * Decrement the current level being processed.
     *
     * @return the new level
     */
    int decrementLevel();

    /**
     * @return the collection of lines to add to
     */
    List<GedWriterLine> getLines();

    /**
     * Get the tag that goes with this long string. Sometimes a tag is used
     * that isn't in the list. Then we just return the tag.
     *
     * @param longString the long string
     * @return the tag
     */
    String mapTag(String longString);

    /**
     * Break up the input into continuations and concatenations.
     *
     * @param tail the tail item we are processing
     */
    default void contAndConc(final Tail tail) {
        final String tailString = tail.getTail();
        incrementLevel();
        final String[] continuations = tailString.split("\n");
        concatenation(tail, continuations[0]);
        for (int i = 1; i < continuations.length; i++) {
            final GedWriterLine line =
                    createContinuationLine(tail, continuations, i);
            getLines().add(line);
            concatenation(tail, continuations[i]);
        }
        decrementLevel();
    }

    /**
     * Break up with content at or less than 80 characters.
     *
     * @param tail the tail item we are processing
     * @param instring the input string
     */
    default void concatenation(final Tail tail, final String instring) {
        String string = instring.substring(trimToMaxLength(instring).length());
        while (!string.isEmpty()) {
            final String firstStringSegment = trimToMaxLength(string);
            final GedWriterLine line =
                    createConcatenationLine(tail, firstStringSegment);
            getLines().add(line);
            string = string.substring(firstStringSegment.length());
        }
    }

    /**
     * @param tail the tail we are processing
     * @param firstStringSegment the line segment for this CONT line
     * @return the writer line
     */
    default GedWriterLine createConcatenationLine(final Tail tail,
            final String firstStringSegment) {
        return new GedWriterLine(getLevel(), (GedObject) tail,
                getLevel() + " CONC " + firstStringSegment);
    }

    /**
     * @param tail the tail object being processed
     * @param continuations the array of continuations
     * @param i the index
     * @return the line
     */
    default GedWriterLine createContinuationLine(final Tail tail,
            final String[] continuations, final int i) {
        return new GedWriterLine(getLevel(), (GedObject) tail,
                getLevel()
                + " CONT "
                + trimToMaxLength(continuations[i]));
    }

    /**
     * Avoid trailing space.
     *
     * @param tail the object that has a tail of
     * @return the tail string, prepended with space if not empty
     */
    default String tail(final Tail tail) {
        final String tailString = tail.getTail();
        if (tailString.isEmpty()) {
            return "";
        }
        final int lineBreakIndex = tailString.indexOf("\n");
        if (lineBreakIndex >= 0) {
            return " "
                    + trimToMaxLength(tailString.substring(0, lineBreakIndex));
        }
        return " " + trimToMaxLength(tailString);
    }

    /**
     * Return a string trimmed to the max length. Trimmed string may not end
     * space. Remainder may not start with space.
     *
     * @param string the string to trim
     * @return the trimmed string
     */
    default String trimToMaxLength(final String string) {
        if (string.length() <= MAX_LINE_LENGTH) {
            return string;
        }
        for (int length = MAX_LINE_LENGTH; length > 0; length--) {
            if (string.charAt(length) != ' '
                    && string.charAt(length - 1) != ' ') {
                return string.substring(0, length);
            }
        }
        return "";
    }

    /**
     * @param gob the current object
     */
    default void handleChildren(final GedObject gob) {
        incrementLevel();
        for (final GedObject child : gob.getAttributes()) {
            child.accept(this);
        }
        decrementLevel();
    }
}
