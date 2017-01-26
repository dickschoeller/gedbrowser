package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLine;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class CreateGedLineTest {
    /** */
    private final String line;
    /** */
    private final int level;
    /** */
    private final String tag;
    /** */
    private final String tail;
    /** */
    private final String xref;

    /**
     * Constructor.
     * @param line the line to parse
     * @param level the expected level
     * @param tag the expected tag
     * @param tail the expected tail
     * @param xref the expected xref
     */
    public CreateGedLineTest(final String line, final int level,
            final String tag, final String tail, final String xref) {
        this.line = line;
        this.level = level;
        this.tag = tag;
        this.tail = tail;
        this.xref = xref;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][] {
            {"0 HEAD", 0, "HEAD", "", ""},
            {"1 SOUR TMG", 1, "SOUR", "TMG", ""},
            {"1 SUBM @SUB1@", 1, "SUBM", "@SUB1@", ""},
            {"0 @SUB1@ SUBM", 0, "SUBM", "", "SUB1"},
            {"1 NAME Richard Schoeller", 1, "NAME", "Richard Schoeller", ""},
            {"1 ADDR 242 Marked Tree Road", 1, "ADDR", "242 Marked Tree Road",
                ""},
        });
    }

    /** */
    @Test
    public void testCreateGedLineNullString() {
        final AbstractGedLine gedLine = GedLine.createGedLine(null, line);
        assertMatch(gedLine);
    }

    /**
     * @param gedLine the line to check
     */
    private void assertMatch(final AbstractGedLine gedLine) {
        assertEquals("Level mismatch", level, gedLine.getLevel());
        assertEquals("Tag mismatch", tag, gedLine.getTag());
        assertEquals("Tail mismatch", tail, gedLine.getTail());
        assertEquals("Xref mismatch", xref, gedLine.getXref());
    }
}
