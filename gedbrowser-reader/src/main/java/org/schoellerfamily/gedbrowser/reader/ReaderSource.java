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
     * Creates a new ReaderSource.
     *
     * @param reader the reader
     */
    public ReaderSource(final BufferedReader reader) {
        this.reader = reader;
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
        final String line = reader.readLine();
        return createGedLine(parent, line);
    }
}
