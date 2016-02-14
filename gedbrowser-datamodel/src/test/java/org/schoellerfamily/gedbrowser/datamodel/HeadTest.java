package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class HeadTest {
    /** */
    private static final int ATTRIBUTE_COUNT = 7;
    /** */
    private static final String HEAD_TAG = "Head";
    /** */
    private static final String ROOT_TAG = "Root";

    /** */
    @Test
    public void testUsualHead() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root, "HEAD");
        root.insert("HEAD", head);

        final Source source = new Source(root, new ObjectId("TMG"));
        root.insert("TMG", source);

        final SourceLink sourceLink = new SourceLink(head, "SOUR",
                new ObjectId("TMG"));
        head.insert(sourceLink);

        final Attribute version = new Attribute(head, "VERS", "4.0");
        head.insert(version);

        final Submittor submittor = new Submittor(root, "@SUB1@");
        root.insert("SUB1", submittor);
        final SubmittorLink submittorLink = new SubmittorLink(head, "SUBM",
                new ObjectId("@SUB1@"));
        head.insert(submittorLink);

        final Attribute gedc = new Attribute(head, "GEDC");
        head.insert(gedc);
        final Attribute vers = new Attribute(gedc, "VERS", "5.5");
        gedc.insert(vers);
        final Attribute form = new Attribute(gedc, "FORM", "LINEAGE-LINKED");
        gedc.insert(form);

        final Attribute dest = new Attribute(head, "DEST", "GED55");
        head.insert(dest);

        final Date date = new Date(head, "16 FEB 2001");
        head.insert(date);
        final Attribute time = new Attribute(date, "TIME", "22:04");
        date.insert(time);

        final Attribute charset = new Attribute(head, "CHAR", "ANSI");
        head.insert(charset);

        assertEquals(ATTRIBUTE_COUNT, head.getAttributes().size());
        assertTrue(head.getAttributes().contains(sourceLink));
        assertTrue(head.getAttributes().contains(version));
        assertTrue(head.getAttributes().contains(submittorLink));
        assertTrue(head.getAttributes().contains(gedc));
        assertTrue(head.getAttributes().contains(dest));
        assertTrue(head.getAttributes().contains(date));
        assertTrue(head.getAttributes().contains(charset));
    }

    /** */
    @Test
    public void testHeadGedObject() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root);
        root.insert(HEAD_TAG, head);

        final GedObject gob = root.find(HEAD_TAG);
        assertEquals(head, gob);

        assertTrue("Head string should be empty", head.getString().isEmpty());
    }

    /** */
    @Test
    public void testHeadGedObjectString() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root, HEAD_TAG);
        root.insert(HEAD_TAG, head);

        final GedObject gob = root.find(HEAD_TAG);
        assertEquals(head, gob);

        assertEquals(HEAD_TAG, head.getString());
    }

    /** */
    @Test
    public void testHeadGedObjectStringString() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root, HEAD_TAG, "");
        root.insert(HEAD_TAG, head);

        final GedObject gob = root.find(HEAD_TAG);
        assertEquals(head, gob);

        assertEquals(HEAD_TAG, head.getString());

        final Head head2 = new Head(root, HEAD_TAG, "foo");
        assertEquals(HEAD_TAG + " foo", head2.getString());
    }
}
