package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;


/**
 * Contains tests for link constructor.
 */

final class LinkConstructorTest {

    @SuppressWarnings("checkstyle:nowhitespaceafter")
    static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1", "J. Random/Schoeller/");

        return Arrays.stream(new Object[][] { { null, null, new ObjectId("I2"), "", "I2", "" },
            { person1, null, new ObjectId("I3"), "", "I3", "I1" },
            { null, "", new ObjectId("F1"), "", "F1", "" },
            { person1, "", new ObjectId("I2"), "", "I2", "I1" },
            { null, "Link", new ObjectId("I3"), "Link", "I3", "" },
            { person1, "Lunk", new ObjectId("F1"), "Lunk", "F1", "I1" },
            { null, null, new ObjectId(""), "", "", "" },
            { person1, null, new ObjectId(""), "", "", "I1" },
            { null, "", new ObjectId(""), "", "", "" },
            { person1, "", new ObjectId(""), "", "", "I1" },
            { null, "Link", new ObjectId(""), "Link", "", "" },
            { person1, "Lunk", new ObjectId(""), "Lunk", "", "I1" },
            { null, null, null, "", "", "" }, { person1, null, null, "", "", "I1" },
            { null, "", null, "", "", "" }, { person1, "", null, "", "", "I1" },
            { null, "Link", null, "Link", "", "" }, { person1, "Lunk", null, "Lunk", "", "I1" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testOneArgumentConstructor(final Person parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Link link1 = new Link(parent);
        assertMatch(link1, parent, "", "", expectedFromString);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testTwoArgumentConstructor(final Person parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Link link1 = new Link(parent, string);
        assertMatch(link1, parent, expectedString, "", expectedFromString);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testThreeArgumentConstructor(final Person parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Link link1 = new Link(parent, string, xref);
        assertMatch(link1, parent, expectedString, expectedToString, expectedFromString);
    }

    private void assertMatch(final Link link, final Person expParent, final String expString,
        final String expToString, final String expFromString) {
        assertEquals(expParent, link.getParent(), "Parent mismatch");
        assertEquals(expString, link.getString(), "String mismatch");
        assertEquals(expToString, link.getToString(), "To string mismatch");
        assertEquals(expFromString, link.getFromString(), "From string mismatch");
    }
}
