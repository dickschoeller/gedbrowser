package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
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
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
@SuppressWarnings({ "PMD.CouplingBetweenObjects", "PMD.ExcessiveImports",
        "PMD.CommentSize" })
public class GedObjectBuilderEventByNameTest {
    /** */
    private static GedObjectBuilder builder = new GedObjectBuilder();
    /** */
    private static Root root = builder.getRoot();
    /** */
    private static Person person = builder.createPerson("I99999", "Name/Me/");
    /** */
    private final GedObject parent;
    /** */
    private final String type;
    /** */
    private final String string;
    /** */
    private final String tail;
    /** */
    private final Class<? extends GedObject> clazz;
    /** */
    private final String expectedString;
    /** */
    private final String expectedToString;

    /** */
    private static final Object[][] PARAMETERS = {
            {person, null, null, null, Attribute.class, "", null},
            {person, "", null, null, Attribute.class, "", null},
            {person, "", "", null, Attribute.class, "", null},
            {person, "", "", "", Attribute.class, "", null},
            {person, "attribute", "Birth", "", Attribute.class, "Birth", null},
            {person, "child", "", "", Child.class, "Child", ""},
            {person, "famc", "", "", FamC.class, "Child of Family", ""},
            {person, "fams", "", "", FamS.class, "Spouse of Family", ""},
            {person, "husband", "", "", Husband.class, "Husband", ""},
            {person, "wife", "", "", Wife.class, "Wife", ""},
            {person, "child", "I1", "", Child.class, "Child", "I1"},
            {person, "famc", "F1", "", FamC.class, "Child of Family", "F1"},
            {person, "fams", "F1", "", FamS.class, "Spouse of Family", "F1"},
            {person, "husband", "I1", "", Husband.class, "Husband", "I1"},
            {person, "wife", "I1", "", Wife.class, "Wife", "I1"},
            {person, "date", "12 DEC 1958", "",
                Date.class, "12 DEC 1958", null},
            {person, "date", "", "", Date.class, "", null},
            {person, "place", "Home", "", Place.class, "Home", null},
            {person, "place", "", "", Place.class, "", null},
            {person, "name", "Richard/Schoeller/", "",
                Name.class, "Richard/Schoeller/", null},
            {person, "name", "", "", Name.class, "", null},
            {root, "family", "F1", "", Family.class, "F1", null},
            {root, "person", "I1", "", Person.class, "I1", null},
            {root, "source", "S1", "", Source.class, "S1", null},
            {root, "submitter", "SUB1", "", Submitter.class, "SUB1", null},
            {root, "submission", "SUBN", "", Submission.class, "SUBN", null},
            {root, "head", "", "", Head.class, "Header", null},
            {root, "header", "", "", Head.class, "Header", null},
            {root, "trailer", "", "", Trailer.class, "Trailer", null},
            {person, "sourcelink", "S1", "", SourceLink.class, "Source", "S1"},
            {person, "submitterlink", "SUB1", "",
                SubmitterLink.class, "Submitter", "SUB1"},
            {person, "submissionlink", "SUBN", "",
                SubmissionLink.class, "Submission", "SUBN"},
    };

//        CONSTRUCTION_MAP.put("link", Construction.reference);
//        CONSTRUCTION_MAP.put("multimedia", Construction.value);
//        CONSTRUCTION_MAP.put("note", Construction.note);

    /**
     * @param parent the parent of the object we are creating
     * @param type the name of the type of event to build
     * @param string a primary piece of string data
     * @param tail additional string
     * @param clazz expected class
     * @param expectedString the expected output of getString
     * @param expectedToString the expected output of getToString
     */
    public GedObjectBuilderEventByNameTest(
            final GedObject parent,
            final String type,
            final String string, final String tail,
            final Class<? extends GedObject> clazz,
            final String expectedString,
            final String expectedToString) {
        this.parent = parent;
        this.type = type;
        this.string = string;
        this.tail = tail;
        this.clazz = clazz;
        this.expectedString = expectedString;
        this.expectedToString = expectedToString;
    }

    /**
     * @return the array of tests values
     */
    @Parameters
    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public static Object[][] params() {
        return PARAMETERS;
    }

    /** */
    @BeforeClass
    public static void init() {
        builder = new GedObjectBuilder();
        root = builder.getRoot();
        person = builder.createPerson(null, "Name/Me/");
    }

    /** */
    @Test
    public void testClass() {
        final GedObject gob = builder.createEvent(parent, type, string, tail);
        assertEquals("wrong class", clazz, gob.getClass());
    }

    /** */
    @Test
    public void testGetString() {
        final GedObject gob = builder.createEvent(parent, type, string, tail);
        assertEquals("getString mismatch", expectedString, gob.getString());
    }

    /** */
    @Test
    public void testGetToString() {
        final GedObject gob = builder.createEvent(parent, type, string, tail);
        assertToString(gob);
    }

    /**
     * Assert that the value of toString matches expectations.
     *
     * @param gob the input object
     */
    private void assertToString(final GedObject gob) {
        if (gob instanceof AbstractLink) {
            assertEquals("toString mismatch", expectedToString,
                    ((AbstractLink) gob).getToString());
        } else {
            assertNull("There is no toString, expectation better be null",
                    expectedToString);
        }
    }
}
