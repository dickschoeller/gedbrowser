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
    private final int lineNumber;
    /** */
    private int level;
    /** */
    private String xref = "";
    /** */
    private String tag = "";
    /** */
    private String tail = "";
    /** */
    private GedObject gedObject;

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
