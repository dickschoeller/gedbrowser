package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;



/**
 * Represents abstract single ged line.
 *
 * @author Richard Schoeller
 */
@Getter
@Setter
@Accessors(makeFinal = true)
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
     * Executes abstract single ged line.
     *
     * @param lineNumber the line number
     */
    protected AbstractSingleGedLine(final int lineNumber) {
        this.lineNumber = lineNumber;
        this.gedObject = null;
    }
}
