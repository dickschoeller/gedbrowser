package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * @author Dick Schoeller
 */
public interface GedLineSource {
    /**
     * Create the next GedLine.
     *
     * @param parent the current parent.
     * @return the GedLine.
     * @throws IOException if this is a file source and there are problems.
     */
    AbstractGedLine createGedLine(AbstractGedLine parent) throws IOException;

    /**
     * @return the highest line number known in the file.
     */
    int getMaxLineNumber();

    /**
     * @return the next line number in the file.
     */
    int nextLineNumber();

    /**
     * Create a GedLine from the provided string.
     *
     * @param parent the current parent.
     * @param inLine the line of GEDCOM data
     * @return the GedLine
     */
    AbstractGedLine createGedLine(AbstractGedLine parent, String inLine);
}
