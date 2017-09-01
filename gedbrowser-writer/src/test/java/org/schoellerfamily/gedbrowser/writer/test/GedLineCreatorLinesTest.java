package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
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
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;
import org.schoellerfamily.gedbrowser.writer.GedWriterLineCreator;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class GedLineCreatorLinesTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final String message;
    /** */
    private final String expected;
    /** */
    private final String actual;

    /**
     * This array contains the expected output and messages to go along with.
     */
    private static final String[][] output = {
            { "Should be an empty", "" },
            { "Should be an HEAD", "0 HEAD" },
            { "Should be an SUBM", "1 SUBM @SUB1@" },
            { "Should be an SUBN", "1 SUBN @SUBN@" },
            { "Should be a SUBM", "0 @SUB1@ SUBM" },
            { "Should be a NAME", "1 NAME Richard/Schoeller/" },
            { "Should be an INDI", "0 @I1@ INDI" },
            { "Should be a NAME", "1 NAME John/Doe/" },
            { "Should be a BIRT", "1 BIRT" },
            { "Should be a DATE", "2 DATE 25 DEC 1990" },
            { "Should be a PLAC", "2 PLAC Springfield, USA" },
            { "Should be a SOUR", "2 SOUR @S1@" },
            { "Should be a NOTE", "1 NOTE @N1@" },
            { "Should be a NOTE", "1 NOTE This is an embedded note" },
            { "Should be a NOTE", "1 NOTE This is an embedded note" },
            { "Should be a CONT", "2 CONT with continuation" },
            { "Should be a NOTE", "1 NOTE This is an embedded note with"
                    + " really, really, really long content, long enough"
                    + " content to require that a concatentation be used. XXXX"
                    + " XXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX"
                    + " XXXXXXXXX X" },
            { "Should be a CONC", "2 CONC XXXXXXXX XXXXXXXXX XXXXXXXXX"
                    + " XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX"
                    + " XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX"
                    + " XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX"
                    + " XXXXXXX, XXXX XXXXX" },
            { "Should be a CONC", "2 CONC X should have broken before the last"
                    + " X" },
            { "Should be a FAMS", "1 FAMS @F1@" },
            { "Should be an INDI", "0 @I2@ INDI" },
            { "Should be a NAME", "1 NAME Mary/Roe/" },
            { "Should be a FAMS", "1 FAMS @F1@" },
            { "Should be an INDI", "0 @I3@ INDI" },
            { "Should be a NAME", "1 NAME Junior/Doe/" },
            { "Should be a FAMC", "1 FAMC @F1@" },
            { "Should be an INDI", "0 @I4@ INDI" },
            { "Should be a NAME", "1 NAME Anonymous/Smith/" },
            { "Should be a NAME", "1 LINK @I2@" },
            { "Should be a OBJE", "1 OBJE" },
            { "Should be a FILE",
                    "2 FILE https://archive.org/details/luckybag1924unse" },
            { "Should be a FAM", "0 @F1@ FAM" },
            { "Should be a HUSB", "1 HUSB @I1@" },
            { "Should be a WIFE", "1 WIFE @I2@" },
            { "Should be a CHIL", "1 CHIL @I3@" },
            { "Should be a SOUR", "0 @S1@ SOUR" },
            { "Should be a NOTE", "0 @N1@ NOTE This is a note" },
            { "Should be a SUBN", "0 @SUBN@ SUBN" },
            { "Should be a TRLR", "0 TRLR" },
            { "Should not be here", "XXXXX" },
            { "Should not be here", "XXXXX" },
            { "Should not be here", "XXXXX" },
            { "Should not be here", "XXXXX" },
            { "Should not be here", "XXXXX" },
    };

    /**
     * @return create the table of expected and actual
     */
    @Parameters
    public static Collection<String[]> data() {
        final GedObjectBuilder builder = new GedObjectBuilder();
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
        final Note note2 = new Note();
        note2.setParent(p1);
        note2.appendString("This is an embedded note");
        p1.addAttribute(note2);
        final Note note3 = new Note();
        note3.setParent(p1);
        note3.appendString("This is an embedded note\nwith continuation");
        p1.addAttribute(note3);

        final Note note4 = new Note();
        note4.setParent(p1);
        note4.appendString("This is an embedded note with"
        + " really, really, really long content, long enough"
        + " content to require that a concatentation be used. XXXX XXXX"
        + " XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX"
        + " XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX"
        + " XXXXXXX, XXXX XXXXXX should have broken before the last X");
        p1.addAttribute(note4);

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
        final GedWriterLineCreator gedLineCreator = new GedWriterLineCreator();
        root.accept(gedLineCreator);
        final List<GedWriterLine> lines = gedLineCreator.getLines();
        final List<String[]> parameters = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            final String message = output[i][0];
            final String expected = output[i][1];
            final String actual = lines.get(i).getLine();
            final String[] array = { message, expected, actual };
            parameters.add(array);
        }
        return parameters;
    }

    /**
     * @param message the message string
     * @param expected the expected line string
     * @param actual the actual line string
     */
    public GedLineCreatorLinesTest(final String message, final String expected,
            final String actual) {
        this.message = message;
        this.expected = expected;
        this.actual = actual;
    }

    /** */
    @Test
    public void testLine() {
        logger.info("Actual line: " + actual);
        assertEquals(message, expected, actual);
    }
}
