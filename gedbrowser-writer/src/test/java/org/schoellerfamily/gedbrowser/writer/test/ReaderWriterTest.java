package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;
import org.schoellerfamily.gedbrowser.writer.creator.GedObjectToGedWriterVisitor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
@SuppressWarnings("null")
public class ReaderWriterTest {
    /**
     * The file name to use in the test.
     */
    private static final String FILE_NAME =
            "mini-schoeller.ged";
// Tests can be done with these others.
//            "/var/lib/gedbrowser/schoeller.ged";
//            "gl120368.ged";);

    /**
     * Build the list of expected/actual pairs.
     *
     * @return list of pairs
     * @throws IOException if resource can't be read
     */
    public static List<String[]> data() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final GedLineToGedObjectTransformer g2g =
                new GedLineToGedObjectTransformer();
        final Root root = g2g.create(top);
        final GedObjectToGedWriterVisitor gedLineVisitor =
                new GedObjectToGedWriterVisitor();
        root.accept(gedLineVisitor);
        final List<GedWriterLine> lines = gedLineVisitor.getLines();
        final List<String[]> parameters = new ArrayList<>();
        final Object[] strings = readFileTestSourceAsStrings().toArray();
        for (int i = 0; i < lines.size(); i++) {
            final String expected = expected(strings, i);
            final String actual = lines.get(i).getLine();
            final String[] array = {expected, actual};
            parameters.add(array);
        }
        return parameters;
    }

    /**
     * @param strings the string array
     * @param i index into the string array
     * @return the value at the index or a dummy if overflowed
     */
    private static String expected(final Object[] strings, final int i) {
        if (i == 0) {
            return "";
        }
        if (i > strings.length) {
            return "too many strings";
        }
        return (String) strings[i - 1];
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private static AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(
                "", FILE_NAME);
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a stream of strings
     * @throws IOException because reader might throw.
     */
    private static Stream<String> readFileTestSourceAsStrings()
            throws IOException {
        return TestResourceReader.readFileTestSourceAsStrings(
                "", FILE_NAME);
    }

    /**
     * Iterate all expected/actual pairs and assert equality.
     *
     * @throws Exception on IO/read errors
     */
    @Test
    void testLines() throws Exception {
        final List<String[]> parameters = data();
        for (final String[] param : parameters) {
            final String expected = param[0];
            final String actual = param[1];
            log.info("Actual line: {}", actual);
            assertEquals(expected, actual, "Line mismatch");
        }
    }
}
