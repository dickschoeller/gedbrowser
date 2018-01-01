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
        if (filename.charAt(0) == '/') {
            return new FileInputStream(filename);
        } else {
            return getClass().getResourceAsStream(DATA_DIR + filename);
        }
    }
}
