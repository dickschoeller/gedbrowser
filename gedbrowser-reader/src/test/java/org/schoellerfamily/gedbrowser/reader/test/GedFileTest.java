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
        System.out.println(top); // NOPMD
        System.out.println("--------"); // NOPMD
        final GedObject root = top.createGedObject((AbstractGedLine) null);
        final String out = root.toString();
        System.out.println(out); // NOPMD

        final Person melissa = (Person) root.find("I1");
        final Person dick = melissa.getFather();
        final List<Person> spouses = dick.getSpouses(null, dick);
        assertEquals("Dick only has one spouse", 1, spouses.size());
        System.out.println(spouses.get(0)); // NOPMD
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

    /**
     * Test factory methods with string input.
     */
    @Test
    public final void testFactoryGedLineHeadString() {
        final AbstractGedLine headLine = GedLine.createGedLine(null, "0 HEAD");
        assertEquals(0, headLine.getLevel());
        assertEquals("HEAD", headLine.getTag());
        assertEquals("", headLine.getTail());
        assertEquals("", headLine.getXref());
    }

    /**
     * Test factory methods with string input.
     */
    @Test
    public final void testFactoryGedLineSourString() {
        final AbstractGedLine sourLine = GedLine.createGedLine(null,
                "1 SOUR TMG");
        assertEquals(1, sourLine.getLevel());
        assertEquals("SOUR", sourLine.getTag());
        assertEquals("TMG", sourLine.getTail());
        assertEquals("", sourLine.getXref());
    }

    /**
     * Test factory methods with string input.
     */
    @Test
    public final void testFactoryGedLineOneSubmString() {
        final AbstractGedLine submLinkLine = GedLine.createGedLine(null,
                "1 SUBM @SUB1@");
        assertEquals(1, submLinkLine.getLevel());
        assertEquals("SUBM", submLinkLine.getTag());
        assertEquals("@SUB1@", submLinkLine.getTail());
        assertEquals("", submLinkLine.getXref());
    }

    /**
     * Test factory methods with string input.
     */
    @Test
    public final void testFactoryGedLineZeroSubmString() {
        final AbstractGedLine submLine = GedLine.createGedLine(null,
                "0 @SUB1@ SUBM");
        assertEquals(0, submLine.getLevel());
        assertEquals("SUBM", submLine.getTag());
        assertEquals("", submLine.getTail());
        assertEquals("SUB1", submLine.getXref());
    }

    /**
     * Test factory methods with string input.
     */
    @Test
    public final void testFactoryGedLineNameString() {
        final AbstractGedLine submNameLine = GedLine.createGedLine(null,
                "1 NAME Richard Schoeller");
        assertEquals(1, submNameLine.getLevel());
        assertEquals("NAME", submNameLine.getTag());
        assertEquals("Richard Schoeller", submNameLine.getTail());
        assertEquals("", submNameLine.getXref());
    }

    /**
     * Test factory methods with string input.
     */
    @Test
    public final void testFactoryGedLineAddrString() {
        final AbstractGedLine submAddrLine = GedLine.createGedLine(null,
                "1 ADDR 242 Marked Tree Road");
        assertEquals(1, submAddrLine.getLevel());
        assertEquals("ADDR", submAddrLine.getTag());
        assertEquals("242 Marked Tree Road", submAddrLine.getTail());
        assertEquals("", submAddrLine.getXref());
    }

}
