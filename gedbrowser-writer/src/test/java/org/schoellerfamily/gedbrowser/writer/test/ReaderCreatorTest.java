package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;
import org.schoellerfamily.gedbrowser.writer.GedWriterLineCreator;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class ReaderCreatorTest {
    private static final String FILE_NAME =
            "mini-schoeller.ged";
// Tests can be done with these others.
//            "/var/lib/gedbrowser/schoeller.ged";
//            "gl120368.ged";

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final String message;
    /** */
    private final String expected;
    /** */
    private final String actual;

    /**
     * @param message the message string
     * @param expected the expected line string
     * @param actual the actual line string
     */
    public ReaderCreatorTest(final String expected,
            final String actual) {
        this.message = "Line mismatch";
        this.expected = expected;
        this.actual = actual;
    }

    /**
     * @return create the table of expected and actual
     * @throws IOException because of problems reading the file
     */
    @Parameters
    public static Collection<String[]> data() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        GedObjectCreator g2g = new GedObjectCreator();
        final Root root = g2g.create(top);
        final GedWriterLineCreator gedLineCreator = new GedWriterLineCreator();
        root.accept(gedLineCreator);
        final List<GedWriterLine> lines = gedLineCreator.getLines();
        final List<String[]> parameters = new ArrayList<>();
        final Object[] strings = readFileTestSourceAsStrings().toArray();
        for (int i = 0; i < lines.size(); i++) {
            String expected = expected(strings, i);
            String actual = lines.get(i).getLine();
            String[] array = { expected, actual };
            parameters.add(array);
        }
        return parameters;
    }

    private static String expected(final Object[] strings, int i) {
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
    private static Stream<String> readFileTestSourceAsStrings() throws IOException {
        return TestResourceReader.readFileTestSourceAsStrings(
                "", FILE_NAME);
    }

    /** */
    @Test
    public void testLine() {
        logger.info("Actual line: " + actual);
        assertEquals(message, expected, actual);
    }
}
