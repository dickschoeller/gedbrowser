package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
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

        assertHeadValid(head, sourceLink, version, submittorLink, gedc, dest,
                date, charset);
    }

    /**
     * @param head the head we're checking
     * @param sourceLink the expected source link
     * @param version the expected version
     * @param submittorLink the expected submittor link
     * @param gedc the expected gedc descriptor
     * @param dest the expected destination
     * @param date the expected date
     * @param charset the expected charset
     */
    // CHECKSTYLE:OFF
    private void assertHeadValid(final Head head, final SourceLink sourceLink,
            final Attribute version, final SubmittorLink submittorLink,
            final Attribute gedc, final Attribute dest, final Date date,
            final Attribute charset) {
        // CHECKSTYLE:ON
        assertEquals("Mismatch attribute count", ATTRIBUTE_COUNT,
                head.getAttributes().size());
        assertTrue("Should contain sourceLink",
                head.getAttributes().contains(sourceLink));
        assertTrue("Should contain version",
                head.getAttributes().contains(version));
        assertTrue("Should contain submittorLink",
                head.getAttributes().contains(submittorLink));
        assertTrue("Should contain gedc item",
                head.getAttributes().contains(gedc));
        assertTrue("Should contain dest", head.getAttributes().contains(dest));
        assertTrue("Should contain date", head.getAttributes().contains(date));
        assertTrue("Should contain charset",
                head.getAttributes().contains(charset));
    }

    /** */
    @Test
    public void testHeadGedObject() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root);
        root.insert(HEAD_TAG, head);

        final GedObject gob = root.find(HEAD_TAG);
        assertTrue("Should have found head with empty string",
                head.equals(gob) && head.getString().isEmpty());
    }

    /** */
    @Test
    public void testHeadGedObjectString() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root, HEAD_TAG);
        root.insert(HEAD_TAG, head);

        final GedObject gob = root.find(HEAD_TAG);
        assertTrue("Should have found head with head tag string",
                head.equals(gob) && HEAD_TAG.equals(head.getString()));
    }

    /** */
    @Test
    public void testHeadGedObjectStringEmptyString() {
        final Root root = new Root(null, ROOT_TAG);

        final Head head = new Head(root, HEAD_TAG, "");
        root.insert(HEAD_TAG, head);

        final GedObject gob = root.find(HEAD_TAG);
        assertTrue("Should have found the head with head tag string",
                head.equals(gob) && HEAD_TAG.equals(head.getString()));
    }

    /** */
    @Test
    public void testHeadGedObjectStringString() {
        final Root root = new Root(null, ROOT_TAG);
        final Head head2 = new Head(root, HEAD_TAG, "foo");
        assertEquals("Combined string mismatch",
                HEAD_TAG + " foo", head2.getString());
    }
}
