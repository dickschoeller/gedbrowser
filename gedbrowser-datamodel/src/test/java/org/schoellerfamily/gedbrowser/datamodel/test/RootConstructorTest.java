package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public final class RootConstructorTest {
    /** */
    private final String string;
    /** */
    private final String expectedString;

    /**
     * @param string the input string
     * @param expectedString the expected result of getString
     */
    public RootConstructorTest(final String string,
            final String expectedString) {
        this.string = string;
        this.expectedString = expectedString;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][] {
            {null, ""},
            {"", ""},
            {"Root", "Root"},
            {"ROOT", "ROOT"},
        });
    }

    /** */
    @Test
    public void testRootGedObject() {
        final Root root = new Root();
        assertMatch(root, null, "");
    }

    /** */
    @Test
    public void testTwoArgumentConstructor() {
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
        assertEquals("Parent mismatch", expParent, root.getParent());
        assertEquals("String mismatch", expString, root.getString());
    }
}
