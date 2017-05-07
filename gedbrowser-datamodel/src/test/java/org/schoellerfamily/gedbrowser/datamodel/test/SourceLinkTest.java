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
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public final class SourceLinkTest {
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
    public SourceLinkTest(final GedObject parent, final String string,
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
            {null, "Source", null, "Source", "", ""},
            {person, "Source", null, "Source", "I1", ""},
            {null, null, new ObjectId(""), "", "", ""},
            {person, null, new ObjectId(""), "", "I1", ""},
            {null, "", new ObjectId(""), "", "", ""},
            {person, "", new ObjectId(""), "", "I1", ""},
            {null, "Source", new ObjectId(""), "Source", "", ""},
            {person, "Source", new ObjectId(""), "Source", "I1", ""},
            {null, null, new ObjectId("S1"), "", "", "S1"},
            {person, null, new ObjectId("S1"), "", "I1", "S1"},
            {null, "", new ObjectId("S1"), "", "", "S1"},
            {person, "", new ObjectId("S1"), "", "I1", "S1"},
            {null, "Source", new ObjectId("S1"), "Source", "", "S1"},
            {person, "Source", new ObjectId("S1"), "Source", "I1", "S1"},
        });
    }

    /** */
    @Test
    public void testOneArgumentConstructor() {
        final SourceLink sourceLink = new SourceLink();
        assertMatch(sourceLink, null, "", "", "");
    }

    /** */
    @Test
    public void testThreeArgumentConstructor() {
        final SourceLink sourceLink = new SourceLink(parent, string, xref);
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
    private void assertMatch(final SourceLink sourceLink,
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
