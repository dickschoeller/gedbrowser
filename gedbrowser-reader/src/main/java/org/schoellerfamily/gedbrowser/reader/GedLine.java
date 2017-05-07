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
     * @param parent The parent GedLine. Parentage is determined by the GEDCOM
     *               level number.
     */
    protected GedLine(final AbstractGedLine parent) {
        super(parent);
    }

    /**
     * @param reader The buffered reader that we are getting data from.
     */
    protected GedLine(final BufferedReader reader) {
        super(reader);
    }

    /**
     * @param arraySource Array of strings containing lines of GEDCOM.
     */
    @SuppressWarnings("PMD.UseVarargs")
    public GedLine(final String[] arraySource) {
        super(arraySource);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedLineVisitor visitor) {
        visitor.visit(this);
    }
}
