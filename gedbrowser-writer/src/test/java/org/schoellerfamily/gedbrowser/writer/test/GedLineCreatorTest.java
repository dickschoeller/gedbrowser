package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.writer.GedWriterFile;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;
import org.schoellerfamily.gedbrowser.writer.GedWriterLineCreator;

/**
 * @author Dick Schoeller
 */
public class GedLineCreatorTest {
    /** */
    private GedWriterLineCreator gedLineCreator;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        gedLineCreator = new GedWriterLineCreator();
        final Root root = builder.getRoot();
        root.setFilename("huh.ged");
        root.setDbName("huh");
        final Head head = builder.createHead();
        final Submitter submitter = builder.createSubmitter("SUB1",
                "Richard/Schoeller/");
        builder.createSubmitterLink(head, submitter);
        final Submission submission = builder.createSubmission("SUBN");
        builder.createSubmissionLink(submission);
        final Family f1 = builder.createFamily("F1");
        final Person p1 = builder.createPerson("I1", "John/Doe/");
        final Attribute birth = builder.createPersonEvent(p1, "Birth",
                "25 DEC 1990");
        final Place birthPlace = new Place(birth, "Springfield, USA");
        birth.addAttribute(birthPlace);
        final Source source = builder.createSource("S1");
        final Note note = new Note(root, new ObjectId("N1"), "This is a note");
        root.insert(note);
        final NoteLink noteLink1 = new NoteLink(p1, "Note", new ObjectId("N1"));
        p1.addAttribute(noteLink1);
        builder.createSourceLink(birth, source);
        final Person p2 = builder.createPerson("I2", "Mary/Roe/");
        // These are inserted out of order to check that the results are
        // returned in order.
        final Person p4 = builder.createPerson("I4", "Anonymous/Smith/");
        final Person p3 = builder.createPerson("I3", "Junior/Doe/");
        final Link link = new Link(p4, "Link", new ObjectId("I2"));
        p4.addAttribute(link);
        builder.addHusbandToFamily(f1, p1);
        builder.addWifeToFamily(f1, p2);
        builder.addChildToFamily(f1, p3);
        final Trailer trailer = new Trailer(root, "Trailer");
        root.insert(trailer);
        final Multimedia multimedia = builder.addMultimediaToPerson(p4, "");
        builder.createAttribute(multimedia, "FILE",
                "https://archive.org/details/luckybag1924unse");
        root.accept(gedLineCreator);
    }

    /** */
    @Test
    public void testLineCount() {
        final Collection<GedWriterLine> lines = gedLineCreator.getLines();
        final int expected = 33;
        assertEquals("Output size mismatch", expected, lines.size());
    }

    /** */
    @Test
    public void testRootFilename() {
        final List<GedWriterLine> lines = gedLineCreator.getLines();
        final GedWriterFile file = (GedWriterFile) lines.get(0);
        assertEquals("Should be the filename set above", "huh.ged",
                file.getFilename());
    }

    /** */
    @Test
    public void testRootDbName() {
        final List<GedWriterLine> lines = gedLineCreator.getLines();
        final GedWriterFile file = (GedWriterFile) lines.get(0);
        assertEquals("Should be the DB name set above", "huh",
                file.getDbName());
    }
}
