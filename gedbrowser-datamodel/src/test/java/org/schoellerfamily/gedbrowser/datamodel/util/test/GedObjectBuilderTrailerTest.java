package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
final class GedObjectBuilderTrailerTest {

    /** */
    @Test
    void testCreateTrailer() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Trailer trailer = builder.createTrailer();
        assertEquals("Trailer", trailer.getString(), "Mismatched tag");
    }

}
