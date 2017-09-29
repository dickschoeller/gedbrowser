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
     * @param level the GEDCOM object level
     * @param gedObject the GEDCOM object that this line represents
     * @param line the line
     */
    public GedWriterLine(final int level, final GedObject gedObject,
            final String line) {
        this.level = level;
        this.gedObject = gedObject;
        this.line = line;
    }

    /**
     * @return the level for the current object
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the GEDCOM object
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * @return the string for this line
     */
    public String getLine() {
        return line;
    }
}
