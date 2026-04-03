package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * Represents array source.
 *
 * @author Richard Schoeller
 */
public final class ArraySource extends AbstractGedLineSource {
    /** */
    private final String[] stringArray;

    /**
     * Creates a new ArraySource.
     *
     * @param stringArray the string array
     */
    @SuppressWarnings("PMD.UseVarargs")
    public ArraySource(final String[] stringArray) {
        this.stringArray = stringArray.clone();
    }

    /**
     * Creates the ged line.
     *
     * @param parent the parent
     * @return the resulting abstract ged line
     */
    @Override
    public AbstractGedLine createGedLine(final AbstractGedLine parent)
            throws IOException {
        final int lineNumber = nextLineNumber();
        if (lineNumber >= stringArray.length) {
            return null;
        }
        final String line = stringArray[lineNumber];
        return createGedLine(parent, line);
    }
}
