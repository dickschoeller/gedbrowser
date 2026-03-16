package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;


/**
 * Models one read line from a GEDCOM file.  Connects that up with
 * the GedBrowser data model.
 *
 * @author Dick Schoeller
 */
public final class GedLine extends AbstractGedLine {
    /**
     * Creates a new GedLine.
     *
     * @param parent the parent
     */
    protected GedLine(final AbstractGedLine parent) {
        super(parent);
    }

    /**
     * Creates a new GedLine.
     *
     * @param reader the reader
     */
    protected GedLine(final BufferedReader reader) {
        super(reader);
    }

    /**
     * Creates a new GedLine.
     *
     * @param arraySource the array source
     */
    @SuppressWarnings("PMD.UseVarargs")
    public GedLine(final String[] arraySource) {
        super(arraySource);
    }

    /**
     * Executes to string.
     *
     * @return the resulting string
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getLevel(); i++) {
            builder.append(' ');
        }

        if (getXref().isEmpty()) {
            builder.append(getLevel());
            builder.append(' ');
            builder.append(getTag());
            builder.append(' ');
            builder.append(getTail());
        } else {
            builder.append(getLevel());
            builder.append(" @");
            builder.append(getXref());
            builder.append("@ ");
            builder.append(getTag());
            builder.append(' ').append(getTail());
        }

        builder.append(childrenString());

        return builder.toString();
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedLineVisitor visitor) {
        visitor.visit(this);
    }
}
