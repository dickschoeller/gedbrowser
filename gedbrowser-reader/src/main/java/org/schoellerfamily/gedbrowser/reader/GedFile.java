package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;

/**
 * The file level object for parsing GEDCOM files into GedBrowser form.
 *
 * @author Dick Schoeller
 */
public final class GedFile extends AbstractGedLine {
    /** */
    private final transient String filename;

    /** */
    private final transient String dbName;

    /** */
    private final transient FinderStrategy finder;

    /**
     * @param filename the name of the file
     * @param dbName the name of the database
     * @param finder the finder to use in the root object
     * @param reader The buffered reader to use to read the file
     * @throws IOException if there are problems reading the file
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
     * @return the file name
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the name of the DB for queries
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @return the finder implementation
     */
    public FinderStrategy getFinder() {
        return finder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return childrenString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedLineVisitor visitor) {
        visitor.visit(this);
    }
}
