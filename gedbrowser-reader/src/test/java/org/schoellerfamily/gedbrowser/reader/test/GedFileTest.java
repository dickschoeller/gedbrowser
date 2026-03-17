package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;



/**
 * Contains tests for ged file.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@Slf4j
final class GedFileTest {

    /** */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    @Test
    void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        log.info(top.toString());
        final Root root = g2g.create(top);
        final String out = root.toString();
        log.info(out);

        final Person melissa = (Person) root.find("I1");
        final PersonNavigator meliNavigator = new PersonNavigator(melissa);
        final Person dick = meliNavigator.getFather();
        final PersonNavigator dickNavigator = new PersonNavigator(dick);
        final List<Person> spouses = dickNavigator.getSpouses();
        assertEquals(1, spouses.size(), "Dick only has one spouse");
        log.info(spouses.get(0).toString());
    }

    @Test
    void testFactoryGedFileHead() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final Root root = g2g.create(top);
        final Head head = (Head) root.find("Header");
        final List<String> headsCheckResults = checkHeads(head);
        assertEquals(0, headsCheckResults.size(),
                "Problems in head:\n" + formatResult(headsCheckResults));
    }

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

    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    private String formatResult(final List<String> checkResults) {
        String output = "";
        for (final String result : checkResults) {
            output += "    " + result + "\n";
        }
        return output;
    }

    private AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(
                "mini-schoeller.ged");
    }

    @Test
    void testFactoryGedLineHeadString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "0 HEAD");
        assertMatch(line, 0, "HEAD", "", "");
    }

    @Test
    void testFactoryGedLineSourString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 SOUR TMG");
        assertMatch(line, 1, "SOUR", "TMG", "");
    }

    @Test
    void testFactoryGedLineOneSubmString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 SUBM @SUB1@");
        assertMatch(line, 1, "SUBM", "@SUB1@", "");
    }

    @Test
    void testFactoryGedLineZeroSubmString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "0 @SUB1@ SUBM");
        assertMatch(line, 0, "SUBM", "", "SUB1");
    }

    @Test
    void testFactoryGedLineNameString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 NAME Richard Schoeller");
        assertMatch(line, 1, "NAME", "Richard Schoeller", "");
    }

    @Test
    void testFactoryGedLineAddrString() {
        final AbstractGedLine line = GedLine.createGedLine(
                null, "1 ADDR 242 Marked Tree Road");
        assertMatch(line, 1, "ADDR", "242 Marked Tree Road", "");
    }

    private void assertMatch(final AbstractGedLine line,
            final int expectedLevel, final String expectedTag,
            final String expectedTail, final String expectedXref) {
        assertEquals(expectedLevel, line.getLevel(), "Level mismatch");
        assertEquals(expectedTag, line.getTag(), "Tag mismatch");
        assertEquals(expectedTail, line.getTail(), "Tail mismatch");
        assertEquals(expectedXref, line.getXref(), "Xref mismatch");
    }
}
