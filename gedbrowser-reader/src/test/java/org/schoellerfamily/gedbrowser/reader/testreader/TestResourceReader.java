package org.schoellerfamily.gedbrowser.reader.testreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedFile;

/**
 * This class provides a method to simplify reading test data files.
 *
 * @author Dick Schoeller
 */
public final class TestResourceReader {
    /**
     * Directory containing test data files.
     */
    private static final String DATA_DIR =
            "/org/schoellerfamily/gedbrowser/reader/data/";

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
        String shortername;
        InputStream fis;
        if (filename.charAt(0) == '/') {
            shortname = filename.substring(filename.lastIndexOf("/") + 1);
            shortername = shortname.substring(0, shortname.indexOf("."));
            fis = new FileInputStream(filename);
        } else {
            shortname = filename;
            shortername = shortname.substring(0, shortname.indexOf("."));
            fis = caller.getClass().getResourceAsStream(DATA_DIR + filename);
        }
        final Reader reader = new InputStreamReader(fis, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(reader);
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
        InputStream fis;
        if (filename.charAt(0) == '/') {
            fis = new FileInputStream(filename);
        } else {
            fis = caller.getClass().getResourceAsStream(DATA_DIR + filename);
        }
        final Reader reader = new InputStreamReader(fis, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.lines();
    }
}
