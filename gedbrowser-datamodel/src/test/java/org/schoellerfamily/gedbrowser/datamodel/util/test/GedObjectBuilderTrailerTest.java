package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class GedObjectBuilderTrailerTest {

    /** */
    @Test
    public void testCreateTrailer() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Trailer trailer = builder.createTrailer();
        assertEquals("Mismatched tag", "Trailer", trailer.getString());
    }

}
