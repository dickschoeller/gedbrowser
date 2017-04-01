package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

/**
 * @author Dick Schoeller
 */
public final class MultimediaTest {
    /** */
    private transient String filePathString;

    /** */
    private transient GedObjectBuilder builder;
    /** */
    private transient Person person1;
    /** */
    private String homeUrl;

    /** */
    @Before
    public void setUp() {
        builder = new GedObjectBuilder();
        person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();
        final Family family = builder.createFamily("F1");
        builder.addChildToFamily(family, person1);
        builder.addHusbandToFamily(family, person2);
        builder.addWifeToFamily(family, person3);
        homeUrl = "http://www.schoellerfamily.org/";
        filePathString = homeUrl + "images/genealogy/"
                + "luckybag1924-john-a-hayes.jpg";
    }

    /** */
    @Test
    public void testBasicConstruct() {
        final Multimedia mm = new Multimedia(
                person1, "Multimedia", homeUrl);
        assertEquals("Mismatched tail",
                homeUrl, mm.getTail());
    }

    /** */
    @Test
    public void testAppendString() {
        final Multimedia mm = builder.addMultimediaToPerson(
                person1, homeUrl);
        mm.appendString("genealogy");
        assertEquals("Mismatched tail", homeUrl + "genealogy", mm.getTail());
    }

    /**
     * @param multimedia the multimedia object to test
     * @param expParent the expected parent
     * @param expString the expected string
     * @param expTail the expected tail
     */
    private void assertMatch(final Multimedia multimedia,
            final GedObject expParent, final String expString,
            final String expTail) {
        assertEquals("Parent mismatch", expParent, multimedia.getParent());
        assertEquals("String mismatch", expString, multimedia.getString());
        assertEquals("Tail mismatch", expTail, multimedia.getTail());
    }

    /** */
    @Test
    public void testSetGetTail() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        assertMatch(multimedia, null, "", "test 1");
    }

    /** */
    @Test
    public void testResetToNullGetTail() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        multimedia.setTail(null);
        assertMatch(multimedia, null, "", "");
    }

    /** */
    @Test
    public void testResetToNullAndToNewValueGetTail() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        multimedia.setTail(null);
        multimedia.setTail("test 2");
        assertMatch(multimedia, null, "", "test 2");
    }

    /** */
    @Test
    public void testResetToEmptyGetTail() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        multimedia.setTail(null);
        multimedia.setTail("test 2");
        multimedia.setTail("");
        assertMatch(multimedia, null, "", "");
    }

    /** */
    @Test
    public void testGetFilePathEmpty() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File path mismatch", null, visitor.getFilePath());
    }

    /** */
    @Test
    public void testGetFilePathGood() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File path mismatch",
                filePathString, visitor.getFilePath());
    }

    /** */
    @Test
    public void testGetFileFormatGood() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File format mismatch",
                "jpg", visitor.getFormat());
    }

    /** */
    @Test
    public void testGetFileFormatEmpty() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File format mismatch", null, visitor.getFormat());
    }

    /** */
    @Test
    public void testGetFileFormatPartiallyBuilt() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File format mismatch", null, visitor.getFormat());
    }

    /** */
    @Test
    public void testGetFileTitleGood1() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File title mismatch", "The title", visitor.getTitle());
    }

    /** */
    @Test
    public void testGetFileTitleGood2() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        multimedia.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File title mismatch", "The title", visitor.getTitle());
    }

    /** */
    @Test
    public void testGetFileTitleEmpty() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("File title mismatch", null, visitor.getTitle());
    }

    /** */
    @Test
    public void testIsImageTrueJpg() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertTrue("Expected is image", visitor.isImage());
    }

    /** */
    @Test
    public void testIsImageTrueGif() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "gif");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertTrue("Expected is image", visitor.isImage());
    }

    /** */
    @Test
    public void testIsImageTruePng() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "png");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertTrue("Expected is image", visitor.isImage());
    }

    /** */
    @Test
    public void testIsImageTrueTif() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "tif");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertTrue("Expected is image", visitor.isImage());
    }

    /** */
    @Test
    public void testIsImageFalse() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "html");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertFalse("Expected is not image", visitor.isImage());
    }

    /** */
    @Test
    public void testIsImageEmpty() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertFalse("Expected is not image", visitor.isImage());
    }
}
