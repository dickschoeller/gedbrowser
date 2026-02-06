package org.schoellerfamily.gedbrowser.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

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
        final boolean looksLikeFile = (!filename.isEmpty() && filename.charAt(0) == '/')
            || (!filename.isEmpty() && filename.charAt(0) == '.')
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
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        // Check for null bytes and other dangerous characters
        if (filePath.contains("\0")) {
            throw new IllegalArgumentException(
                "File path contains null bytes: " + filePath);
        }
        // Check for ".." as a path component (not just substring)
        checkForPathTraversal(filePath);
    }

    /**
     * Validates that a resource path does not contain path traversal sequences.
     *
     * @param resourcePath the resource path to validate
     * @throws IllegalArgumentException if resourcePath contains path traversal sequences
     */
    private void validateResourcePath(final String resourcePath) {
        if (StringUtils.isEmpty(resourcePath)) {
            throw new IllegalArgumentException("Resource path cannot be null or empty");
        }
        // Check for null bytes and absolute paths
        if (resourcePath.contains("\0") || resourcePath.startsWith("/")) {
            throw new IllegalArgumentException(
                "Resource path contains invalid characters or traversal sequences: "
                    + resourcePath);
        }
        // Check for ".." as a path component (not just substring)
        checkForPathTraversal(resourcePath);
    }

    /**
     * Checks if a path contains ".." as a path component, which indicates a path traversal attempt.
     *
     * @param pathString the path string to check
     * @throws IllegalArgumentException if the path contains ".." as a path component
     */
    private void checkForPathTraversal(final String pathString) {
        final Path path = Paths.get(pathString);
        for (final Path part : path) {
            if ("..".equals(part.toString())) {
                throw new IllegalArgumentException(
                    "Path contains traversal sequences: " + pathString);
            }
        }
    }
}
