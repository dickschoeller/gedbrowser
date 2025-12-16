package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
public final class AttributeConstructorTest {

    /**
     * @return stream of argument arrays
     */
    public static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson(
                "I1", "J. Random/Schoeller/");

        return Arrays.stream(new Object[][] {
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
        }).map(Arguments::of);
    }

    /** */
    @ParameterizedTest
    @MethodSource("params")
    public void testThreeArgumentConstructor(final GedObject parent,
            final String string, final String tail,
            final GedObject expectedParent, final String expectedString,
            final String expectedTail) {
        final Attribute attribute = new Attribute(parent, string, tail);
        assertMatch(attribute, expectedParent, expectedString, expectedTail);
    }

    /** */
    @ParameterizedTest
    @MethodSource("params")
    public void testTwoArgumentConstructor(final GedObject parent,
            final String string, final String tail,
            final GedObject expectedParent, final String expectedString,
            final String expectedTail) {
        final Attribute attribute = new Attribute(parent, string);
        assertMatch(attribute, expectedParent, expectedString, "");
    }

    /** */
    @ParameterizedTest
    @MethodSource("params")
    public void testOneArgumentConstructor(final GedObject parent,
            final String string, final String tail,
            final GedObject expectedParent, final String expectedString,
            final String expectedTail) {
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
        assertEquals(expParent, attribute.getParent(), "Parent mismatch");
        assertEquals(expString, attribute.getString(), "String mismatch");
        assertEquals(expTail, attribute.getTail(), "Tail mismatch");
    }
}