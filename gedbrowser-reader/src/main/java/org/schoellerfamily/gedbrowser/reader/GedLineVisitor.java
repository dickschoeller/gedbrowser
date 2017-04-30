package org.schoellerfamily.gedbrowser.reader;

/**
 * @author Dick Schoeller
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
