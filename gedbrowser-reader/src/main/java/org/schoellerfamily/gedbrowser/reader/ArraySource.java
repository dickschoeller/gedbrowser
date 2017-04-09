package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * @author Dick Schoeller
 */
public final class ArraySource extends AbstractGedLineSource {
    /** */
    private final String[] arraySource;

    /**
     * Constructor.
     *
     * @param arraySource array of strings containing the gedcom content
     */
    @SuppressWarnings("PMD.UseVarargs")
    public ArraySource(final String[] arraySource) {
        this.arraySource = arraySource.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractGedLine createGedLine(final AbstractGedLine parent)
            throws IOException {
        final int lineNumber = nextLineNumber();
        if (lineNumber >= arraySource.length) {
            return null;
        }
        final String line = arraySource[lineNumber];
        return createGedLine(parent, line);
    }
}
