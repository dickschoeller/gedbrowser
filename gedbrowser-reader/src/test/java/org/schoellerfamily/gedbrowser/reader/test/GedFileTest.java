package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class GedFileTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    /**
     * Test GedLine with an array input.
     *
     * @throws IOException never.
     */
    @Test
    public void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        logger.info(top.toString());
        final Root root = g2g.create(top);
        final String out = root.toString();
        logger.info(out);

        final Person melissa = (Person) root.find("I1");
        final PersonNavigator meliNavigator = new PersonNavigator(melissa);
        final Person dick = meliNavigator.getFather();
        final PersonNavigator dickNavigator = new PersonNavigator(dick);
        final List<Person> spouses = dickNavigator.getSpouses();
        assertEquals("Dick only has one spouse", 1, spouses.size());
        logger.info(spouses.get(0));
    }

    /**
     * Test GedLine with an array input.
     *
     * @throws IOException never.
     */
    @Test
    public void testFactoryGedFileHead() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final Root root = g2g.create(top);
        final Head head = (Head) root.find("Header");
        final List<String> headsCheckResults = checkHeads(head);
        assertEquals(
                "Problems in head:\n" + formatResult(headsCheckResults),
                0, headsCheckResults.size());
    }

    /**
     * @param head the header to check
     * @return list of problem descriptions
     */
    private List<String> checkHeads(final Head head) {
        final List<String> errors = new ArrayList<>();
        if (head == null) {
            errors.add("No header found");
            return errors;
        }
        final int expectedSize = 6;
        final List<GedObject> attributes = head.getAttributes();
        if (attributes.size() < expectedSize) {
            errors.add("Wrong number of attributes: expected 6, found "
                    + attributes.size());
        }
        final String[] expectedAttributes = {
                "Source",
                "Submitter",
                "GEDCOM",
                "Destination",
                "Date",
                "Character Set"
        };
        for (int i = 0; i < expectedSize; i++) {
            final GedObject attribute = attributes.get(i);
            final String expectedType = expectedAttributes[i];
            final String actualType = attribute.getString();
            if ("Date".equals(expectedType)) {
                if (!attribute.getClass().equals(Date.class)) {
                    errors.add("attribute[" + i + "], expected type "
                            + expectedType + ", actual type " + actualType);
                }
            } else {
                if (!expectedType.equals(actualType)) {
                    errors.add("attribute[" + i + "], expected type "
                            + expectedType + ", actual type " + actualType);
                }
            }
        }
        return errors;
    }

    /**
     * @param checkResults array
     * @return a nicely build up string
     */
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    private String formatResult(final List<String> checkResults) {
        String output = "";
        for (final String result : checkResults) {
            output += "    " + result + "\n";
        }
        return output;
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(
                this, "mini-schoeller.ged");
    }

    /** */
    @Test
    public void testFactoryGedLineHeadString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "0 HEAD");
        assertMatch(line, 0, "HEAD", "", "");
    }

    /** */
    @Test
    public void testFactoryGedLineSourString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 SOUR TMG");
        assertMatch(line, 1, "SOUR", "TMG", "");
    }

    /** */
    @Test
    public void testFactoryGedLineOneSubmString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 SUBM @SUB1@");
        assertMatch(line, 1, "SUBM", "@SUB1@", "");
    }

    /** */
    @Test
    public void testFactoryGedLineZeroSubmString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "0 @SUB1@ SUBM");
        assertMatch(line, 0, "SUBM", "", "SUB1");
    }

    /** */
    @Test
    public void testFactoryGedLineNameString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 NAME Richard Schoeller");
        assertMatch(line, 1, "NAME", "Richard Schoeller", "");
    }

    /** */
    @Test
    public void testFactoryGedLineAddrString() {
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
    private void assertMatch(final AbstractGedLine line,
            final int expectedLevel, final String expectedTag,
            final String expectedTail, final String expectedXref) {
        assertEquals("Level mismatch", expectedLevel, line.getLevel());
        assertEquals("Tag mismatch", expectedTag, line.getTag());
        assertEquals("Tail mismatch", expectedTail, line.getTail());
        assertEquals("Xref mismatch", expectedXref, line.getXref());
    }
}
