package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * @author Dick Schoeller
 */
public final class SourceTest {
    /** */
    @Test
    public void testSourceGedObject() {
        final Root root = new Root(null, "Root");
        final Source source = new Source(root);
        root.insert("S1", source);
        final GedObject gob = root.find("S1");
        assertEquals("Found wrong source", source, gob);
        assertTrue("Source string should be empty", source.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testSourceGedObjectString() {
        final Root root = new Root(null, "Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert("S1", source);
        final GedObject gob = root.find("S1");
        assertEquals("Found wrong source", source, gob);
        assertEquals("Expected source 1", "S1", source.getString());
    }
}
