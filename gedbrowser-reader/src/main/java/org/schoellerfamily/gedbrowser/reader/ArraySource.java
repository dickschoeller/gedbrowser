package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * @author Dick Schoeller
 */
public final class ArraySource extends AbstractGedLineSource {
    /** */
    private final String[] stringArray;

    /**
     * Constructor.
     *
     * @param stringArray array of strings containing the gedcom content
     */
    @SuppressWarnings("PMD.UseVarargs")
    public ArraySource(final String[] stringArray) {
        this.stringArray = stringArray.clone();
    }

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
