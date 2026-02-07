package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

final class SubmitterLinkTest {

    /**
     * @return parameters for the parameterized tests
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1", "J. Random/Schoeller/");
        return Arrays
            .stream(new Object[][] { { null, null, null, "", "", "" },
                { person, null, null, "", "I1", "" }, { null, "", null, "", "", "" },
                { person, "", null, "", "I1", "" }, { null, "Source", null, "Source", "", "" },
                { person, "Source", null, "Source", "I1", "" },
                { null, null, new ObjectId(""), "", "", "" },
                { person, null, new ObjectId(""), "", "I1", "" },
                { null, "", new ObjectId(""), "", "", "" },
                { person, "", new ObjectId(""), "", "I1", "" },
                { null, "Source", new ObjectId(""), "Source", "", "" },
                { person, "Source", new ObjectId(""), "Source", "I1", "" },
                { null, null, new ObjectId("S1"), "", "", "S1" },
                { person, null, new ObjectId("S1"), "", "I1", "S1" },
                { null, "", new ObjectId("S1"), "", "", "S1" },
                { person, "", new ObjectId("S1"), "", "I1", "S1" },
                { null, "Source", new ObjectId("S1"), "Source", "", "S1" },
                { person, "Source", new ObjectId("S1"), "Source", "I1", "S1" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testOneArgumentConstructor(final GedObject parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedFromString,
        final String expectedToString) {
        final SubmitterLink sourceLink = new SubmitterLink();
        assertMatch(sourceLink, null, "", "", "");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testThreeArgumentConstructor(final GedObject parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedFromString,
        final String expectedToString) {
        final SubmitterLink sourceLink = new SubmitterLink(parent, string, xref);
        assertMatch(sourceLink, parent, expectedString, expectedFromString, expectedToString);
    }

    private void assertMatch(final SubmitterLink sourceLink, final GedObject expParent,
        final String expString, final String expFromString, final String expToString) {
        assertEquals(expParent, sourceLink.getParent(), "Parent mismatch");
        assertEquals(expString, sourceLink.getString(), "String mismatch");
        assertEquals(expToString, sourceLink.getToString(), "To string mismatch");
        assertEquals(expFromString, sourceLink.getFromString(), "From string mismatch");
    }
}
