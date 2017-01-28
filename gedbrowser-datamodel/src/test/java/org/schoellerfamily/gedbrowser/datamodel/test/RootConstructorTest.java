package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class RootConstructorTest {
    /** */
    private final GedObject parent;
    /** */
    private final String string;
    /** */
    private final GedObject expectedParent;
    /** */
    private final String expectedString;

    /**
     * @param parent the input parent
     * @param string the input string
     * @param expectedParent the expected result of getParent
     * @param expectedString the expected result of getString
     */
    public RootConstructorTest(final GedObject parent, final String string,
            final GedObject expectedParent, final String expectedString) {
        this.parent = parent;
        this.string = string;
        this.expectedParent = expectedParent;
        this.expectedString = expectedString;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final GedObject gob = new Person();
        return Arrays.asList(new Object[][] {
            {null, null, null, ""},
            {gob, null, gob, ""},
            {null, "", null, ""},
            {gob, "", gob, ""},
            {null, "Root", null, "Root"},
            {gob, "ROOT", gob, "ROOT"},
        });
    }

    /** */
    @Test
    public void testRootGedObject() {
        final Root root = new Root(parent);
        assertMatch(root, expectedParent, "");
    }

    /** */
    @Test
    public void testTwoArgumentConstructor() {
        final Root root = new Root(parent, string);
        assertMatch(root, expectedParent, expectedString);
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
