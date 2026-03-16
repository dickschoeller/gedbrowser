package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
final class TrailerTest {
    @Test
    void testTrailerGedObject() {
        final Root root = new Root("Root");

        final Trailer trailer = new Trailer(root, "Trailer");
        root.insert(trailer);

        final GedObject gob = root.find("Trailer");
        assertEquals(trailer, gob, "expected trailer");
    }

    @Test
    void testTrailerGedFoundObjectString() {
        final Root root = new Root("Root");
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Trailer trailer = builder.createTrailer();

        final GedObject gob = root.find("Trailer");
        assertEquals(trailer, gob, "expected trailer");
    }
    @Test
    void testTrailerGedObjectString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Trailer trailer = builder.createTrailer();
        assertEquals("Trailer", trailer.getString(), "Mismatched tag");
    }
}
