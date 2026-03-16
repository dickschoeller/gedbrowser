package org.schoellerfamily.gedbrowser.writer;

import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public class GedWriterFile extends GedWriterLine {
    /** */
    private final String filename;
    /** */
    private final String dbName;

    /**
     * Creates a new GedWriterFile.
     *
     * @param root the root
     */
    public GedWriterFile(final Root root) {
        super(-1, root, "");
        this.filename = root.getFilename();
        this.dbName = root.getDbName();
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
}
