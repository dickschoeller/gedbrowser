package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

public final class MultimediaConstructorTest {

    /**
     * @return parameters for parameterized tests
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    public static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1", "J. Random/Schoeller/");

        return Arrays.stream(new Object[][] { { null, null, null, null, "", "" },
            { person1, null, null, person1, "", "" }, { null, "", null, null, "", "" },
            { person1, "", null, person1, "", "" }, { null, "string", null, null, "string", "" },
            { person1, "string", null, person1, "string", "" }, { null, null, "", null, "", "" },
            { person1, null, "", person1, "", "" }, { null, "", "", null, "", "" },
            { person1, "", "", person1, "", "" }, { null, "string", "", null, "string", "" },
            { person1, "string", "", person1, "string", "" },
            { null, null, "strung", null, "", "strung" },
            { person1, null, "strung", person1, "", "strung" },
            { null, "", "strung", null, "", "strung" },
            { person1, "", "strung", person1, "", "strung" },
            { null, "string", "strung", null, "string", "strung" },
            { person1, "string", "strung", person1, "string", "strung" }, }).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testThreeArgumentConstructor(final GedObject parent, final String string,
        final String tail, final GedObject expectedParent, final String expectedString,
        final String expectedTail) {
        final Multimedia attribute = new Multimedia(parent, string, tail);
        assertMatch(attribute, expectedParent, expectedString, expectedTail);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testTwoArgumentConstructor(final GedObject parent, final String string,
        final String tail, final GedObject expectedParent, final String expectedString,
        final String expectedTail) {
        final Multimedia attribute = new Multimedia(parent, string);
        assertMatch(attribute, expectedParent, expectedString, "");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testOneArgumentConstructor(final GedObject parent, final String string,
        final String tail, final GedObject expectedParent, final String expectedString,
        final String expectedTail) {
        final Multimedia attribute = new Multimedia();
        assertMatch(attribute, null, "", "");
    }

    private void assertMatch(final Multimedia multimedia, final GedObject expParent,
        final String expString, final String expTail) {
        assertEquals(expParent, multimedia.getParent(), "Parent mismatch");
        assertEquals(expString, multimedia.getString(), "String mismatch");
        assertEquals(expTail, multimedia.getTail(), "Tail mismatch");
    }
}
