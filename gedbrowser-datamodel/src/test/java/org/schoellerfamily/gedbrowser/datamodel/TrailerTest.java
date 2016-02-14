package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class TrailerTest {
    /** */
    private static final String TRAILER_TAG = "Trailer";

    /** */
    @Test
    public void testTrailerGedObject() {
        final Root root = new Root(null, "Root");

        final Trailer trailer = new Trailer(root);
        root.insert(TRAILER_TAG, trailer);

        final GedObject gob = root.find(TRAILER_TAG);
        assertEquals("expected trailer", trailer, gob);

        assertTrue("Trailer string should be empty", trailer.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testTrailerGedObjectString() {
        final Root root = new Root(null, "Root");

        final Trailer trailer = new Trailer(root, TRAILER_TAG);
        root.insert(TRAILER_TAG, trailer);

        final GedObject gob = root.find(TRAILER_TAG);
        assertEquals("expected trailer", trailer, gob);

        assertEquals("expected trailer tag", TRAILER_TAG, trailer.getString());
    }
}
