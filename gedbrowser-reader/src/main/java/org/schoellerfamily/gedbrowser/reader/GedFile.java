package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;

import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * The file level object for parsing GEDCOM files into GedBrowser form.
 *
 * @author dick
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

    @Override
    protected GedObject createGedObject(final GedObject parent) {
        Root gob;
        if (finder == null) {
            gob = new Root(null);
        } else {
            gob = new Root(null, finder);
        }
        gob.setFilename(filename);
        gob.setDbName(dbName);
        return gob;
    }

    @Override
    public String toString() {
        return childrenString();
    }
}
