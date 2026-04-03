package org.schoellerfamily.gedbrowser.reader;

/**
 * Visits ged line elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public interface GedLineVisitor {
    /**
     * @param gedline the object visited.
     */
    void visit(GedLine gedline);

    /**
     * @param gedfile the object visited.
     */
    void visit(GedFile gedfile);
}
