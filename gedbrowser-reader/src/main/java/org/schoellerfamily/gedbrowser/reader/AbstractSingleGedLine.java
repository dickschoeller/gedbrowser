package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the basic information read from a line of GEDCOM.
 * It does not account for hierarchy, just the specific line. Children are
 * accounted for at the next level of abstraction.
 *
 * @author Dick Schoeller
 */
@Getter
@Setter
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
    protected AbstractSingleGedLine(final int lineNumber) {
        this.lineNumber = lineNumber;
        this.gedObject = null;
    }
}
