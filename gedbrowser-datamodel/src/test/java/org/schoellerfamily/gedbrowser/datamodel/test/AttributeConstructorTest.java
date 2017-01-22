package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Data driven tests of the permutations of calling the constructors
 * of Attribute.
 *
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class AttributeConstructorTest {
    /** */
    private final GedObject parent;
    /** */
    private final String string;
    /** */
    private final String tail;
    /** */
    private final GedObject expectedParent;
    /** */
    private final String expectedString;
    /** */
    private final String expectedTail;

    /**
     * @param parent input parent value for constructor call
     * @param string input string value for constructor call
     * @param tail input tail value for constructor call
     * @param expectedParent expected output parent from getter
     * @param expectedString expected output string from getter
     * @param expectedTail expected output tail from getter
     */
    public AttributeConstructorTest(final GedObject parent,
            final String string, final String tail,
            final GedObject expectedParent,
            final String expectedString, final String expectedTail) {
                this.parent = parent;
                this.string = string;
                this.tail = tail;
                this.expectedParent = expectedParent;
                this.expectedString = expectedString;
                this.expectedTail = expectedTail;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson1();

        return Arrays.asList(new Object[][] {
            {null, null, null, null, "", ""},
            {person1, null, null, person1, "", ""},
            {null, "", null, null, "", ""},
            {person1, "", null, person1, "", ""},
            {null, "string", null, null, "string", ""},
            {person1, "string", null, person1, "string", ""},
            {null, null, "", null, "", ""},
            {person1, null, "", person1, "", ""},
            {null, "", "", null, "", ""},
            {person1, "", "", person1, "", ""},
            {null, "string", "", null, "string", ""},
            {person1, "string", "", person1, "string", ""},
            {null, null, "strung", null, "", "strung"},
            {person1, null, "strung", person1, "", "strung"},
            {null, "", "strung", null, "", "strung"},
            {person1, "", "strung", person1, "", "strung"},
            {null, "string", "strung", null, "string", "strung"},
            {person1, "string", "strung", person1, "string", "strung"},
        });
    }

    /** */
    @Test
    public void testThreeArgumentConstructor() {
        final Attribute attribute = new Attribute(parent, string, tail);
        assertMatch(attribute, expectedParent, expectedString, expectedTail);
    }

    /** */
    @Test
    public void testTwoArgumentConstructor() {
        final Attribute attribute = new Attribute(parent, string);
        assertMatch(attribute, expectedParent, expectedString, "");
    }

    /** */
    @Test
    public void testOneArgumentConstructor() {
        final Attribute attribute = new Attribute(parent);
        assertMatch(attribute, expectedParent, "", "");
    }

    /**
     * Fails an assertion if one of the values doesn't match.
     *
     * @param attribute the attribute to test
     * @param expParent expected parent value
     * @param expString expected string value
     * @param expTail expected tail value
     */
    private void assertMatch(final Attribute attribute,
            final GedObject expParent, final String expString,
            final String expTail) {
        assertEquals("Parent mismatch", expParent, attribute.getParent());
        assertEquals("String mismatch", expString, attribute.getString());
        assertEquals("Tail mismatch", expTail, attribute.getTail());
    }
}
