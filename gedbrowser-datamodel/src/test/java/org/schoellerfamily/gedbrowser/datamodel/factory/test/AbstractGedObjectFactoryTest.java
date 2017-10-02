package org.schoellerfamily.gedbrowser.datamodel.factory.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
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
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
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
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
@SuppressWarnings("PMD.ExcessiveImports")
public final class AbstractGedObjectFactoryTest {
    /** */
    private GedObjectFactory factory;
    /** */
    private final GedObject parent;
    /** */
    private final String xref;
    /** */
    private final String tag;
    /** */
    private final String tail;
    /** */
    private final GedObject expected;

    /** */
    @Before
    public void setUp() {
        factory = new GedObjectFactory();
    }

    /**
     * @param parent the parent of the object being created
     * @param xref a cross reference string (can be null or empty)
     * @param tag a tag (if null or empty defaults to ATTRIBUTE)
     * @param tail additional text from the line (can be null or empty)
     * @param expected an object like the one we expect to create
     */
    public AbstractGedObjectFactoryTest(final GedObject parent,
            final String xref, final String tag, final String tail,
            final GedObject expected) {
        this.parent = parent;
        this.xref = xref;
        this.tag = tag;
        this.tail = tail;
        this.expected = expected;
    }

    /**
     * @return the collection of test parameters
     */
    @Parameters
    public static Object[][] params() {
        final GedObjectBuilder builder = new GedObjectBuilder();

        final Root root = builder.getRoot();
        final Person person = builder.createPerson(
                "I1", "Richard John/Schoeller/");
        final Attribute note = new Attribute(person, "Note", "Some text");
        person.addAttribute(note);
        return new Object[][] {
            {null, null, "ROOT", null, new Root("Root")},
            {null, "", "ROOT", null, new Root("Root")},
            {null, null, "ROOT", "", new Root("Root")},
            {null, "", "ROOT", "", new Root("Root")},
            {root, "", "ROOT", "", new Root("Root")},
            {null, null, null, null, new Attribute()},
            {null, "", "", "", new Attribute()},
            {null, null, "ATTRIBUTE", null,
                new Attribute(null, "Attribute", null)},
            {null, "", "ATTRIBUTE", "", new Attribute(null, "Attribute", "")},
            {person, null, null, null, new Attribute(person)},
            {person, "", "", "", new Attribute(person)},
            {person, null, "ATTRIBUTE", null,
                new Attribute(person, "Attribute", null)},
            {person, "", "ATTRIBUTE", "",
                new Attribute(person, "Attribute", "")},
            {null, "", "BIRT", null, new Attribute(null, "Birth", null)},
            {null, null, "INDI", null, new Person()},
            {null, "I1", "INDI", null, new Person(null, new ObjectId("I1"))},
            {person, "", "ATTRIBUTE", "",
                new Attribute(person, "Attribute", "")},
            {person, "", "BIRT", null, new Attribute(person, "Birth", null)},
            {null, null, "FAMS", "F1",
                new FamS(null, "Spouse of Family", new ObjectId("F1"))},
            {null, null, "FAMC", "F1",
                new FamC(null, "Child of Family", new ObjectId("F1"))},
            {null, null, "LINK", "I1",
                    new Link(null, "Link", new ObjectId("I1"))},
            {null, null, "FAM", null, new Family()},
            {null, "F1", "FAM", null, new Family(null, new ObjectId("F1"))},
            {null, null, "HUSB", "I1",
                new Husband(null, "Husband", new ObjectId("I1"))},
            {null, null, "WIFE", "I2",
                new Wife(null, "Wife", new ObjectId("I2"))},
            {null, null, "CHIL", "I1",
                new Child(null, "Child", new ObjectId("I1"))},
            {root, "S1", "SOUR", null,
                new Source(root, new ObjectId("S1"))},
            {person, null, "SOUR", "@S1@",
                new SourceLink(person, "Source", new ObjectId("S1"))},
            {person, null, "SOUR", "Source text",
                new Attribute(person, "Source", "Source text")},
            {root, "SUB1", "SUBM", null,
                new Submitter(root, new ObjectId("SUB1"))},
            {person, null, "SUBM", "@SUB1@",
                new SubmitterLink(person, "Submitter", new ObjectId("SUB1"))},
            {person, null, "SUBM", "SUB1",
                new SubmitterLink(person, "Submitter", new ObjectId("SUB1"))},
            {root, "SUBN", "SUBN", null,
                new Submission(root, new ObjectId("SUBN"))},
            {person, null, "SUBN", "@SUBN@",
                new SubmissionLink(person, "Submission", new ObjectId("SUBN"))},
            {person, null, "SUBN", "SUBN",
                new SubmissionLink(person, "Submission", new ObjectId("SUBN"))},
            {null, null, "DATE", null, new Date(null)},
            {person, null, "DATE", null, new Date(person)},
            {person, null, "DATE", "1 JAN 1950",
                new Date(person, "1 JAN 1950")},
            {null, null, "NAME", null, new Name()},
            {person, null, "NAME", null, new Name(person)},
            {person, null, "NAME", "John/Smith/",
                new Name(person, "John/Smith/")},
            {null, null, "PLAC", null, new Place(null, null)},
            {person, null, "PLAC", null, new Place(person, null)},
            {person, null, "PLAC", "Home", new Place(person, "Home")},
            {null, null, "TRLR", null, new Trailer(null, "Trailer")},
            {root, null, "TRLR", null, new Trailer(root, "Trailer")},
            {root, null, "HEAD", "", new Head(root, "Header")},
            {root, null, "HEAD", "", new Head(root, "Header")},
            {root, null, "HEAD", "tail", new Head(root, "Header", "tail")},
            {person, null, "NOTE", "", new Attribute(person, "Note", "")},
            {person, null, "NOTE", "Some text",
                new Attribute(person, "Note", "Some text")},
            {root, "N1", "NOTE", "Some text",
                new Note(root, new ObjectId("N1"), "Some text")},
            {person, null, "NOTE", "@N1@",
                new NoteLink(person, "Note", new ObjectId("N1"))},
            {person, null, "OBJE", null, new Multimedia(person, "Multimedia")},
            {person, null, "OBJE", null,
                new Multimedia(person, "Multimedia", null)},
            {person, null, "OBJE", "string",
                new Multimedia(person, "Multimedia", "string")},
            {person, "foo", "OBJE", null,
                new Multimedia(person, "Multimedia", null)},
            // These 2 are funky
            {note, null, "CONC", "y stuff", null},
            {note, null, "CONT", "and some more text", null},
//            {note, null, "CONC", "y stuff",
//                new Attribute(person, "Note", "Some texty stuff")},
//            {note, null, "CONT", "and some more text",
//                new Attribute(person, "Note",
//                    "Some text\nand some more text")},
        };
    }

    /** */
    @Test
    public void testFactory() {
        final GedObject gob = factory.create(parent, xref, tag, tail);
        assertEquals("Produced object doesn't match expectation",
                expected, gob);
    }
}
