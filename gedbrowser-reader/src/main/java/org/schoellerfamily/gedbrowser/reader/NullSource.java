package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * @author Dick Schoeller
 *
 */
public class NullSource extends AbstractGedLineSource {
    /**
     * Creates the ged line.
     *
     * @param parent the parent
     * @return the resulting abstract ged line
     */
    @Override
    public AbstractGedLine createGedLine(final AbstractGedLine parent)
            throws IOException {
        return null;
    }
}
