package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * @author Dick Schoeller
 */
final class SourceTest {
    /** */
    @Test
    void testSourceGedObjectCompare() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        final GedObject gob = root.find("S1");
        assertEquals(source, gob, "Found wrong source");
    }

    /** */
    @Test
    void testSourceGedObjectGetString() {
        final Source source = new Source();
        assertTrue(source.getString().isEmpty(), "Source string should be empty");
    }

    /** */
    @Test
    void testSourceGedObjectStringGetString() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        assertEquals("S1", source.getString(), "Expected source 1");
    }
}
