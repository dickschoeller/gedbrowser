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
    private final transient GedLineSource source;

    /** */
    private final transient List<AbstractGedLine> children =
            new ArrayList<AbstractGedLine>();
    /** */
    private final transient int lineNumber;
    /** */
    private transient int level;
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
        if (parent == null) {
            this.source = new NullSource();
            this.lineNumber = 0;
        } else {
            this.source = parent.source;
            this.lineNumber = parent.source.getMaxLineNumber();
        }
        this.setLevel(-1);
        this.gedObject = null;
    }

    /**
     * @param reader The buffered reader that we are getting data from.
     */
    protected AbstractGedLine(final BufferedReader reader) {
        this.source = new ReaderSource(reader);
        this.lineNumber = 0;
        this.setLevel(-1);
        this.gedObject = null;
    }

    /**
     * @param arraySource Array of strings containing lines of GEDCOM.
     */
    @SuppressWarnings({ "PMD.UseVarargs", "PMD.ArrayIsStoredDirectly" })
    protected AbstractGedLine(final String[] arraySource) {
        this.source = new ArraySource(arraySource);
        this.lineNumber = 0;
        this.setLevel(-1);
        this.setTag("ROOT");
        this.gedObject = null;
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
     * @return the current GEDCOM level
     */
    public final int getLevel() {
        return level;
    }

    /**
     * @param level the GEDCOM level for this line
     */
    public final void setLevel(final int level) {
        this.level = level;
    }

    /**
     * @return the cross reference string for this line
     */
    public final String getXref() {
        return xref;
    }

    /**
     * @param xref the cross reference string for this line
     */
    public final void setXref(final String xref) {
        this.xref = xref;
    }

    /**
     * @return the GEDCOM tag on this line
     */
    public final String getTag() {
        return tag;
    }

    /**
     * @param tag the GEDCOM tag on this line
     */
    public final void setTag(final String tag) {
        this.tag = tag;
    }

    /**
     * @return whatever text follows the tag
     */
    public final String getTail() {
        return tail;
    }

    /**
     * @param tail the text following the tag
     */
    public final void setTail(final String tail) {
        this.tail = tail;
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
