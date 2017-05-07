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
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public final class PlaceTest {
    /** */
    private final GedObject parent;
    /** */
    private final String string;
    /** */
    private final GedObject expectedParent;
    /** */
    private final String expectedString;

    /**
     * @param parent input parent value for constructor call
     * @param string input string value for constructor call
     * @param expectedParent expected output parent from getter
     * @param expectedString expected output string from getter
     */
    public PlaceTest(final GedObject parent, final String string,
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
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person1 = builder.createPerson(
                "I1", "J. Random/Schoeller/");

        return Arrays.asList(new Object[][] {
            {null, null, null, ""},
            {person1, null, person1, ""},
            {null, "", null, ""},
            {person1, "", person1, ""},
            {null, "string", null, "string"},
            {person1, "string", person1, "string"},
            {null, "string", null, "string"},
            {person1, "string", person1, "string"},
        });
    }

    /** */
    @Test
    public void testOneArgumentConstructor() {
        final Place place1 = new Place();
        assertMatch(place1, null, "");
    }

    /** */
    @Test
    public void testTwoArgumentConstructor() {
        final Place place1 = new Place(parent, string);
        assertMatch(place1, expectedParent, expectedString);
    }

    /**
     * @param place1 the place to test
     * @param expParent the expected parent from the getter
     * @param expString the expected string from the getter
     */
    private void assertMatch(final Place place1, final GedObject expParent,
            final String expString) {
        assertEquals("Parent mismatch", expParent, place1.getParent());
        assertEquals("String mismatch", expString, place1.getString());
    }
}
