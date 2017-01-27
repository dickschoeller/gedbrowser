package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
public class GedFileTest {
    /**
     * Test GedLine with an array input.
     *
     * @throws IOException never.
     */
    @Test
    public final void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        System.out.println(top);
        System.out.println("--------");
        final GedObject root = top.createGedObject((AbstractGedLine) null);
        final String out = root.toString();
        System.out.println(out);

        final Person melissa = (Person) root.find("I1");
        final Person dick = melissa.getFather();
        final List<Person> spouses = dick.getSpouses(null, dick);
        assertEquals("Dick only has one spouse", 1, spouses.size());
        System.out.println(spouses.get(0));
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private AbstractGedLine readFileTestSource() throws IOException {
        return ReaderHelper.readFileTestSource(this, "mini-schoeller.ged");
    }

    /** */
    @Test
    public final void testFactoryGedLineHeadString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "0 HEAD");
        assertMatch(line, 0, "HEAD", "", "");
    }

    /** */
    @Test
    public final void testFactoryGedLineSourString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 SOUR TMG");
        assertMatch(line, 1, "SOUR", "TMG", "");
    }

    /** */
    @Test
    public final void testFactoryGedLineOneSubmString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 SUBM @SUB1@");
        assertMatch(line, 1, "SUBM", "@SUB1@", "");
    }

    /** */
    @Test
    public final void testFactoryGedLineZeroSubmString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "0 @SUB1@ SUBM");
        assertMatch(line, 0, "SUBM", "", "SUB1");
    }

    /** */
    @Test
    public final void testFactoryGedLineNameString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 NAME Richard Schoeller");
        assertMatch(line, 1, "NAME", "Richard Schoeller", "");
    }

    /** */
    @Test
    public final void testFactoryGedLineAddrString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 ADDR 242 Marked Tree Road");
        assertMatch(line, 1, "ADDR", "242 Marked Tree Road", "");
    }

    /**
     * @param line the line that is being checked
     * @param expectedLevel the expected level value
     * @param expectedTag the expected tag string
     * @param expectedTail the expected tail string
     * @param expectedXref the expected reference string
     */
    public void assertMatch(final AbstractGedLine line,
            final int expectedLevel, final String expectedTag,
            final String expectedTail, final String expectedXref) {
        assertEquals("Level mismatch", expectedLevel, line.getLevel());
        assertEquals("Tag mismatch", expectedTag, line.getTag());
        assertEquals("Tail mismatch", expectedTail, line.getTail());
        assertEquals("Xref mismatch", expectedXref, line.getXref());
    }
}
