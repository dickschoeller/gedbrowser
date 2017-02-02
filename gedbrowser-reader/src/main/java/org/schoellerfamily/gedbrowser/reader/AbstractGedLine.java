package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.reader.AbstractGedObjectFactory.GedObjectFactory;

/**
 * Base of GedLine and GedFile.
 *
 * @author Dick Schoeller
 */
public abstract class AbstractGedLine {
    /** */
    private static final GedObjectFactory GOB_FACTORY = new GedObjectFactory();
    /** */
    private transient AbstractGedLine parent;
    /** */
    private transient BufferedReader reader;
    /** */
    private transient String[] arraySource;
    /** */
    private final transient List<AbstractGedLine> children =
            new ArrayList<AbstractGedLine>();
    /** */
    private final transient int lineNumber;
    /** */
    private transient int level;
    /** */
    private transient int maxLineNumber;
    /** */
    private transient String xref = "";
    /** */
    private transient String tag = "";
    /** */
    private transient String tail = "";
    /** */
    private transient GedObject gedObject;

    /**
     * @param parent The parent GedLine. Parentage is determined by the GEDCOM
     *               level number.
     */
    protected AbstractGedLine(final AbstractGedLine parent) {
        this.parent = parent;
        if (parent == null) {
            this.reader = null;
            this.arraySource = null;
            this.lineNumber = 0;
        } else {
            this.reader = parent.reader;
            this.arraySource = parent.arraySource;
            this.lineNumber = parent.getMaxLineNumber();
        }
        this.maxLineNumber = 0;
        this.level = -1;
        this.gedObject = null;
    }

    /**
     * @param reader The buffered reader that we are getting data from.
     */
    protected AbstractGedLine(final BufferedReader reader) {
        this.parent = null;
        this.reader = reader;
        this.arraySource = null;
        this.lineNumber = 0;
        this.maxLineNumber = 0;
        this.level = -1;
        this.gedObject = null;
    }

    /**
     * @param arraySource Array of strings containing lines of GEDCOM.
     */
    @SuppressWarnings("PMD.UseVarargs")
    protected AbstractGedLine(final String[] arraySource) {
        this.parent = null;
        this.reader = null;
        this.arraySource = arraySource.clone();
        this.lineNumber = 0;
        this.level = -1;
        this.maxLineNumber = 0;
        this.tag = "ROOT";
        this.gedObject = null;
    }

    /**
     * Create the next GedLine.
     *
     * @param parent the current parent.
     * @return the GedLine.
     * @throws IOException if this is a file source and there are problems.
     */
    protected static AbstractGedLine createGedLine(final AbstractGedLine parent)
            throws IOException {
        if (parent.reader != null) {
            final String line = parent.reader.readLine();
            return createGedLine(parent, line);
        }
        if (parent.arraySource != null) {
            final int lineNumber = parent.nextLineNumber();
            if (lineNumber >= parent.arraySource.length) {
                return null;
            }
            final String line = parent.arraySource[lineNumber];
            return createGedLine(parent, line);
        }
        return null;
    }

    /**
     * @return the current GEDCOM level.
     */
    public final int getLevel() {
        return level;
    }

    /**
     * @return the cross reference string for this line.
     */
    public final String getXref() {
        return xref;
    }

    /**
     * @return the GEDCOM tag on this line.
     */
    public final String getTag() {
        return tag;
    }

    /**
     * @return whatever text follows the tag.
     */
    public final String getTail() {
        return tail;
    }

    /**
     * Sets the GedObject associated with this line.
     *
     * @param gedObject the GedObject
     */
    public final void setGedObject(final GedObject gedObject) {
        this.gedObject = gedObject;
    }

    /**
     * Gets the GedObject associated with this line.
     *
     * @return the GedObject
     */
    public final GedObject getGedObject() {
        return gedObject;
    }

    /**
     * @return the highest line number known in the file.
     */
    protected final int getMaxLineNumber() {
        if (parent != null) {
            return parent.getMaxLineNumber();
        }
        return maxLineNumber;
    }

    /**
     * @return the next line number in the file.
     */
    private int nextLineNumber() {
        if (parent != null) {
            return parent.nextLineNumber();
        }
        return maxLineNumber++;
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
        String line = inLine;
        if (line == null) {
            return null;
        }

        line = line.trim();

        if (line.isEmpty()) {
            return null;
        }

        final AbstractGedLine gedLine = new GedLine(parent);

        final String[] levelAndRest = line.split("[^0-9]", 2);
        gedLine.level = Integer.parseInt(levelAndRest[0]);

        line = levelAndRest[1].trim();

        if (gedLine.level == 0) {
            int bpos = line.indexOf('@');
            if (bpos == -1) {
                gedLine.xref = "";
            } else {
                bpos++;
                final int epos = line.indexOf('@', bpos);
                gedLine.xref = line.substring(bpos, epos);
                line = line.substring(epos + 1);
            }

            line = line.trim();
            final String[] parts = line.split("[ \t]", 2);
            gedLine.tag = parts[0];
            gedLine.tail = "";
        } else {
            gedLine.xref = "";

            line = line.trim();

            final String[] parts = line.split("[ \t]", 2);
            gedLine.tag = parts[0];
            if (parts.length == 1) {
                line = "";
            } else {
                line = parts[1].trim();
            }

            gedLine.tail = line;
        }
        return gedLine;
    }

    /**
     * Create the GedObject for this line.
     *
     * @param parentLine the parent GedLine.
     * @return the GedObject
     */
    public final GedObject createGedObject(final AbstractGedLine parentLine) {
        GedObject parentGob;
        if (parentLine == null) {
            parentGob = null;
        } else {
            parentGob = parentLine.gedObject;
        }

        final GedObject gob = createGedObject(parentGob);
        if (gob == null) {
            return null;
        }

        setGedObject(gob);

        createChildren(gob);

        return gob;
    }

    /**
     * Create a GedObject for this line.
     *
     * @param parentGedObj the parent GedObject
     * @return the GedObject
     */
    protected abstract GedObject createGedObject(GedObject parentGedObj);

    /**
     * Create the GedObjects for the child lines.
     *
     * @param subObject the GedObject whose children are to be created.
     */
    protected final void createChildren(final GedObject subObject) {
        for (final AbstractGedLine child : getChildren()) {
            final GedObject childGob = child.createGedObject(this);
            subObject.insert(childGob);
        }
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
        AbstractGedLine gedLine = createGedLine(this);
        while (true) {
            if (gedLine == null || gedLine.level == -1) {
                break;
            }

            if (gedLine.level <= this.level) {
                return gedLine;
            }

            children.add(gedLine);

            gedLine = gedLine.readToNext();
            if (gedLine != null) {
                gedLine.parent = this;
            }
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
     * Get the line number.
     *
     * @return the line number.
     */
    public final int getLineNumber() {
        return lineNumber;
    }

    /**
     * @return the GedObjectFactory
     */
    public static GedObjectFactory getGobFactory() {
        return GOB_FACTORY;
    }
}
