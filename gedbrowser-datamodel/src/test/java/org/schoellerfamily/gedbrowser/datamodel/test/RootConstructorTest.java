package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class RootConstructorTest {
    /**
     * @return collection of parameter arrays
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    public static Stream<Arguments> params() {
        return Arrays.stream(
            new Object[][] { { null, "" }, { "", "" }, { "Root", "Root" }, { "ROOT", "ROOT" }, })
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testRootGedObject(final String string, final String expectedString) {
        final Root root = new Root();
        assertMatch(root, null, "");
    }

    @ParameterizedTest
    @MethodSource("params")
    void testTwoArgumentConstructor(final String string, final String expectedString) {
        final Root root = new Root(string);
        assertMatch(root, null, expectedString);
    }

    private void assertMatch(final Root root, final GedObject expParent, final String expString) {
        assertEquals(expParent, root.getParent(), "Parent mismatch");
        assertEquals(expString, root.getString(), "String mismatch");
    }
}
