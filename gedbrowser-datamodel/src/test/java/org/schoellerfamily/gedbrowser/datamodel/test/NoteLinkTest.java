package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class NoteLinkTest {
    /** */
    private final GedObject parent;
    /** */
    private final String string;
    /** */
    private final ObjectId xref;
    /** */
    private final String expectedString;
    /** */
    private final String expectedFromString;
    /** */
    private final String expectedToString;

    /**
     * @param parent the input parent
     * @param string the input string
     * @param xref the input xref
     * @param expectedString the expected result of getString
     * @param expectedFromString the expected result of getFromString
     * @param expectedToString the expected result of getToString
     */
    public NoteLinkTest(final GedObject parent, final String string,
            final ObjectId xref, final String expectedString,
            final String expectedFromString, final String expectedToString) {
        this.parent = parent;
        this.string = string;
        this.xref = xref;
        this.expectedString = expectedString;
        this.expectedFromString = expectedFromString;
        this.expectedToString = expectedToString;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson(
                "I1", "J. Random/Schoeller/");
        return Arrays.asList(new Object[][] {
            {null, null, null, "", "", ""},
            {person, null, null, "", "I1", ""},
            {null, "", null, "", "", ""},
            {person, "", null, "", "I1", ""},
            {null, "Note", null, "Note", "", ""},
            {person, "Note", null, "Note", "I1", ""},
            {null, null, new ObjectId(""), "", "", ""},
            {person, null, new ObjectId(""), "", "I1", ""},
            {null, "", new ObjectId(""), "", "", ""},
            {person, "", new ObjectId(""), "", "I1", ""},
            {null, "Note", new ObjectId(""), "Note", "", ""},
            {person, "Note", new ObjectId(""), "Note", "I1", ""},
            {null, null, new ObjectId("N1"), "", "", "N1"},
            {person, null, new ObjectId("N1"), "", "I1", "N1"},
            {null, "", new ObjectId("N1"), "", "", "N1"},
            {person, "", new ObjectId("N1"), "", "I1", "N1"},
            {null, "Note", new ObjectId("N1"), "Note", "", "N1"},
            {person, "Note", new ObjectId("N1"), "Note", "I1", "N1"},
        });
    }

    /** */
    @Test
    public void testOneArgumentConstructor() {
        final NoteLink sourceLink = new NoteLink();
        assertMatch(sourceLink, null, "", "", "");
    }

    /** */
    @Test
    public void testThreeArgumentConstructor() {
        final NoteLink sourceLink = new NoteLink(parent, string, xref);
        assertMatch(sourceLink, parent, expectedString, expectedFromString,
                expectedToString);
    }

    /**
     * @param sourceLink the source link to compare
     * @param expParent expected output from getParent
     * @param expString expected output from getString
     * @param expFromString expected output from getFromString
     * @param expToString expected output from getToString
     */
    private void assertMatch(final NoteLink sourceLink,
            final GedObject expParent, final String expString,
            final String expFromString, final String expToString) {
        assertEquals("Parent mismatch", expParent, sourceLink.getParent());
        assertEquals("String mismatch", expString, sourceLink.getString());
        assertEquals("To string mismatch", expToString,
                sourceLink.getToString());
        assertEquals("From string mismatch", expFromString,
                sourceLink.getFromString());
    }
}
