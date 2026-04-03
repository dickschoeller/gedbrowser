package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;



/**
 * Contains tests for head.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
final class HeadTest {
    /** */
    private static final int ATTRIBUTE_COUNT = 7;

    @Test
    void testUsualHead() {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        final Head head = builder.createHead();
        final Source source = builder.createSource("TMG");
        final SourceLink soLink = builder.createSourceLink(head, source);

        final Attribute vers = builder.createAttribute(head, "VERS", "4.0");

        final Submitter submitter = builder.createSubmitter("SUB1");
        final SubmitterLink suLink = builder.createSubmitterLink(head, submitter);

        final Attribute gedc = builder.createAttribute(head, "GEDC");
        builder.createAttribute(gedc, "VERS", "5.5");
        builder.createAttribute(gedc, "FORM", "LINEAGE-LINKED");

        final Attribute dest = builder.createAttribute(head, "DEST", "GED55");

        final Date date = builder.addDateToGedObject(head, "16 FEB 2001");
        builder.createAttribute(date, "TIME", "22:04");
        final Attribute chars = builder.createAttribute(head, "CHAR", "ANSI");

        final Map<String, Attribute> attributeChecks = Map.of("Should contain version", vers,
            "Should contain gedc item", gedc, "Should contain dest", dest, "Should contain charset",
            chars);
        assertHeadValid(head, soLink, suLink, date, attributeChecks);
    }

    private void assertHeadValid(final Head head, final SourceLink sourceLink,
        final SubmitterLink submitterLink, final Date date,
        final Map<String, Attribute> attributeChecks) {
        assertEquals(ATTRIBUTE_COUNT, head.getAttributes().size(), "Mismatch attribute count");
        assertTrue(head.getAttributes().contains(sourceLink), "Should contain sourceLink");
        assertTrue(head.getAttributes().contains(submitterLink), "Should contain submitterLink");
        assertTrue(head.getAttributes().contains(date), "Should contain date");
        for (final Map.Entry<String, Attribute> entry : attributeChecks.entrySet()) {
            assertTrue(head.getAttributes().contains(entry.getValue()), entry.getKey());
        }
    }

    @Test
    void testHeadGedObjectString() {
        final Root root = new Root("Root");

        final Head head = new Head(root, "Head");
        root.insert(head);

        final GedObject gob = root.find("Head");
        assertTrue(head.equals(gob) && "Head".equals(head.getString()),
            "Should have found head with head tag string");
    }

    @Test
    void testHeadGedObjectStringEmptyString() {
        final Root root = new Root("Root");

        final Head head = new Head(root, "Head", "");
        root.insert(head);

        final GedObject gob = root.find("Head");
        assertTrue(head.equals(gob) && "Head".equals(head.getString()),
            "Should have found the head with head tag string");
    }

    @Test
    void testHeadGedObjectStringString() {
        final Root root = new Root("Root");
        final Head head2 = new Head(root, "Head", "foo");
        assertEquals("Head" + " foo", head2.getString(), "Combined string mismatch");
    }
}
