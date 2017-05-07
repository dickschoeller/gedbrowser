package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base of GedLine and GedFile.
 *
 * @author Dick Schoeller
 */
public abstract class AbstractGedLine extends AbstractSingleGedLine {
    /** */
    private final transient GedLineSource source;

    /** */
    private final transient List<AbstractGedLine> children =
            new ArrayList<AbstractGedLine>();

    /**
     * @param parent The parent GedLine. Parentage is determined by the GEDCOM
     *               level number.
     */
    protected AbstractGedLine(final AbstractGedLine parent) {
        super(generateLineNumber(parent));
        if (parent == null) {
            this.source = new NullSource();
        } else {
            this.source = parent.source;
        }
        this.setLevel(-1);
    }

    /**
     * @param parent the parent
     * @return the appropriate line number
     */
    private static int generateLineNumber(final AbstractGedLine parent) {
        if (parent == null) {
            return 0;
        }
        return parent.source.getMaxLineNumber();
    }

    /**
     * @param reader The buffered reader that we are getting data from.
     */
    protected AbstractGedLine(final BufferedReader reader) {
        super(0);
        this.source = new ReaderSource(reader);
        this.setLevel(-1);
    }

    /**
     * @param arraySource Array of strings containing lines of GEDCOM.
     */
    @SuppressWarnings({ "PMD.UseVarargs", "PMD.ArrayIsStoredDirectly" })
    protected AbstractGedLine(final String[] arraySource) {
        super(0);
        this.source = new ArraySource(arraySource);
        this.setLevel(-1);
        this.setTag("ROOT");
    }

    /**
     * Create a GedLine from the provided string.
     *
     * @param parent the current parent.
     * @param inLine the line of GEDCOM data
     * @return the GedLine
     */
    public static AbstractGedLine createGedLine(
            final AbstractGedLine parent, final String inLine) {
        GedLineSource sour;
        if (parent == null) {
            sour = new NullSource();
        } else {
            sour = parent.source;
        }
        return sour.createGedLine(parent, inLine);
    }

    /**
     * Read the source data until the a line is encountered at the same level as
     * this one. That allows the reading of children.
     *
     * @return The GedLine at the current level. Null if the end of data is
     *         reached first.
     * @throws IOException If this is a file source and we encounter a problem.
     */
    public final AbstractGedLine readToNext() throws IOException {
        AbstractGedLine gedLine = source.createGedLine(this);
        while (true) {
            if (gedLine == null || gedLine.getLevel() == -1) {
                break;
            }

            if (gedLine.getLevel() <= this.getLevel()) {
                return gedLine;
            }

            children.add(gedLine);

            gedLine = gedLine.readToNext();
        }

        return null;
    }

    /**
     * @return the combine toStrings of the children.
     */
    protected final String childrenString() {
        final StringBuilder builder = new StringBuilder();
        for (final AbstractGedLine child : children) {
            builder.append('\n');
            builder.append(child.toString());
        }
        return builder.toString();
    }

    /**
     * Get the list of children.
     *
     * @return the children.
     */
    public final List<AbstractGedLine> getChildren() {
        return children;
    }

    /**
     * @param visitor the visiting object
     */
    public abstract void accept(GedLineVisitor visitor);
}
