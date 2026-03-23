package org.schoellerfamily.gedbrowser.datamodel.factory.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedobject.datamodel.factory.GedObjectStarterFactory;



/**
 * Contains tests for abstract ged object factory.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.ExcessiveImports")
final class AbstractGedObjectFactoryTest {
    /** */
    private static final Root ROOT = new Root();
    /** */
    private static final GedObjectBuilder BUILDER = new GedObjectBuilderImpl(ROOT);
    /** */
    private static final Person PERSON = BUILDER.createPerson("I1", "Richard John/Schoeller/");
    /** */
    private static final Attribute NOTE = new Attribute(PERSON, "Note", "Some text");

    /** */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    private static final Object[][] PARAMETERS = { { null, null, "ROOT", null, new Root("Root") },
        { null, "", "ROOT", null, new Root("Root") }, { null, null, "ROOT", "", new Root("Root") },
        { null, "", "ROOT", "", new Root("Root") }, { ROOT, "", "ROOT", "", new Root("Root") },
        { null, null, null, null, new Attribute() }, { null, "", "", "", new Attribute() },
        { null, null, "ATTRIBUTE", null, new Attribute(null, "Attribute", null) },
        { null, "", "ATTRIBUTE", "", new Attribute(null, "Attribute", "") },
        { PERSON, null, null, null, new Attribute(PERSON) },
        { PERSON, "", "", "", new Attribute(PERSON) },
        { PERSON, null, "ATTRIBUTE", null, new Attribute(PERSON, "Attribute", null) },
        { PERSON, "", "ATTRIBUTE", "", new Attribute(PERSON, "Attribute", "") },
        { null, "", "BIRT", null, new Attribute(null, "Birth", null) },
        { null, null, "INDI", null, new Person() },
        { null, "I1", "INDI", null, new Person(null, new ObjectId("I1")) },
        { PERSON, "", "ATTRIBUTE", "", new Attribute(PERSON, "Attribute", "") },
        { PERSON, "", "BIRT", null, new Attribute(PERSON, "Birth", null) },
        { null, null, "FAMS", "F1", new FamS(null, "Spouse of Family", new ObjectId("F1")) },
        { null, null, "FAMC", "F1", new FamC(null, "Child of Family", new ObjectId("F1")) },
        { null, null, "LINK", "I1", new Link(null, "Link", new ObjectId("I1")) },
        { null, null, "FAM", null, new Family() },
        { null, "F1", "FAM", null, new Family(null, new ObjectId("F1")) },
        { null, null, "HUSB", "I1", new Husband(null, "Husband", new ObjectId("I1")) },
        { null, null, "WIFE", "I2", new Wife(null, "Wife", new ObjectId("I2")) },
        { null, null, "CHIL", "I1", new Child(null, "Child", new ObjectId("I1")) },
        { ROOT, "S1", "SOUR", null, new Source(ROOT, new ObjectId("S1")) },
        { PERSON, null, "SOUR", "@S1@", new SourceLink(PERSON, "Source", new ObjectId("S1")) },
        { PERSON, null, "SOUR", "Source text", new Attribute(PERSON, "Source", "Source text") },
        { ROOT, "SUB1", "SUBM", null, new Submitter(ROOT, new ObjectId("SUB1")) },
        { PERSON, null, "SUBM", "@SUB1@",
            new SubmitterLink(PERSON, "Submitter", new ObjectId("SUB1")) },
        { PERSON, null, "SUBM", "SUB1",
            new SubmitterLink(PERSON, "Submitter", new ObjectId("SUB1")) },
        { ROOT, "SUBN", "SUBN", null, new Submission(ROOT, new ObjectId("SUBN")) },
        { PERSON, null, "SUBN", "@SUBN@",
            new SubmissionLink(PERSON, "Submission", new ObjectId("SUBN")) },
        { PERSON, null, "SUBN", "SUBN",
            new SubmissionLink(PERSON, "Submission", new ObjectId("SUBN")) },
        { null, null, "DATE", null, new Date(null) },
        { PERSON, null, "DATE", null, new Date(PERSON) },
        { PERSON, null, "DATE", "1 JAN 1950", new Date(PERSON, "1 JAN 1950") },
        { null, null, "NAME", null, new Name() }, { PERSON, null, "NAME", null, new Name(PERSON) },
        { PERSON, null, "NAME", "John/Smith/", new Name(PERSON, "John/Smith/") },
        { null, null, "PLAC", null, new Place(null, null) },
        { PERSON, null, "PLAC", null, new Place(PERSON, null) },
        { PERSON, null, "PLAC", "Home", new Place(PERSON, "Home") },
        { null, null, "TRLR", null, new Trailer(null, "Trailer") },
        { ROOT, null, "TRLR", null, new Trailer(ROOT, "Trailer") },
        { ROOT, null, "HEAD", "", new Head(ROOT, "Header") },
        { ROOT, null, "HEAD", "", new Head(ROOT, "Header") },
        { ROOT, null, "HEAD", "tail", new Head(ROOT, "Header", "tail") },
        { PERSON, null, "NOTE", "", new Attribute(PERSON, "Note", "") },
        { PERSON, null, "NOTE", "Some text", new Attribute(PERSON, "Note", "Some text") },
        { ROOT, "N1", "NOTE", "Some text", new Note(ROOT, new ObjectId("N1"), "Some text") },
        { PERSON, null, "NOTE", "@N1@", new NoteLink(PERSON, "Note", new ObjectId("N1")) },
        { PERSON, null, "OBJE", null, new Multimedia(PERSON, "Multimedia") },
        { PERSON, null, "OBJE", null, new Multimedia(PERSON, "Multimedia", null) },
        { PERSON, null, "OBJE", "string", new Multimedia(PERSON, "Multimedia", "string") },
        { PERSON, "foo", "OBJE", null, new Multimedia(PERSON, "Multimedia", null) },
        // These 2 are funky
        { NOTE, null, "CONC", "y stuff", null }, { NOTE, null, "CONT", "and some more text", null },
//            {note, null, "CONC", "y stuff,
//                new Attribute(person, "Note", "Some texty stuff")},
//            {note, null, "CONT", "and some more text,
//                new Attribute(person, "Note",
//                    "Some text\nand some more text")},
    };

    /** */
    private GedObjectStarterFactory factory;

    @BeforeEach
    void setUp() {
        factory = new GedObjectStarterFactory();
    }

    static Stream<Arguments> params() {
        PERSON.addAttribute(NOTE);
        return Arrays.stream(PARAMETERS).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testFactory(final GedObject parent, final String xref, final String tag, final String tail,
        final GedObject expected) {
        final GedObject gob = factory.create(parent, xref, tag, tail);
        assertEquals(expected, gob, "Produced object doesn't match expectation");
    }
}
