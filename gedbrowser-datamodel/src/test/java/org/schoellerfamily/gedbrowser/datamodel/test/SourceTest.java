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
    public void testSourceGedObjectCompare() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        final GedObject gob = root.find("S1");
        assertEquals("Found wrong source", source, gob);
    }

    /** */
    @Test
    public void testSourceGedObjectGetString() {
        final Source source = new Source();
        assertTrue("Source string should be empty", source.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testSourceGedObjectStringGetString() {
        final Root root = new Root("Root");
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        assertEquals("Expected source 1", "S1", source.getString());
    }
}
