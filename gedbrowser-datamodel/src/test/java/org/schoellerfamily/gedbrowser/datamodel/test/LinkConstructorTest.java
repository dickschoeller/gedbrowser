package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public final class LinkConstructorTest {
    /** */
    private final Person parent;
    /** */
    private final String string;
    /** */
    private final ObjectId xref;
    /** */
    private final String expectedString;
    /** */
    private final String expectedToString;
    /** */
    private final String expectedFromString;

    /**
     * @param parent the parent of the link to be created
     * @param string the input string
     * @param xref the input xref
     * @param expectedString the expected output of getString
     * @param expectedToString the expected to string
     * @param expectedFromString the expected from string
     */
    public LinkConstructorTest(final Person parent, final String string,
            final ObjectId xref, final String expectedString,
            final String expectedToString, final String expectedFromString) {
        this.parent = parent;
        this.string = string;
        this.xref = xref;
        this.expectedString = expectedString;
        this.expectedToString = expectedToString;
        this.expectedFromString = expectedFromString;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();

        return Arrays.asList(new Object[][] {
            {null, null, new ObjectId("I2"), "", "I2", ""},
            {person1, null, new ObjectId("I3"), "", "I3", "I1"},
            {null, "", new ObjectId("F1"), "", "F1", ""},
            {person1, "", new ObjectId("I2"), "", "I2", "I1"},
            {null, "Link", new ObjectId("I3"), "Link", "I3", ""},
            {person1, "Lunk", new ObjectId("F1"), "Lunk", "F1", "I1"},
            {null, null, new ObjectId(""), "", "", ""},
            {person1, null, new ObjectId(""), "", "", "I1"},
            {null, "", new ObjectId(""), "", "", ""},
            {person1, "", new ObjectId(""), "", "", "I1"},
            {null, "Link", new ObjectId(""), "Link", "", ""},
            {person1, "Lunk", new ObjectId(""), "Lunk", "", "I1"},
            {null, null, null, "", "", ""},
            {person1, null, null, "", "", "I1"},
            {null, "", null, "", "", ""},
            {person1, "", null, "", "", "I1"},
            {null, "Link", null, "Link", "", ""},
            {person1, "Lunk", null, "Lunk", "", "I1"},
        });
    }

    /** */
    @Test
    public void testOneArgumentConstructor() {
        final Link link1 = new Link(parent);
        assertMatch(link1, parent, "", "", expectedFromString);
    }

    /** */
    @Test
    public void testTwoArgumentConstructor() {
        final Link link1 = new Link(parent, string);
        assertMatch(link1, parent, expectedString, "", expectedFromString);
    }

    /** */
    @Test
    public void testThreeArgumentConstructor() {
        final Link link1 = new Link(parent, string, xref);
        assertMatch(link1, parent, expectedString, expectedToString,
                expectedFromString);
    }

    /**
     * @param link the link we are checking
     * @param expParent the expected parent
     * @param expString the expected tag string
     * @param expToString the expected to string
     * @param expFromString the expected from string
     */
    private void assertMatch(final Link link, final Person expParent,
            final String expString, final String expToString,
            final String expFromString) {
        assertEquals("Parent mismatch", expParent, link.getParent());
        assertEquals("String mismatch", expString, link.getString());
        assertEquals("To string mismatch", expToString,
                link.getToString());
        assertEquals("From string mismatch", expFromString,
                link.getFromString());
    }
}
