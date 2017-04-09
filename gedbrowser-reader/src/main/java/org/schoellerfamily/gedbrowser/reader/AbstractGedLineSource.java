package org.schoellerfamily.gedbrowser.reader;

/**
 * @author Dick Schoeller
 */
public abstract class AbstractGedLineSource implements GedLineSource {
    /**
     * Current line number.
     */
    private int maxLineNumber = 0;

    /**
     * @return the highest line number known in the file.
     */
    public final int getMaxLineNumber() {
        return maxLineNumber;
    }

    /**
     * @return the next line number in the file.
     */
    public final int nextLineNumber() {
        return maxLineNumber++;
    }
}
