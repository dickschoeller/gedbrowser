package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

public final class PlaceTest {

    /**
     * @return parameters for parameterized tests
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    public static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson("I1", "J. Random/Schoeller/");

        return Arrays
            .stream(new Object[][] { { null, null, null, "" }, { person1, null, person1, "" },
                { null, "", null, "" }, { person1, "", person1, "" },
                { null, "string", null, "string" }, { person1, "string", person1, "string" },
                { null, "string", null, "string" }, { person1, "string", person1, "string" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testOneArgumentConstructor(final GedObject parent, final String string,
        final GedObject expectedParent, final String expectedString) {
        final Place place1 = new Place();
        assertMatch(place1, null, "");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testTwoArgumentConstructor(final GedObject parent, final String string,
        final GedObject expectedParent, final String expectedString) {
        final Place place1 = new Place(parent, string);
        assertMatch(place1, expectedParent, expectedString);
    }

    private void assertMatch(final Place place1, final GedObject expParent,
        final String expString) {
        assertEquals(expParent, place1.getParent(), "Parent mismatch");
        assertEquals(expString, place1.getString(), "String mismatch");
    }
}
