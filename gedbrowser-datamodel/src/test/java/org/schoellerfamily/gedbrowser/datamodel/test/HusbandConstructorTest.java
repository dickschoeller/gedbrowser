package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

final class HusbandConstructorTest {
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");

        return Arrays.stream(new Object[][] { { null, null, new ObjectId("I2"), "", "I2", "" },
            { family, null, new ObjectId("I3"), "", "I3", "F1" },
            { null, "", new ObjectId("F1"), "", "F1", "" },
            { family, "", new ObjectId("I2"), "", "I2", "F1" },
            { null, "Link", new ObjectId("I3"), "Link", "I3", "" },
            { family, "Lunk", new ObjectId("F1"), "Lunk", "F1", "F1" },
            { null, null, new ObjectId(""), "", "", "" },
            { family, null, new ObjectId(""), "", "", "F1" },
            { null, "", new ObjectId(""), "", "", "" },
            { family, "", new ObjectId(""), "", "", "F1" },
            { null, "Link", new ObjectId(""), "Link", "", "" },
            { family, "Lunk", new ObjectId(""), "Lunk", "", "F1" },
            { null, null, null, "", "", "" }, { family, null, null, "", "", "F1" },
            { null, "", null, "", "", "" }, { family, "", null, "", "", "F1" },
            { null, "Link", null, "Link", "", "" }, { family, "Lunk", null, "Lunk", "", "F1" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testConstructHusbandToString(final Family parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Husband husband = new Husband(parent, string, xref);
        assertEquals(expectedToString, husband.getToString(), "To string mismatch");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testConstructHusbandFromString(final Family parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Husband husband = new Husband(parent, string, xref);
        assertEquals(expectedFromString, husband.getFromString(), "From string mismatch");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testConstructHusbandParent(final Family parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Husband husband = new Husband(parent, string, xref);
        assertEquals(parent, husband.getParent(), "Parent mismatch");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testConstructHusbandString(final Family parent, final String string,
        final ObjectId xref, final String expectedString, final String expectedToString,
        final String expectedFromString) {
        final Husband husband = new Husband(parent, string, xref);
        assertEquals(expectedString, husband.getString(), "String mismatch");
    }
}
