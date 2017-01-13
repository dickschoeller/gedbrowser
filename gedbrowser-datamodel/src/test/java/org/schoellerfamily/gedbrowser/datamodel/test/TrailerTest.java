package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

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
    }

    /** */
    @Test
    public void testTrailerEmpty() {
        final Root root = new Root(null, "Root");

        final Trailer trailer = new Trailer(root);
        root.insert(TRAILER_TAG, trailer);

        assertTrue("Trailer string should be empty", trailer.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testTrailerGedFoundObjectString() {
        final Root root = new Root(null, "Root");
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Trailer trailer = builder.createTrailer();

        final GedObject gob = root.find(TRAILER_TAG);
        assertEquals("expected trailer", trailer, gob);
    }
    /** */
    @Test
    public void testTrailerGedObjectString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Trailer trailer = builder.createTrailer();
        assertEquals("expected trailer tag", TRAILER_TAG, trailer.getString());
    }
}
