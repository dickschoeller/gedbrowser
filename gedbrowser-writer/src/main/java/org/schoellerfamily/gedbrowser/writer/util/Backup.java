package org.schoellerfamily.gedbrowser.writer.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Dick Schoeller
 */
public final class Backup {
    /** Logger. */
    private static final transient Log LOGGER =
            LogFactory.getLog(Backup.class);

    /**
     * Private constructor.
     */
    private Backup() {
    }

    /**
     * Save the existing version of the file by renaming it to something ending
     * with .&lt;number&gt;.
     *
     * @param filename the full path to create
     * @throws IOException if the rename fails
     */
    public static void backup(final String filename) throws IOException {
        final File dest = createFile(filename);
        if (dest.exists()) {
            final File backupFile = generateBackupFilename(filename);
            LOGGER.debug("backing up file from " + filename + " to "
                    + backupFile.getName());
            if (!dest.renameTo(backupFile)) {
                throw new IOException("Could not rename file from "
                        + dest.getName() + " to " + backupFile.getName());
            }
        }
    }

    /**
     * @param filename the name of the file being backedup
     * @return the filename.n that doesn't exist
     */
    private static File generateBackupFilename(final String filename) {
        int i = 1;
        while (true) {
            final File backupFile = createFile(filename + "." + i);
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
    private static File createFile(final String filename) {
        return new File(filename);
    }
}
