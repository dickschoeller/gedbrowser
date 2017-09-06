package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public final class HeadTest {
    /** */
    private static final int ATTRIBUTE_COUNT = 7;
    /** */
    @Test
    public void testUsualHead() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Head head = builder.createHead();
        final Source source = builder.createSource("TMG");
        final SourceLink soLink = builder.createSourceLink(head, source);

        final Attribute vers = builder.createAttribute(head, "VERS", "4.0");

        final Submitter submitter = builder.createSubmitter("SUB1");
        final SubmitterLink suLink = builder.createSubmitterLink(head,
                submitter);

        final Attribute gedc = builder.createAttribute(head, "GEDC");
        builder.createAttribute(gedc, "VERS", "5.5");
        builder.createAttribute(gedc, "FORM", "LINEAGE-LINKED");

        final Attribute dest = builder.createAttribute(head, "DEST", "GED55");

        final Date date = builder.addDateToGedObject(head, "16 FEB 2001");
        builder.createAttribute(date, "TIME", "22:04");
        final Attribute chars = builder.createAttribute(head, "CHAR", "ANSI");

        final Map<String, Attribute> attributeChecks = new HashMap<>();
        attributeChecks.put("Should contain version", vers);
        attributeChecks.put("Should contain gedc item", gedc);
        attributeChecks.put("Should contain dest", dest);
        attributeChecks.put("Should contain charset", chars);
        assertHeadValid(head, soLink, suLink, date, attributeChecks);
    }

    /**
     * @param head the head we're checking
     * @param sourceLink the expected source link
     * @param submitterLink the expected submitter link
     * @param date the expected date
     * @param attributeChecks maps of messages to attributes to check
     */
    private void assertHeadValid(final Head head, final SourceLink sourceLink,
            final SubmitterLink submitterLink,
            final Date date,
            final Map<String, Attribute> attributeChecks) {
        assertEquals("Mismatch attribute count", ATTRIBUTE_COUNT,
                head.getAttributes().size());
        assertTrue("Should contain sourceLink",
                head.getAttributes().contains(sourceLink));
        assertTrue("Should contain submitterLink",
                head.getAttributes().contains(submitterLink));
        assertTrue("Should contain date", head.getAttributes().contains(date));
        for (Map.Entry<String, Attribute> entry : attributeChecks.entrySet()) {
            assertTrue(entry.getKey(),
                    head.getAttributes().contains(entry.getValue()));
        }
    }

    /** */
    @Test
    public void testHeadGedObjectString() {
        final Root root = new Root("Root");

        final Head head = new Head(root, "Head");
        root.insert(head);

        final GedObject gob = root.find("Head");
        assertTrue("Should have found head with head tag string",
                head.equals(gob) && "Head".equals(head.getString()));
    }

    /** */
    @Test
    public void testHeadGedObjectStringEmptyString() {
        final Root root = new Root("Root");

        final Head head = new Head(root, "Head", "");
        root.insert(head);

        final GedObject gob = root.find("Head");
        assertTrue("Should have found the head with head tag string",
                head.equals(gob) && "Head".equals(head.getString()));
    }

    /** */
    @Test
    public void testHeadGedObjectStringString() {
        final Root root = new Root("Root");
        final Head head2 = new Head(root, "Head", "foo");
        assertEquals("Combined string mismatch",
                "Head" + " foo", head2.getString());
    }
}
