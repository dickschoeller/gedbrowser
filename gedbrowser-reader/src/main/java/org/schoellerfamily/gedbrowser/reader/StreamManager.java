package org.schoellerfamily.gedbrowser.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Can open a stream either in an absolute file location or in the classpath.
 *
 * @author Dick Schoeller
 */
public class StreamManager {

    /**
     * Location inside JARs where GEDCOMs might be found.
     */
    private static final String DATA_DIR =
            "/org/schoellerfamily/gedbrowser/reader/data/";

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
     */
    public InputStream getInputStream() throws FileNotFoundException {
        // Treat obvious filesystem paths (absolute/relative paths, files
        // under src/ or target/, or Windows drive letters) as files. All
        // other short names are treated as classpath resources under
        // DATA_DIR.
        if (filename == null) {
            return null;
        }
        final boolean looksLikeFile = filename.charAt(0) == '/' ||
                filename.charAt(0) == '.' || filename.contains(":") ||
                filename.contains("src/") || filename.contains("target/");
        if (looksLikeFile) {
            return new FileInputStream(filename);
        }
        final InputStream is = getClass().getResourceAsStream(DATA_DIR + filename);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: %s%s".formatted(DATA_DIR, filename));
        }
        return is;
    }
}
