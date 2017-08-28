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
     * @param root the root object of this structure
     */
    public GedWriterFile(Root root) {
        super(-1, root, "");
        this.filename = root.getFilename();
        this.dbName = root.getDbName();
    }

    /**
     * @return the filename for this file
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the DB name for this file
     */
    public String getDbName() {
        return dbName;
    }
}
