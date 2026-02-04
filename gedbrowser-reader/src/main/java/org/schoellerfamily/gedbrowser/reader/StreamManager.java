package org.schoellerfamily.gedbrowser.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Can open a stream either in an absolute file location or in the classpath.
 *
 * @author Dick Schoeller
 */
public class StreamManager {

    /**
     * Location inside JARs where GEDCOMs might be found.
     */
    private static final String DATA_DIR = "/org/schoellerfamily/gedbrowser/reader/data/";

    /**
     * Holds the name of the file that we are opening.
     */
    private final String filename;

    /**
     * Constructor.
     *
     * @param filename the name of the file that we are opening
     */
    public StreamManager(final String filename) {
        this.filename = filename;
    }

    /**
     * @return the input stream
     * @throws FileNotFoundException if the file can't be opened
     * @throws IllegalArgumentException if the filename contains path traversal sequences
     */
    public InputStream getInputStream() throws FileNotFoundException {
        // Treat obvious filesystem paths (absolute/relative paths, files
        // under src/ or target/, or Windows drive letters) as files. All
        // other short names are treated as classpath resources under
        // DATA_DIR.
        if (filename == null) {
            return null;
        }
        if (filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }
        final boolean looksLikeFile = (filename.length() > 0 && filename.charAt(0) == '/')
            || (filename.length() > 0 && filename.charAt(0) == '.')
            || filename.contains(":") || filename.contains("src/") || filename.contains("target/");
        if (looksLikeFile) {
            validateFilePath(filename);
            return new FileInputStream(filename);
        }
        validateResourcePath(filename);
        final InputStream is = getClass().getResourceAsStream(DATA_DIR + filename);
        if (is == null) {
            throw new FileNotFoundException(
                "Resource not found: %s%s".formatted(DATA_DIR, filename));
        }
        return is;
    }

    /**
     * Validates that a file path does not contain path traversal sequences.
     *
     * @param filePath the file path to validate
     * @throws IllegalArgumentException if filePath contains path traversal sequences
     */
    private void validateFilePath(final String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        // Check for null bytes and other dangerous characters
        if (filePath.contains("\0")) {
            throw new IllegalArgumentException(
                "File path contains null bytes: " + filePath);
        }
        // Check for path traversal sequences
        if (filePath.contains("..")) {
            throw new IllegalArgumentException(
                "File path contains traversal sequences: " + filePath);
        }
        // Normalize the path to detect bypassed traversal attempts
        final Path normalizedPath = Paths.get(filePath).normalize();
        final Path filePath2 = Paths.get(filePath);
        // Check if normalization changed the path (indicates traversal)
        if (!normalizedPath.toString().equals(filePath2.toString())) {
            throw new IllegalArgumentException(
                "File path contains invalid characters: " + filePath);
        }
    }

    /**
     * Validates that a resource path does not contain path traversal sequences.
     *
     * @param resourcePath the resource path to validate
     * @throws IllegalArgumentException if resourcePath contains path traversal sequences
     */
    private void validateResourcePath(final String resourcePath) {
        if (resourcePath == null || resourcePath.isEmpty()) {
            throw new IllegalArgumentException("Resource path cannot be null or empty");
        }
        if (resourcePath.contains("..") || resourcePath.contains("\0")
            || resourcePath.startsWith("/")) {
            throw new IllegalArgumentException(
                "Resource path contains invalid characters or traversal sequences: "
                    + resourcePath);
        }
    }
}
