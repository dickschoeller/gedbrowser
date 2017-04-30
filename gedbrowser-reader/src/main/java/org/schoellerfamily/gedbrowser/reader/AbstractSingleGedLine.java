package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * This class represents the basic information read from a line of GEDCOM.
 * It does not account for hierarchy, just the specific line. Children are
 * accounted for at the next level of abstraction.
 *
 * @author Dick Schoeller
 */
public abstract class AbstractSingleGedLine implements GedObjectHolder {
    /** */
    private final transient int lineNumber;
    /** */
    private transient int level;
    /** */
    private transient String xref = "";
    /** */
    private transient String tag = "";
    /** */
    private transient String tail = "";
    /** */
    private transient GedObject gedObject;

    /**
     * @param lineNumber the line number
     */
    public AbstractSingleGedLine(final int lineNumber) {
        this.lineNumber = lineNumber;
        this.gedObject = null;
    }

    /**
     * Get the line number.
     *
     * @return the line number.
     */
    public final int getLineNumber() {
        return lineNumber;
    }

    /**
     * @return the current GEDCOM level
     */
    public final int getLevel() {
        return level;
    }

    /**
     * @param level the GEDCOM level for this line
     */
    public final void setLevel(final int level) {
        this.level = level;
    }

    /**
     * @return the cross reference string for this line
     */
    public final String getXref() {
        return xref;
    }

    /**
     * @param xref the cross reference string for this line
     */
    public final void setXref(final String xref) {
        this.xref = xref;
    }

    /**
     * @return the GEDCOM tag on this line
     */
    public final String getTag() {
        return tag;
    }

    /**
     * @param tag the GEDCOM tag on this line
     */
    public final void setTag(final String tag) {
        this.tag = tag;
    }

    /**
     * @return whatever text follows the tag
     */
    public final String getTail() {
        return tail;
    }

    /**
     * @param tail the text following the tag
     */
    public final void setTail(final String tail) {
        this.tail = tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setGedObject(final GedObject gedObject) {
        this.gedObject = gedObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GedObject getGedObject() {
        return gedObject;
    }
}
