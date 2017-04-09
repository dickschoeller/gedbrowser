package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Dick Schoeller
 *
 */
public final class ReaderSource extends AbstractGedLineSource {
    /** */
    private final BufferedReader reader;

    /**
     * Constructor.
     *
     * @param reader the reader for getting GEDCOM from a file
     */
    public ReaderSource(final BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractGedLine createGedLine(final AbstractGedLine parent)
            throws IOException {
        return createGedLine(parent, reader.readLine());
    }
}
