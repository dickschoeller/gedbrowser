package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;


/**
 * Contains tests for note link.
 */

final class NoteLinkTest {

    @SuppressWarnings("checkstyle:nowhitespaceafter")
    static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        final Person person = builder.createPerson("I1", "J. Random/Schoeller/");
        return Arrays.stream(
            new Object[][] { { null, null, null, "", "", "" }, { person, null, null, "", "I1", "" },
                { null, "", null, "", "", "" }, { person, "", null, "", "I1", "" },
                { null, "Note", null, "Note", "", "" }, { person, "Note", null, "Note", "I1", "" },
                { null, null, new ObjectId(""), "", "", "" },
                { person, null, new ObjectId(""), "", "I1", "" },
                { null, "", new ObjectId(""), "", "", "" },
                { person, "", new ObjectId(""), "", "I1", "" },
                { null, "Note", new ObjectId(""), "Note", "", "" },
                { person, "Note", new ObjectId(""), "Note", "I1", "" },
                { null, null, new ObjectId("N1"), "", "", "N1" },
                { person, null, new ObjectId("N1"), "", "I1", "N1" },
                { null, "", new ObjectId("N1"), "", "", "N1" },
                { person, "", new ObjectId("N1"), "", "I1", "N1" },
                { null, "Note", new ObjectId("N1"), "Note", "", "N1" },
                { person, "Note", new ObjectId("N1"), "Note", "I1", "N1" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testOneArgumentConstructor(final GedObject parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedFromString,
        final String expectedToString) {
        final NoteLink sourceLink = new NoteLink();
        assertMatch(sourceLink, null, "", "", "");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testThreeArgumentConstructor(final GedObject parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedFromString,
        final String expectedToString) {
        final NoteLink sourceLink = new NoteLink(parent, string, xref);
        assertMatch(sourceLink, parent, expectedString, expectedFromString, expectedToString);
    }

    private void assertMatch(final NoteLink sourceLink, final GedObject expParent,
        final String expString, final String expFromString, final String expToString) {
        assertEquals(expParent, sourceLink.getParent(), "Parent mismatch");
        assertEquals(expString, sourceLink.getString(), "String mismatch");
        assertEquals(expToString, sourceLink.getToString(), "To string mismatch");
        assertEquals(expFromString, sourceLink.getFromString(), "From string mismatch");
    }
}
