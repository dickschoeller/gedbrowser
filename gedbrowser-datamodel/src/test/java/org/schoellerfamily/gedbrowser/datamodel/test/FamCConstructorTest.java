package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Data driven tests of the permutations of calling the constructors
 * of FamC.
 *
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
@SuppressWarnings("PMD.CommentSize")
public final class FamCConstructorTest {
    /** */
    private final Person parent;
    /** */
    private final String string;
    /** */
    private final ObjectId tail;
    /** */
    private final Person expectedParent;
    /** */
    private final String expectedString;
    /** */
    private final String expectedToString;
    /** */
    private final String expectedFromString;

    /**
     * @param parent input parent value for constructor call
     * @param string input string value for constructor call
     * @param tail input tail value for constructor call
     * @param expectedParent expected output parent from getter
     * @param expectedString expected output string from getter
     * @param expectedToString expected to string
     * @param expectedFromString expected from string
     */
    public FamCConstructorTest(final Person parent,
            final String string, final ObjectId tail,
            final Person expectedParent,
            final String expectedString, final String expectedToString,
            final String expectedFromString) {
                this.parent = parent;
                this.string = string;
                this.tail = tail;
                this.expectedParent = expectedParent;
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
            {null, null, new ObjectId("I2"), null, "", "I2", ""},
            {person1, null, new ObjectId("I3"), person1, "",  "I3", "I1"},
            {null, "", new ObjectId("F1"), null, "", "F1", ""},
            {person1, "", new ObjectId("I2"), person1, "", "I2", "I1"},
            {null, "FamC", new ObjectId("I3"), null, "FamC", "I3", ""},
            {person1, "Lunk", new ObjectId("F1"), person1, "Lunk", "F1", "I1"},
            {null, null, new ObjectId(""), null, "", "", ""},
            {person1, null, new ObjectId(""), person1, "", "", "I1"},
            {null, "", new ObjectId(""), null, "", "", ""},
            {person1, "", new ObjectId(""), person1, "", "", "I1"},
            {null, "FamC", new ObjectId(""), null, "FamC", "", ""},
            {person1, "Lunk", new ObjectId(""), person1, "Lunk", "", "I1"},
            {null, null, null, null, "", "", ""},
            {person1, null, null, person1, "", "", "I1"},
            {null, "", null, null, "", "", ""},
            {person1, "", null, person1, "", "", "I1"},
            {null, "FamC", null, null, "FamC", "", ""},
            {person1, "Lunk", null, person1, "Lunk", "", "I1"},
        });
    }

    /** */
    @Test
    public void testTwoArgumentConstructor() {
        final FamC famc = new FamC(parent, string);
        assertMatch(famc, expectedParent, expectedString, "",
                expectedFromString);
    }

    /** */
    @Test
    public void testThreeArgumentConstructor() {
        final FamC famc = new FamC(parent, string, tail);
        assertMatch(famc, expectedParent, expectedString, expectedToString,
                expectedFromString);
    }

    /**
     * @param famc the FamC to check
     * @param expParent the parent
     * @param expString the string
     * @param expToString the to string
     * @param expFromString the from string
     */
    private void assertMatch(final FamC famc, final Person expParent,
            final String expString,
            final String expToString,
            final String expFromString) {
        assertEquals("Parent mismatch", expParent, famc.getParent());
        assertEquals("String mismatch", expString, famc.getString());
        assertEquals("To string mismtach", expToString,
                famc.getToString());
        assertEquals("From string mismatch", expFromString,
                famc.getFromString());
    }

}
