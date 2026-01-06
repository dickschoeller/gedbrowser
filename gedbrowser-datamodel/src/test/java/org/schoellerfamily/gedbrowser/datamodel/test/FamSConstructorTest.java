package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Data driven tests of the permutations of calling the constructors of FamS.
 *
 * @author Dick Schoeller
 */
public final class FamSConstructorTest {

    /**
     * @return stream of arguments for parameterized tests.
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    public static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1", "J. Random/Schoeller/");

        return Arrays.stream(new Object[][] {
            { null, null, new ObjectId("I2"), null, "", "I2", "" },
            { person1, null, new ObjectId("I3"), person1, "", "I3", "I1" },
            { null, "", new ObjectId("F1"), null, "", "F1", "" },
            { person1, "", new ObjectId("I2"), person1, "", "I2", "I1" },
            { null, "FamS", new ObjectId("I3"), null, "FamS", "I3", "" },
            { person1, "Lunk", new ObjectId("F1"), person1, "Lunk", "F1", "I1" },
            { null, null, new ObjectId(""), null, "", "", "" },
            { person1, null, new ObjectId(""), person1, "", "", "I1" },
            { null, "", new ObjectId(""), null, "", "", "" },
            { person1, "", new ObjectId(""), person1, "", "", "I1" },
            { null, "FamS", new ObjectId(""), null, "FamS", "", "" },
            { person1, "Lunk", new ObjectId(""), person1, "Lunk", "", "I1" },
            { null, null, null, null, "", "", "" }, { person1, null, null, person1, "", "", "I1" },
            { null, "", null, null, "", "", "" }, { person1, "", null, person1, "", "", "I1" },
            { null, "FamS", null, null, "FamS", "", "" },
            { person1, "Lunk", null, person1, "Lunk", "", "I1" }, }).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testParent(final Person parent, final String string, final ObjectId tail,
        final Person expectedParent, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final FamS fams = new FamS(parent, string, tail);
        assertEquals(expectedParent, fams.getParent(), "Parent mismatch");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testString(final Person parent, final String string, final ObjectId tail,
        final Person expectedParent, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final FamS fams = new FamS(parent, string, tail);
        assertEquals(expectedString, fams.getString(), "String mismatch");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testToString(final Person parent, final String string, final ObjectId tail,
        final Person expectedParent, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final FamS fams = new FamS(parent, string, tail);
        assertEquals(expectedToString, fams.getToString(), "To string mismtach");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testFromString(final Person parent, final String string, final ObjectId tail,
        final Person expectedParent, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final FamS fams = new FamS(parent, string, tail);
        assertEquals(expectedFromString, fams.getFromString(), "From string mismatch");
    }
}
