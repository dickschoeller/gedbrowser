package org.schoellerfamily.gedbrowser.datamodel.factory.test;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class GedObjectFactoryTest {
    /** */
    private final GedObjectFactory factory;
    /** */
    private final GedObject parent;
    /** */
    private final String xref;
    /** */
    private final String tag;
    /** */
    private final String tail;

    /**
     * @param parent the parent of the object being created
     * @param xref a cross reference string (can be null or empty)
     * @param tag a tag (if null or empty defaults to ATTRIBUTE)
     * @param tail additional text from the line (can be null or empty)
     */
    public GedObjectFactoryTest(final GedObject parent,
            final String xref, final String tag, final String tail) {
        this.factory = new GedObjectFactory();
        this.parent = parent;
        this.xref = xref;
        this.tag = tag;
        this.tail = tail;
    }

    /**
     * @return the collection of test parameters
     */
    @Parameters
    public static Object[][] params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Root root = builder.getRoot();

        return new Object[][] {
            {null, null, null, null},
            {null, null, "ROOT", null},
            {null, "", "ROOT", null},
            {null, null, "ROOT", ""},
            {root, null, null, null},
            {root, null, "ROOT", null},
            {root, "", "ROOT", null},
            {root, null, "ROOT", ""},
        };
    }


    /** */
    @Test
    public void testFactory() {
        final GedObject gob = factory.create(parent, new ObjectId(xref), tag,
                tail);
        assertNull("should always return null", gob);
    }
}
