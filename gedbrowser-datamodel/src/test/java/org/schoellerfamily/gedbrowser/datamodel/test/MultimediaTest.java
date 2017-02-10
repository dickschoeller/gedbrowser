package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

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
        final Family family = builder.createFamily1();
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

    /** */
    @Test
    public void testGetDummyBirthDate() {
        final Multimedia mm = builder.addMultimediaToPerson(person1, "Dummy");
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty birth date string",
                "", mm.getBirthDate());
    }

    /** */
    @Test
    public void testGetUninsertedBirthDate() {
        final Multimedia mm = new Multimedia(person1, "Birth");
        new Date(mm, "31 July 1990");
        assertEquals("Expected empty birth date string",
                "", mm.getBirthDate());
    }

    /** */
    @Test
    public void testGetBirthDate() {
        final Multimedia mm = new Multimedia(person1, "Birth");
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty birth date string",
                "", mm.getBirthDate());
    }

    /** */
    @Test
    public void testGetDummyDeathDate() {
        final Multimedia mm = new Multimedia(person1, "Dummy");
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty death date string",
                "", mm.getDeathDate());
    }

    /** */
    @Test
    public void testGetDeathDateWithNoDate() {
        final Multimedia mm = new Multimedia(person1, "Death");
        assertEquals("Expected empty death date string",
                "", mm.getDeathDate());
    }

    /** */
    @Test
    public void testGetDeathDate() {
        final Multimedia mm = new Multimedia(person1, "Death");
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty death date string",
                "", mm.getDeathDate());
    }

    /** */
    @Test
    public void testGetDummyDate() {
        final Multimedia mm = new Multimedia(person1, "Dummy");
        // TODO this should become unnecessary if I can further restrict the
        // children of an attribute.
        mm.insert(new Person());
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty date string", "", mm.getDate());
    }

    /** */
    @Test
    public void testGetDateWithString() {
        final Multimedia mm = new Multimedia(person1, "Dummy");
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty date string", "", mm.getDate());
    }

    /** */
    @Test
    public void testGetDateWithNoDate() {
        final Multimedia death = new Multimedia(person1, "Death");
        assertEquals("Expected empty date string", "", death.getDate());
    }

    /** */
    @Test
    public void testGetDateWithDate() {
        final Multimedia mm = new Multimedia(person1, "Death");
        builder.addDateToGedObject(mm, "31 July 1990");
        assertEquals("Expected empty date string", "", mm.getDate());
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
        final Multimedia multimedia = new Multimedia(person1);
        assertEquals("File path mismatch", null, multimedia.getFilePath());
    }

    /** */
    @Test
    public void testGetFilePathGood() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertEquals("File path mismatch",
                filePathString, multimedia.getFilePath());
    }

    /** */
    @Test
    public void testGetFileFormatGood() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertEquals("File format mismatch",
                "jpg", multimedia.getFileFormat());
    }

    /** */
    @Test
    public void testGetFileFormatEmpty() {
        final Multimedia multimedia = new Multimedia(person1);
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        assertEquals("File format mismatch", null, multimedia.getFileFormat());
    }

    /** */
    @Test
    public void testGetFileFormatPartiallyBuilt() {
        final Multimedia multimedia = new Multimedia(person1);
        assertEquals("File format mismatch", null, multimedia.getFileFormat());
    }

    /** */
    @Test
    public void testGetFileTitleGood1() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertEquals("File title mismatch",
                "The title", multimedia.getFileTitle());
    }

    /** */
    @Test
    public void testGetFileTitleGood2() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertEquals("File title mismatch",
                "The title", multimedia.getFileTitle());
    }

    /** */
    @Test
    public void testGetFileTitleEmpty() {
        final Multimedia multimedia = new Multimedia(person1);
        assertEquals("File title mismatch", null, multimedia.getFileTitle());
    }

    /** */
    @Test
    public void testIsImageTrueJpg() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertTrue("Expected is image", multimedia.isImage());
    }

    /** */
    @Test
    public void testIsImageTrueGif() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertTrue("Expected is image", multimedia.isImage());
    }

    /** */
    @Test
    public void testIsImageTruePng() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertTrue("Expected is image", multimedia.isImage());
    }

    /** */
    @Test
    public void testIsImageTrueTif() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertTrue("Expected is image", multimedia.isImage());
    }

    /** */
    @Test
    public void testIsImageFalse() {
        final Multimedia multimedia = new Multimedia(person1);
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
        assertFalse("Expected is not image", multimedia.isImage());
    }

    /** */
    @Test
    public void testIsImageEmpty() {
        final Multimedia multimedia = new Multimedia(person1);
        assertFalse("Expected is not image", multimedia.isImage());
    }
}
