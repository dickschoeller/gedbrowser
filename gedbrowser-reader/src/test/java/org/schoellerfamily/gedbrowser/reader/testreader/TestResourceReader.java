package org.schoellerfamily.gedbrowser.reader.testreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.reader.GedFile;
import org.schoellerfamily.gedbrowser.reader.StreamManager;

/**
 * This class provides a method to simplify reading test data files.
 *
 * @author Dick Schoeller
 */
public final class TestResourceReader {
    /**
     * Constructor.
     */
    private TestResourceReader() {
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @param caller the object doing the calling allows us to get at resources
     * @param filename the name of the file
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    public static AbstractGedLine readFileTestSource(final Object caller,
            final String filename) throws IOException {
        String shortname;
        if (filename.charAt(0) == '/') {
            shortname = filename.substring(filename.lastIndexOf("/") + 1);
        } else {
            shortname = filename;
        }
        final String shortername = shortname.substring(0,
                shortname.indexOf("."));
        final BufferedReader bufferedReader = openBufferedReader(filename);
        final GedFile gedFile = new GedFile(shortname, shortername, null,
                bufferedReader);
        gedFile.readToNext();
        return gedFile;
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @param caller the object doing the calling allows us to get at resources
     * @param filename the name of the file
     * @return the strings of the file
     * @throws IOException because reader might throw.
     */
    public static Stream<String> readFileTestSourceAsStrings(
            final Object caller, final String filename) throws IOException {
        return openBufferedReader(filename).lines();
    }

    /**
     * @param filename the input filename
     * @return the buffered reader
     * @throws IOException if the file isn't found or bad charset
     */
    private static BufferedReader openBufferedReader(final String filename)
            throws IOException {
        final InputStream fis = new StreamManager(filename).getInputStream();
        final String charset = new CharsetScanner().charset(filename);
        final Reader reader = new InputStreamReader(
                fis, charset);
        return new BufferedReader(reader);
    }
}
