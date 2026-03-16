package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLine;

/**
 * Contains tests for create ged line.
 *
 * @author Richard Schoeller
 */
class CreateGedLineTest {

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("0 HEAD", 0, "HEAD", "", ""),
                Arguments.of("1 SOUR TMG", 1, "SOUR", "TMG", ""),
                Arguments.of("1 SUBM @SUB1@", 1, "SUBM", "@SUB1@", ""),
                Arguments.of("0 @SUB1@ SUBM", 0, "SUBM", "", "SUB1"),
                Arguments.of("1 NAME Richard Schoeller", 1, "NAME", "Richard Schoeller", ""),
                Arguments.of("1 ADDR 242 Marked Tree Road", 1, "ADDR", "242 Marked Tree Road", "")
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void testCreateGedLineNullString(final String line, final int level,
            final String tag, final String tail, final String xref) {
        final AbstractGedLine gedLine = GedLine.createGedLine(null, line);
        assertEquals(level, gedLine.getLevel(), "Level mismatch");
        assertEquals(tag, gedLine.getTag(), "Tag mismatch");
        assertEquals(tail, gedLine.getTail(), "Tail mismatch");
        assertEquals(xref, gedLine.getXref(), "Xref mismatch");
    }
}
