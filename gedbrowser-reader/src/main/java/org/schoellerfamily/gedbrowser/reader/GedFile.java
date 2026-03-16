package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;

/**
 * Represents ged file.
 *
 * @author Richard Schoeller
 */
public final class GedFile extends AbstractGedLine {
    /** */
    private final transient String filename;

    /** */
    private final transient String dbName;

    /** */
    private final transient FinderStrategy finder;

    /**
     * Creates a new GedFile.
     *
     * @param filename the filename to use
     * @param dbName the db name to use
     * @param finder the finder
     * @param reader the reader
     */
    public GedFile(final String filename, final String dbName,
            final FinderStrategy finder, final BufferedReader reader)
            throws IOException {
        super(reader);
        this.filename = filename;
        this.dbName = dbName;
        this.finder = finder;
        readToNext();
    }

    /**
     * Gets the filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Gets the db name.
     *
     * @return the db name
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Gets the finder.
     *
     * @return the finder
     */
    public FinderStrategy getFinder() {
        return finder;
    }

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    @Override
    public String toString() {
        return childrenString();
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
