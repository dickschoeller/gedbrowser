package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * @author Dick Schoeller
 *
 */
public class NullSource extends AbstractGedLineSource {
    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractGedLine createGedLine(final AbstractGedLine parent)
            throws IOException {
        return null;
    }
}
