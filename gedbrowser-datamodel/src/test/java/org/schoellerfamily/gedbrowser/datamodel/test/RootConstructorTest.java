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
    public static Stream<Arguments> params() {
        return Arrays.stream(new Object[][] {
            {null, ""},
            {"", ""},
            {"Root", "Root"},
            {"ROOT", "ROOT"},
        }).map(Arguments::of);
    }

    /** */
    @ParameterizedTest
    @MethodSource("params")
    public void testRootGedObject(final String string, final String expectedString) {
        final Root root = new Root();
        assertMatch(root, null, "");
    }

    /** */
    @ParameterizedTest
    @MethodSource("params")
    public void testTwoArgumentConstructor(final String string, final String expectedString) {
        final Root root = new Root(string);
        assertMatch(root, null, expectedString);
    }

    /**
     * @param root the root object to check
     * @param expParent the expected parent
     * @param expString the expected string
     */
    private void assertMatch(final Root root, final GedObject expParent,
            final String expString) {
        assertEquals(expParent, root.getParent(), "Parent mismatch");
        assertEquals(expString, root.getString(), "String mismatch");
    }
}