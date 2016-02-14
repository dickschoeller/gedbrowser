package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class MultimediaTest {
    /** */
    private static final String FILE_PATH_STRING =
            "http://www.schoellerfamily.org/images/genealogy/"
            + "luckybag1924-john-a-hayes.jpg";
    /** */
    private static final String DUMMY = "Dummy";
    /** */
    private static final String TEST_STRUNG = "strung";
    /** */
    private static final String TEST_STRING = "string";
    /** */
    private static final String HUNDRED_DAY = "31 July 2090";
    /** */
    private static final String POTTER_DAY = "31 July 1990";
    /** */
    private static final String SHOULD_BE_EMPTY = "Should be empty";
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Person person1 = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Person person2 = new Person(root,
            new ObjectId("I2"));
    /** */
    private final transient Person person3 = new Person(root,
            new ObjectId("I3"));
    /** */
    private final transient Family family = new Family(root,
            new ObjectId("F1"));
    /** */
    private final transient FamC famC = new FamC(person1, "FAMC",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS2 = new FamS(person2, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS3 = new FamS(person3, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient Child child = new Child(family, "Child",
            new ObjectId("I1"));
    /** */
    private final transient Husband husband = new Husband(family, "Husband",
            new ObjectId("I2"));
    /** */
    private final transient Wife wife = new Wife(family, "Wife",
            new ObjectId("I3"));

    /** */
    @Before
    public void setUp() {
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        root.insert(family);

        family.insert(child);
        family.insert(husband);
        family.insert(wife);

        person1.insert(famC);
        person2.insert(famS2);
        person3.insert(famS3);
    }

    /** */
    @Test
    public void testAppendString() {
        final Multimedia job = new Multimedia(person1, "Multimedia",
                "http://www.schoellerfamily.org");
        assertEquals("http://www.schoellerfamily.org", job.getTail());
        job.appendString("/genealogy");
        assertEquals("http://www.schoellerfamily.org/genealogy", job.getTail());
    }

    /** */
    @Test
    public void testGetBirthDate() {
        final Multimedia dummy = new Multimedia(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        assertEquals("", dummy.getBirthDate());

        final Multimedia birth = new Multimedia(person1, "Birth");
        final Date date = new Date(birth, POTTER_DAY);
        assertEquals("", birth.getBirthDate());

        birth.insert(date);
        assertEquals("", birth.getBirthDate());
    }

    /** */
    @Test
    public void testGetDeathDate() {
        final Multimedia dummy = new Multimedia(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        assertEquals("", dummy.getDeathDate());

        final Multimedia death = new Multimedia(person1, "Death");
        assertEquals("", death.getDeathDate());

        final Date date = new Date(death, HUNDRED_DAY);
        death.insert(date);
        assertEquals("", death.getDeathDate());
    }

    /** */
    @Test
    public void testGetDate() {
        final Multimedia dummy = new Multimedia(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        // TODO this should become unnecessary if I can further restrict the
        // children of an attribute.
        dummy.insert(new Person());
        dummy.insert(dummyDate);
        assertEquals("", dummy.getDate());

        final Multimedia dummy1 = new Multimedia(person1, DUMMY);
        final Date dummyDate1 = new Date(dummy, null);
        dummy1.insert(dummyDate1);
        assertEquals("", dummy1.getDate());

        final Multimedia death = new Multimedia(person1, "Death");
        assertEquals("", death.getDate());

        final Date date = new Date(death, HUNDRED_DAY);
        death.insert(date);
        assertEquals("", death.getDate());
    }

    /** */
    @Test
    public void testMultimediaGedObject() {
        Multimedia multimedia;
        multimedia = new Multimedia(null);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1);
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());
    }

    /** */
    @Test
    public void testMultimediaGedObjectString() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, null);
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(null, "");
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, "");
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals("", multimedia.getTail());

        multimedia = new Multimedia(null, TEST_STRING);
        assertEquals(null, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, TEST_STRING);
        assertEquals(person1, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());
    }

    /** */
    @Test
    public void testMultimediaGedObjectStringString() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, null, null);
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(null, "", null);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, "", null);
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(null, TEST_STRING, null);
        assertEquals(null, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, TEST_STRING, null);
        assertEquals(person1, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        // ///////////////////

        multimedia = new Multimedia(null, null, "");
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, null, "");
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(null, "", "");
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, "", "");
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(null, TEST_STRING, "");
        assertEquals(null, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia = new Multimedia(person1, TEST_STRING, "");
        assertEquals(person1, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        // ///////////////////////

        multimedia = new Multimedia(null, null, TEST_STRUNG);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(TEST_STRUNG, multimedia.getTail());

        multimedia = new Multimedia(person1, null, TEST_STRUNG);
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(TEST_STRUNG, multimedia.getTail());

        multimedia = new Multimedia(null, "", TEST_STRUNG);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(TEST_STRUNG, multimedia.getTail());

        multimedia = new Multimedia(person1, "", TEST_STRUNG);
        assertEquals(person1, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(TEST_STRUNG, multimedia.getTail());

        multimedia = new Multimedia(null, TEST_STRING, TEST_STRUNG);
        assertEquals(null, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(TEST_STRUNG, multimedia.getTail());

        multimedia = new Multimedia(person1, TEST_STRING, TEST_STRUNG);
        assertEquals(person1, multimedia.getParent());
        assertEquals(TEST_STRING, multimedia.getString());
        assertEquals(TEST_STRUNG, multimedia.getTail());
    }

    /** */
    @Test
    public void testSetGetTail() {
        Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia.setTail("test 1");
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals("test 1", multimedia.getTail());

        multimedia.setTail(null);
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());

        multimedia.setTail("test 2");
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals("test 2", multimedia.getTail());

        multimedia.setTail("");
        assertEquals(null, multimedia.getParent());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getString());
        assertEquals(SHOULD_BE_EMPTY, "", multimedia.getTail());
    }

    /** */
    @Test
    public void testGetFilePathEmpty() {
        final Multimedia multimedia = new Multimedia(person1);
        assertEquals(null, multimedia.getFilePath());
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
        assertEquals(FILE_PATH_STRING, multimedia.getFilePath());
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
        assertEquals("jpg", multimedia.getFileFormat());
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
        assertEquals(null, multimedia.getFileFormat());
    }

    /** */
    @Test
    public void testGetFileFormatPartiallyBuilt() {
        final Multimedia multimedia = new Multimedia(person1);
        assertEquals(null, multimedia.getFileFormat());
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
        assertEquals("The title", multimedia.getFileTitle());
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
        assertEquals("The title", multimedia.getFileTitle());
    }

    /** */
    @Test
    public void testGetFileTitleEmpty() {
        final Multimedia multimedia = new Multimedia(person1);
        assertEquals(null, multimedia.getFileTitle());
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
        assertTrue(multimedia.isImage());
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
        assertTrue(multimedia.isImage());
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
        assertTrue(multimedia.isImage());
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
        assertTrue(multimedia.isImage());
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
        assertFalse(multimedia.isImage());
    }

    /** */
    @Test
    public void testIsImageEmpty() {
        final Multimedia multimedia = new Multimedia(person1);
        assertFalse(multimedia.isImage());
    }
}
