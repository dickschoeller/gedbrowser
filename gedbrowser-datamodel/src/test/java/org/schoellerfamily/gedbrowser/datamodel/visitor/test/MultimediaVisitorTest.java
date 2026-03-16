package org.schoellerfamily.gedbrowser.datamodel.visitor.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

/**
 * Contains tests for multimedia visitor.
 *
 * @author Richard Schoeller
 */
final class MultimediaVisitorTest {
    @Test
    void testNoFilePathFromUnrelated() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Name().accept(visitor);
        new Person().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submission().accept(visitor);
        new SubmissionLink().accept(visitor);
        new Submitter().accept(visitor);
        new SubmitterLink().accept(visitor);
        new Trailer().accept(visitor);
        new Wife().accept(visitor);
        final GedObject gob = new GedObject() {
            /**
             * Executes accept.
             *
             * @param visitor the visitor
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            } };
        gob.accept(visitor);
        assertNull(visitor.getFilePath(), "Found unexpected content");
    }

    @Test
    void testNoFormatFromUnrelated() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Name().accept(visitor);
        new Person().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submitter().accept(visitor);
        new SubmitterLink().accept(visitor);
        new Trailer().accept(visitor);
        new Wife().accept(visitor);
        final GedObject gob = new GedObject() {
            /**
             * Executes accept.
             *
             * @param visitor the visitor
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            } };
        gob.accept(visitor);
        assertNull(visitor.getFormat(), "Found unexpected content");
    }

    @Test
    void testNoTitleFromUnrelated() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Child().accept(visitor);
        new Date(null).accept(visitor);
        new FamC().accept(visitor);
        new Family().accept(visitor);
        new FamS().accept(visitor);
        new Head().accept(visitor);
        new Husband().accept(visitor);
        new Link(null).accept(visitor);
        new Name().accept(visitor);
        new Person().accept(visitor);
        new Place().accept(visitor);
        new Root().accept(visitor);
        new Source().accept(visitor);
        new SourceLink().accept(visitor);
        new Submitter().accept(visitor);
        new SubmitterLink().accept(visitor);
        new Trailer().accept(visitor);
        new Wife().accept(visitor);
        final GedObject gob = new GedObject() {
            /**
             * Executes accept.
             *
             * @param visitor the visitor
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            } };
        gob.accept(visitor);
        assertNull(visitor.getTitle(), "Found unexpected content");
    }

    @Test
    void testVisitMultimediaExtractsFileFormatAndTitle() {
        final Multimedia multimedia = new Multimedia();
        multimedia.addAttribute(buildTitle(multimedia, "Holiday Photo"));
        multimedia.addAttribute(buildFileWithFormat(multimedia, "photo.jpg", "jpg"));

        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);

        assertEquals("Holiday Photo", visitor.getTitle());
        assertEquals("photo.jpg", visitor.getFilePath());
        assertEquals("jpg", visitor.getFormat());
    }

    @Test
    void testVisitAttributeSetsTitleAndFormatDirectly() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Attribute(null, "Title", "Some Title").accept(visitor);
        new Attribute(null, "Format", "PNG").accept(visitor);

        assertEquals("Some Title", visitor.getTitle());
        assertEquals("PNG", visitor.getFormat());
    }

    @Test
    void testIsImageRecognizesSupportedFormatsCaseInsensitive() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Attribute(null, "Format", "JPG").accept(visitor);
        assertTrue(visitor.isImage());

        new Attribute(null, "Format", "gif").accept(visitor);
        assertTrue(visitor.isImage());

        new Attribute(null, "Format", "png").accept(visitor);
        assertTrue(visitor.isImage());

        new Attribute(null, "Format", "tif").accept(visitor);
        assertTrue(visitor.isImage());
    }

    @Test
    void testIsVideoRecognizesSupportedFormatsCaseInsensitive() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        for (final String format : new String[] {"AVI", "mp4", "MOV", "m4v", "mpg", "mpeg", "webm"}) {
            new Attribute(null, "Format", format).accept(visitor);
            assertTrue(visitor.isVideo(), "Expected video type for " + format);
        }
    }

    @Test
    void testIsYouTubeAndIsMedia() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Attribute(null, "Format", "youtube").accept(visitor);

        assertTrue(visitor.isYouTube());
        assertTrue(visitor.isMedia());
        assertFalse(visitor.isImage());
        assertFalse(visitor.isVideo());
    }

    @Test
    void testUnsupportedFormatIsNotMedia() {
        final MultimediaVisitor visitor = new MultimediaVisitor();
        new Attribute(null, "Format", "txt").accept(visitor);

        assertFalse(visitor.isImage());
        assertFalse(visitor.isVideo());
        assertFalse(visitor.isYouTube());
        assertFalse(visitor.isMedia());
    }

    @Test
    void testIsMediaForImageAndVideoFormats() {
        final MultimediaVisitor visitor = new MultimediaVisitor();

        new Attribute(null, "Format", "jpg").accept(visitor);
        assertTrue(visitor.isMedia());

        new Attribute(null, "Format", "mp4").accept(visitor);
        assertTrue(visitor.isMedia());
    }

    @Test
    void testNullFormatIsNotMedia() {
        final MultimediaVisitor visitor = new MultimediaVisitor();

        assertNull(visitor.getFormat());
        assertFalse(visitor.isImage());
        assertFalse(visitor.isVideo());
        assertFalse(visitor.isYouTube());
        assertFalse(visitor.isMedia());
    }

    private Attribute buildTitle(final Multimedia parent, final String title) {
        return new Attribute(parent, "Title", title);
    }

    private Attribute buildFileWithFormat(final Multimedia parent,
            final String filePath, final String format) {
        final Attribute file = new Attribute(parent, "File", filePath);
        file.addAttribute(new Attribute(file, "Format", format));
        return file;
    }
}
