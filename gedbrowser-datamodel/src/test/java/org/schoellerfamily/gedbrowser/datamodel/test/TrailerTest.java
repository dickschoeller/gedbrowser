package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

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
    @Test
    public void testTrailerGedObject() {
        final Root root = new Root("Root");

        final Trailer trailer = new Trailer(root, "Trailer");
        root.insert(trailer);

        final GedObject gob = root.find("Trailer");
        assertEquals("expected trailer", trailer, gob);
    }

    /** */
    @Test
    public void testTrailerGedFoundObjectString() {
        final Root root = new Root("Root");
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Trailer trailer = builder.createTrailer();

        final GedObject gob = root.find("Trailer");
        assertEquals("expected trailer", trailer, gob);
    }
    /** */
    @Test
    public void testTrailerGedObjectString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Trailer trailer = builder.createTrailer();
        assertEquals("expected trailer tag", "Trailer", trailer.getString());
    }
}
