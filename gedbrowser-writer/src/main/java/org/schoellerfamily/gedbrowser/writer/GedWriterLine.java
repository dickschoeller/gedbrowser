package org.schoellerfamily.gedbrowser.writer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public class GedWriterLine {
    /** */
    private final int level;
    /** */
    private final GedObject gedObject;
    /** */
    private final String line;

    /**
     * Creates a new GedWriterLine.
     *
     * @param level the level
     * @param gedObject the ged object
     * @param line the line
     */
    public GedWriterLine(final int level, final GedObject gedObject,
            final String line) {
        this.level = level;
        this.gedObject = gedObject;
        this.line = line;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the ged object.
     *
     * @return the ged object
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * Gets the line.
     *
     * @return the line
     */
    public String getLine() {
        return line;
    }
}
