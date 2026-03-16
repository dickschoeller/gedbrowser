package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;



/**
 * Contains tests for ged object builder event by name.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings({ "PMD.CouplingBetweenObjects", "PMD.ExcessiveImports" })
final class GedObjectBuilderEventByNameTest {
    private static GedObjectBuilder builder = new GedObjectBuilder();
    @SuppressWarnings("PMD.SingularField")
    private static Root root = builder.getRoot();
    @SuppressWarnings("PMD.SingularField")
    private static Person person = builder.createPerson("I99999", "Name/Me/");

    /** */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    private static final Object[][] PARAMETERS = {
        { person, null, null, null, Attribute.class, "", null },
        { person, "", null, null, Attribute.class, "", null },
        { person, "", "", null, Attribute.class, "", null },
        { person, "", "", "", Attribute.class, "", null },
        { person, "attribute", "Birth", "", Attribute.class, "Birth", null },
        { person, "child", "", "", Child.class, "Child", "" },
        { person, "famc", "", "", FamC.class, "Child of Family", "" },
        { person, "fams", "", "", FamS.class, "Spouse of Family", "" },
        { person, "husband", "", "", Husband.class, "Husband", "" },
        { person, "wife", "", "", Wife.class, "Wife", "" },
        { person, "child", "I1", "", Child.class, "Child", "I1" },
        { person, "famc", "F1", "", FamC.class, "Child of Family", "F1" },
        { person, "fams", "F1", "", FamS.class, "Spouse of Family", "F1" },
        { person, "husband", "I1", "", Husband.class, "Husband", "I1" },
        { person, "wife", "I1", "", Wife.class, "Wife", "I1" },
        { person, "date", "12 DEC 1958", "", Date.class, "12 DEC 1958", null },
        { person, "date", "", "", Date.class, "", null },
        { person, "place", "Home", "", Place.class, "Home", null },
        { person, "place", "", "", Place.class, "", null },
        { person, "name", "Richard/Schoeller/", "", Name.class, "Richard/Schoeller/", null },
        { person, "name", "", "", Name.class, "", null },
        { root, "family", "F1", "", Family.class, "F1", null },
        { root, "person", "I1", "", Person.class, "I1", null },
        { root, "source", "S1", "", Source.class, "S1", null },
        { root, "submitter", "SUB1", "", Submitter.class, "SUB1", null },
        { root, "submission", "SUBN", "", Submission.class, "SUBN", null },
        { root, "head", "", "", Head.class, "Header", null },
        { root, "header", "", "", Head.class, "Header", null },
        { root, "trailer", "", "", Trailer.class, "Trailer", null },
        { person, "sourcelink", "S1", "", SourceLink.class, "Source", "S1" },
        { person, "submitterlink", "SUB1", "", SubmitterLink.class, "Submitter", "SUB1" },
        { person, "submissionlink", "SUBN", "", SubmissionLink.class, "Submission", "SUBN" }, };

    private static Stream<Arguments> params() {
        return Arrays.stream(PARAMETERS).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testClass(final GedObject parent, final String type, final String string,
        final String tail, final Class<? extends GedObject> clazz, final String expectedString,
        final String expectedToString) {
        final GedObject gob = builder.createEvent(parent, type, string, tail);
        assertEquals(clazz, gob.getClass(), "wrong class");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testGetString(final GedObject parent, final String type, final String string,
        final String tail, final Class<? extends GedObject> clazz, final String expectedString,
        final String expectedToString) {
        final GedObject gob = builder.createEvent(parent, type, string, tail);
        assertEquals(expectedString, gob.getString(), "getString mismatch");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testGetToString(final GedObject parent, final String type, final String string,
        final String tail, final Class<? extends GedObject> clazz, final String expectedString,
        final String expectedToString) {
        final GedObject gob = builder.createEvent(parent, type, string, tail);
        assertToString(gob, expectedToString);
    }

    private void assertToString(final GedObject gob, final String expectedToString) {
        if (gob instanceof AbstractLink) {
            assertEquals(expectedToString, ((AbstractLink) gob).getToString(), "toString mismatch");
        } else {
            assertNull(expectedToString, "There is no toString, expectation better be null");
        }
    }
}
