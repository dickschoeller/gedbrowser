package org.schoellerfamily.gedbrowser.writer.util;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public final class Backup {

    private Backup() {
    }

    /**
     * Save the existing version of the file by renaming it to something ending with
     * .&lt;number&gt;.
     *
     * @param filename the full path to create
     * @throws IOException if the rename fails
     */
    public static void backup(final String filename) throws IOException {
        final File dest = createFile(filename);
        if (dest.exists()) {
            final File backupFile = generateBackupFilename(filename);
            log.debug("backing up file from {} to {}", filename, backupFile.getName());
            if (!dest.renameTo(backupFile)) {
                throw new IOException("Could not rename file from %s to %s"
                    .formatted(dest.getName(), backupFile.getName()));
            }
        }
    }

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

    private static File createFile(final String filename) {
        return new File(filename);
    }
}
