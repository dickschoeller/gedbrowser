package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;
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
@SuppressWarnings("PMD.TooManyMethods")
final class MultimediaTest {
    /** */
    private transient String filePathString;

    /** */
    private transient GedObjectBuilder builder;
    /** */
    private transient Person person1;
    /** */
    private String homeUrl;

    /** */
    @BeforeEach
    void setUp() {
        builder = new GedObjectBuilder();
        person1 = builder.createPerson("I1", "J. Random/Schoeller/");
        final Person person2 = builder.createPerson("I2", "Anonymous/Schoeller/");
        final Person person3 = builder.createPerson("I3", "Anonymous/Jones/");
        final Family family = builder.createFamily("F1");
        final Person person = person1;
        builder.addChildToFamily(family, person);
        builder.addHusbandToFamily(family, person2);
        builder.addWifeToFamily(family, person3);
        homeUrl = "http://www.schoellerfamily.org/";
        filePathString = homeUrl + "images/genealogy/" + "luckybag1924-john-a-hayes.jpg";
    }

    /** */
    @Test
    void testBasicConstruct() {
        final Multimedia mm = new Multimedia(person1, "Multimedia", homeUrl);
        assertEquals(homeUrl, mm.getTail(), "Mismatched tail");
    }

    /** */
    @Test
    void testAppendString() {
        final Multimedia mm = builder.addMultimediaToPerson(person1, homeUrl);
        mm.appendString("genealogy");
        assertEquals(homeUrl + "genealogy", mm.getTail(), "Mismatched tail");
    }

    /**
     * @param multimedia the multimedia object to test
     * @param expParent  the expected parent
     * @param expString  the expected string
     * @param expTail    the expected tail
     */
    private void assertMatch(final Multimedia multimedia, final GedObject expParent,
        final String expString, final String expTail) {
        assertEquals(expParent, multimedia.getParent(), "Parent mismatch");
        assertEquals(expString, multimedia.getString(), "String mismatch");
        assertEquals(expTail, multimedia.getTail(), "Tail mismatch");
    }

    /** */
    @Test
    void testSetGetTail() {
        final Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        assertMatch(multimedia, null, "", "test 1");
    }

    /** */
    @Test
    void testResetToNullGetTail() {
        final Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        multimedia.setTail(null);
        assertMatch(multimedia, null, "", "");
    }

    /** */
    @Test
    void testResetToNullAndToNewValueGetTail() {
        final Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        multimedia.setTail(null);
        multimedia.setTail("test 2");
        assertMatch(multimedia, null, "", "test 2");
    }

    /** */
    @Test
    void testResetToEmptyGetTail() {
        final Multimedia multimedia;
        multimedia = new Multimedia(null, null, null);
        multimedia.setTail("test 1");
        multimedia.setTail(null);
        multimedia.setTail("test 2");
        multimedia.setTail("");
        assertMatch(multimedia, null, "", "");
    }

    /** */
    @Test
    void testGetFilePathEmpty() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals(null, visitor.getFilePath(), "File path mismatch");
    }

    /** */
    @Test
    void testGetFilePathGood() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals(filePathString, visitor.getFilePath(), "File path mismatch");
    }

    /** */
    @Test
    void testGetFileFormatGood() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("jpg", visitor.getFormat(), "File format mismatch");
    }

    /** */
    @Test
    void testGetFileFormatEmpty() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals(null, visitor.getFormat(), "File format mismatch");
    }

    /** */
    @Test
    void testGetFileFormatPartiallyBuilt() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals(null, visitor.getFormat(), "File format mismatch");
    }

    /** */
    @Test
    void testGetFileTitleGood1() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("The title", visitor.getTitle(), "File title mismatch");
    }

    /** */
    @Test
    void testGetFileTitleGood2() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "jpg");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        multimedia.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals("The title", visitor.getTitle(), "File title mismatch");
    }

    /** */
    @Test
    void testGetFileTitleEmpty() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertEquals(null, visitor.getTitle(), "File title mismatch");
    }

    @ParameterizedTest(name = "should detect image format {0}")
    @MethodSource("imageFormatCases")
    void testIsImageTrueJpg(final String formatValue) {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", formatValue);
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertTrue(visitor.isImage(), "Expected is image");
    }

    private static Stream<Arguments> imageFormatCases() {
        return Stream.of(
            Arguments.of("jpg"),
            Arguments.of("gif"),
            Arguments.of("png"),
            Arguments.of("tif")
        );
    }

    /** */
    @Test
    void testIsImageFalse() {
        final Multimedia multimedia = new Multimedia();
        final Attribute note = new Attribute(multimedia, "Note", "A note");
        multimedia.addAttribute(note);
        final Attribute filePath = new Attribute(multimedia, "File", filePathString);
        multimedia.addAttribute(filePath);
        final Attribute format = new Attribute(filePath, "Format", "html");
        filePath.addAttribute(format);
        final Attribute mediaType = new Attribute(filePath, "Media", "booK");
        filePath.addAttribute(mediaType);
        final Attribute title = new Attribute(filePath, "Title", "The title");
        filePath.addAttribute(title);
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertFalse(visitor.isImage(), "Expected is not image");
    }

    /** */
    @Test
    void testIsImageEmpty() {
        final Multimedia multimedia = new Multimedia();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        assertFalse(visitor.isImage(), "Expected is not image");
    }
}
