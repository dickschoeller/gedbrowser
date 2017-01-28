package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class MultimediaTest {
    /** */
    private static final String FILE_PATH_STRING =
            "http://www.schoellerfamily.org/images/genealogy/"
            + "luckybag1924-john-a-hayes.jpg";
    /** */
    private transient Person person1;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        final Person person3 = builder.createPerson3();
        final Family family = builder.createFamily1();
        builder.addChildToFamily(family, person1);
        builder.addHusbandToFamily(family, person2);
        builder.addWifeToFamily(family, person3);
    }

    /** */
    @Test
    public void testBasicConstruct() {
        final Multimedia job = new Multimedia(person1, "Multimedia",
                "http://www.schoellerfamily.org");
        assertEquals("Mismatched tail",
                "http://www.schoellerfamily.org", job.getTail());
    }

    /** */
    @Test
    public void testAppendString() {
        final Multimedia job = new Multimedia(person1, "Multimedia",
                "http://www.schoellerfamily.org");
        job.appendString("/genealogy");
        assertEquals("Mismatched tail",
                "http://www.schoellerfamily.org/genealogy", job.getTail());
    }

    /** */
    @Test
    public void testGetBirthDate() {
        final Multimedia dummy = new Multimedia(person1, "Dummy");
        final Date dummyDate = new Date(dummy, "31 July 1990");
        dummy.insert(dummyDate);
        assertEquals("Expected empty birth date string",
                "", dummy.getBirthDate());

        final Multimedia birth = new Multimedia(person1, "Birth");
        final Date date = new Date(birth, "31 July 1990");
        assertEquals("Expected empty birth date string",
                "", birth.getBirthDate());

        birth.insert(date);
        assertEquals("Expected empty birth date string",
                "", birth.getBirthDate());
    }

    /** */
    @Test
    public void testGetDeathDate() {
        final Multimedia dummy = new Multimedia(person1, "Dummy");
        final Date dummyDate = new Date(dummy, "31 July 1990");
        dummy.insert(dummyDate);
        assertEquals("Expected empty death date string",
                "", dummy.getDeathDate());

        final Multimedia death = new Multimedia(person1, "Death");
        assertEquals("Expected empty death date string",
                "", death.getDeathDate());

        final Date date = new Date(death, "31 July 2090");
        death.insert(date);
        assertEquals("Expected empty death date string",
                "", death.getDeathDate());
    }

    /** */
    @Test
    public void testGetDate() {
        final Multimedia dummy = new Multimedia(person1, "Dummy");
        final Date dummyDate = new Date(dummy, "31 July 1990");
        // TODO this should become unnecessary if I can further restrict the
        // children of an attribute.
        dummy.insert(new Person());
        dummy.insert(dummyDate);
        assertEquals("Expected empty date string", "", dummy.getDate());

        final Multimedia dummy1 = new Multimedia(person1, "Dummy");
        final Date dummyDate1 = new Date(dummy, null);
        dummy1.insert(dummyDate1);
        assertEquals("Expected empty date string", "", dummy1.getDate());

        final Multimedia death = new Multimedia(person1, "Death");
        assertEquals("Expected empty date string", "", death.getDate());

        final Date date = new Date(death, "31 July 2090");
        death.insert(date);
        assertEquals("Expected empty date string", "", death.getDate());
    }

    /** */
    @Test
    public void testMultimediaGedObject() {
        Multimedia multimedia;
        multimedia = new Multimedia(null);
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1);
        assertMatch(multimedia, person1, "", "");
    }

    /** */
    @Test
    public void testMultimediaGedObjectString() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null);
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1, null);
        assertMatch(multimedia, person1, "", "");

        multimedia = new Multimedia(null, "");
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1, "");
        assertMatch(multimedia, person1, "", "");

        multimedia = new Multimedia(null, "string");
        assertMatch(multimedia, null, "string", "");

        multimedia = new Multimedia(person1, "string");
        assertMatch(multimedia, person1, "string", "");
    }

    /** */
    @Test
    public void testMultimediaGedObjectStringString() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1, null, null);
        assertMatch(multimedia, person1, "", "");

        multimedia = new Multimedia(null, "", null);
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1, "", null);
        assertMatch(multimedia, person1, "", "");

        multimedia = new Multimedia(null, "string", null);
        assertMatch(multimedia, null, "string", "");

        multimedia = new Multimedia(person1, "string", null);
        assertMatch(multimedia, person1, "string", "");

        multimedia = new Multimedia(null, null, "");
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1, null, "");
        assertMatch(multimedia, person1, "", "");

        multimedia = new Multimedia(null, "", "");
        assertMatch(multimedia, null, "", "");

        multimedia = new Multimedia(person1, "", "");
        assertMatch(multimedia, person1, "", "");

        multimedia = new Multimedia(null, "string", "");
        assertMatch(multimedia, null, "string", "");

        multimedia = new Multimedia(person1, "string", "");
        assertMatch(multimedia, person1, "string", "");

        multimedia = new Multimedia(null, null, "strung");
        assertMatch(multimedia, null, "", "strung");

        multimedia = new Multimedia(person1, null, "strung");
        assertMatch(multimedia, person1, "", "strung");

        multimedia = new Multimedia(null, "", "strung");
        assertMatch(multimedia, null, "", "strung");

        multimedia = new Multimedia(person1, "", "strung");
        assertMatch(multimedia, person1, "", "strung");

        multimedia = new Multimedia(null, "string", "strung");
        assertMatch(multimedia, null, "string", "strung");

        multimedia = new Multimedia(person1, "string", "strung");
        assertMatch(multimedia, person1, "string", "strung");
    }

    /**
     * @param multimedia the multimedia object to test
     * @param expParent the expected parent
     * @param expString the expected string
     * @param expTail the expected tail
     */
    private void assertMatch(final Multimedia multimedia,
            final Person expParent, final String expString,
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
        assertEquals("Parent mismatch", null, multimedia.getParent());
        assertEquals("String mismatch", "", multimedia.getString());
        assertEquals("Tail mismatch", "", multimedia.getTail());

        multimedia.setTail("test 1");
        assertEquals("Parent mismatch", null, multimedia.getParent());
        assertEquals("String mismatch", "", multimedia.getString());
        assertEquals("Tail mismatch", "test 1", multimedia.getTail());

        multimedia.setTail(null);
        assertEquals("Parent mismatch", null, multimedia.getParent());
        assertEquals("String mismatch", "", multimedia.getString());
        assertEquals("Tail mismatch", "", multimedia.getTail());

        multimedia.setTail("test 2");
        assertEquals("Parent mismatch", null, multimedia.getParent());
        assertEquals("String mismatch", "", multimedia.getString());
        assertEquals("Tail mismatch", "test 2", multimedia.getTail());

        multimedia.setTail("");
        assertEquals("Parent mismatch", null, multimedia.getParent());
        assertEquals("String mismatch", "", multimedia.getString());
        assertEquals("Tail mismatch", "", multimedia.getTail());
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        assertEquals("File path mismatch",
                FILE_PATH_STRING, multimedia.getFilePath());
    }

    /** */
    @Test
    public void testGetFileFormatGood() {
        final Multimedia multimedia = new Multimedia(person1);
        final Attribute note =
                new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath =
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
                new Attribute(multimedia, "File", FILE_PATH_STRING);
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
