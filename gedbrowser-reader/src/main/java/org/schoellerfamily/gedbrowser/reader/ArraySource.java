package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
public final class ArraySource extends AbstractGedLineSource {
    /** */
    private final String[] stringArray;

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
