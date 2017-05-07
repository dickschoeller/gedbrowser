package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class ChildConstructorTest {
    /** */
    private final Family parent;
    /** */
    private final String string;
    /** */
    private final ObjectId xref;
    /** */
    private final String expectedString;
    /** */
    private final String expectedToString;
    /** */
    private final String expectedFromString;
    /** */
    private Child child;

    /**
     * @param parent the parent of the link to be created
     * @param string the input string
     * @param xref the input xref
     * @param expectedString the expected output of getString
     * @param expectedToString the expected to string
     * @param expectedFromString the expected from string
     */
    public ChildConstructorTest(final Family parent, final String string,
            final ObjectId xref, final String expectedString,
            final String expectedToString, final String expectedFromString) {
        this.parent = parent;
        this.string = string;
        this.xref = xref;
        this.expectedString = expectedString;
        this.expectedToString = expectedToString;
        this.expectedFromString = expectedFromString;
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");

        return Arrays.asList(new Object[][] {
            {null, null, new ObjectId("I2"), "", "I2", ""},
            {family, null, new ObjectId("I3"), "", "I3", "F1"},
            {null, "", new ObjectId("F1"), "", "F1", ""},
            {family, "", new ObjectId("I2"), "", "I2", "F1"},
            {null, "Link", new ObjectId("I3"), "Link", "I3", ""},
            {family, "Lunk", new ObjectId("F1"), "Lunk", "F1", "F1"},
            {null, null, new ObjectId(""), "", "", ""},
            {family, null, new ObjectId(""), "", "", "F1"},
            {null, "", new ObjectId(""), "", "", ""},
            {family, "", new ObjectId(""), "", "", "F1"},
            {null, "Link", new ObjectId(""), "Link", "", ""},
            {family, "Lunk", new ObjectId(""), "Lunk", "", "F1"},
            {null, null, null, "", "", ""},
            {family, null, null, "", "", "F1"},
            {null, "", null, "", "", ""},
            {family, "", null, "", "", "F1"},
            {null, "Link", null, "Link", "", ""},
            {family, "Lunk", null, "Lunk", "", "F1"},
        });
    }

    /** */
    @Before
    public void init() {
        child = new Child(parent, string, xref);
    }

    /** */
    @Test
    public void testConstructChildToString() {
        assertEquals("To string mismatch", expectedToString,
                child.getToString());
    }

    /** */
    @Test
    public void testConstructChildFromString() {
        assertEquals("From string mismatch", expectedFromString,
                child.getFromString());
    }

    /** */
    @Test
    public void testConstructChildParent() {
        assertEquals("Parent mismatch", parent, child.getParent());
    }

    /** */
    @Test
    public void testConstructChildString() {
        assertEquals("String mismatch", expectedString, child.getString());
    }

}
