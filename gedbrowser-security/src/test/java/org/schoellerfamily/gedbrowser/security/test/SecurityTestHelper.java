package org.schoellerfamily.gedbrowser.security.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dick
 */
@Slf4j
public final class SecurityTestHelper {

    /**
     * Private constructor.
     */
    private SecurityTestHelper() {
        // Empty
    }
    /**
     * @param userFile the path of the file to reset
     */
    public static void resetUserFile(final String userFile) {
        log.info("resetting {}", userFile);
        final String[] strings = {
                "schoeller@comcast.net,Richard,Schoeller,"
                + "schoeller@comcast.net,HAHANOWAY,USER,ADMIN\n",
                "guest,,,,guest,USER\n" };
        try (FileOutputStream fstream = new FileOutputStream(userFile);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            for (final String string : strings) {
                bstream.write(string.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            log.error("Problem writing user file", e);
        }

        // Get the filename
        final String fileNameStr = FilenameUtils.getName(userFile);
        final String basePathStr = FilenameUtils.getFullPath(userFile);

        deleteNumberedFiles(basePathStr, fileNameStr);
    }

    /**
     * Delete numbered files matching the pattern baseFileName.NNN where NNN is a number.
     *
     * @param directoryPath the directory to search
     * @param baseFileName the base file name
     */
    public static void deleteNumberedFiles(final String directoryPath, final String baseFileName) {
        final String globPattern = baseFileName + ".[0-9]*";
        final Path dir = Paths.get(directoryPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, globPattern)) {
            for (Path file : stream) {
                try {
                    Files.delete(file);
                    log.info("Deleted file: " + file.getFileName());
                } catch (IOException e) {
                    log.error("Failed to delete " + file.getFileName() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error("Error accessing directory: " + e.getMessage());
        }
    }
}
