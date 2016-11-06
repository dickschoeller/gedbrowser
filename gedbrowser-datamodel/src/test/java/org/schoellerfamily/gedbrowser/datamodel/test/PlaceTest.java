package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class PlaceTest {
    /** */
    private final transient Root root = new Root(null, "Root");

    /** */
    @Test
    public void testPlaceGedObject() {
        final Place place1 = new Place(null);
        assertNull(place1.getParent());
        assertEquals("", place1.getString());
        final Place place2 = new Place(root);
        assertEquals(root, place2.getParent());
        assertEquals("", place2.getString());
    }

    /** */
    @Test
    public void testPlaceGedObjectString() {
        final Place place1 = new Place(null, null);
        assertNull(place1.getParent());
        assertEquals("", place1.getString());
        final Place place2 = new Place(root, null);
        assertEquals(root, place2.getParent());
        assertEquals("", place2.getString());

        final Place place3 = new Place(null, "");
        assertNull(place3.getParent());
        assertEquals("", place3.getString());
        final Place place4 = new Place(root, "");
        assertEquals(root, place4.getParent());
        assertEquals("", place4.getString());

        final Place place5 = new Place(null, "Here");
        assertNull(place5.getParent());
        assertEquals("Here", place5.getString());
        final Place place6 = new Place(root, "There");
        assertEquals(root, place6.getParent());
        assertEquals("There", place6.getString());
    }
}
