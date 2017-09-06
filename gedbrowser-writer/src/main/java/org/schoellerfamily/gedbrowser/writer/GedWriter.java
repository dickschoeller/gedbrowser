package org.schoellerfamily.gedbrowser.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Richard Schoeller
 */
public class GedWriter {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final GedWriterLineCreator creator = new GedWriterLineCreator();

    /** */
    private final Root root;

    /**
     * @param root the root of the data set to write.
     */
    public GedWriter(final Root root) {
        this.root = root;
    }

    /**
     * Write the file as directed.
     */
    public void write() {
        root.accept(creator);
        backup();
        final String filename = root.getFilename();
        try (FileOutputStream fstream = new FileOutputStream(filename);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            for (final GedWriterLine line : creator.getLines()) {
                final String string = line.getLine();
                bstream.write(string.getBytes());
            }
        } catch (IOException e) {
            logger.error("Problem writing GEDCOM file", e);
        }
    }

    /**
     * Save the existing version of the file.
     */
    private void backup() {
        final File dest = createFile(root.getFilename());
        if (dest.exists()) {
            final File backupFile = generateBackupFilename();
            dest.renameTo(backupFile);
        }
    }

    /**
     * @return the filename.n that doesn't exist
     */
    private File generateBackupFilename() {
        int i = 1;
        while (true) {
            final File backupFile = createFile(root.getFinder() + "." + i);
            if (!backupFile.exists()) {
                return backupFile;
            }
            i++;
        }
    }

    /**
     * @param filename the name of the file to create
     * @return the file object
     */
    public File createFile(final String filename) {
        return new File(filename);
    }
}
