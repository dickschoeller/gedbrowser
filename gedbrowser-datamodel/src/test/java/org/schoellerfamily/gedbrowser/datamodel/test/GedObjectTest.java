package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.ParentFinder;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyStaticImports")
public final class GedObjectTest {
    /** */
    private static final int HASH_CODE_1 = 79247288;
    /** */
    private static final String ATTR_NAME = "attr";
    /** */
    private static final String ATTR_TAG = "ATTR";
    /** */
    private static final String GOB_TAG = "GOB";

    /** */
    private transient Root root;
    /** */
    private transient GedObject gob;

    /** */
    private final FinderStrategy finder = new ParentFinder();

    /** */
    private static class GedObjectWrapper extends GedObject {
        /**
         * @param parent
         *            parent object of this object
         */
        GedObjectWrapper(final GedObject parent) {
            super(parent);
        }

        /**
         * @param parent
         *            parent object of this object
         * @param string
         *            long version of type string
         */
        GedObjectWrapper(final GedObject parent, final String string) {
            super(parent, string);
        }
    }

    /** */
    @Before
    public void setUp() {
        root = new Root(null, "Root");
        root.setDbName("DBNAME");
    }

    /** */
    @Test
    public void testGedObjectGedObject() {
        gob = new GedObjectWrapper(root);
        root.insert(GOB_TAG, gob);
        assertEquals(gob, gob.find(GOB_TAG));
        assertEquals("", gob.toString());
    }

    /** */
    @Test
    public void testGedObjectGedObjectString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(null, gob);
        assertEquals(gob, gob.find(GOB_TAG));
        assertEquals(GOB_TAG, gob.toString());
    }

    /** */
    @Test
    public void testGetString() {
        gob = new GedObjectWrapper(root);
        assertEquals("", gob.getString());

        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(GOB_TAG, gob.getString());
    }

    /** */
    @Test
    public void testSetString() {
        gob = new GedObjectWrapper(root);
        assertEquals("", gob.getString());
        gob.setString(GOB_TAG);
        assertEquals(GOB_TAG, gob.getString());

        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(GOB_TAG, gob.getString());
        gob.setString("BOG");
        assertEquals("BOG", gob.getString());

        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(GOB_TAG, gob.getString());
        gob.setString(null);
        assertEquals("", gob.getString());
    }

    /** */
    @Test
    public void testAppendString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(GOB_TAG, gob.getString());
        gob.appendString("?");
        assertEquals("GOB?", gob.getString());
    }

    /** */
    @Test
    public void testHashCode() {
        gob = createGedObject();
        assertEquals(HASH_CODE_1, gob.hashCode());

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);

        assertEquals(HASH_CODE_1, gob.hashCode());
    }

    /** */
    @Test
    public void testInsertFamily() {
        final Family family = new Family(root);
        family.setString("F777");
        root.insert(family);
        assertEquals(family, finder.find(family, "F777", Family.class));
    }

    /**
     * @return the new GedObject
     */
    private GedObject createGedObject() {
        return new GedObjectWrapper(root, GOB_TAG);
    }

    /** */
    @Test
    public void testGetParent() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(root, gob.getParent());

        gob = new GedObjectWrapper(null, GOB_TAG);
        assertEquals(null, gob.getParent());
    }

    /** */
    @Test
    public void testSetParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertEquals(null, gob.getParent());
        gob.setParent(root);
        assertEquals(root, gob.getParent());
    }

    /** */
    @Test
    public void testAddAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(0, gob.getAttributes().size());

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertEquals(1, gob.getAttributes().size());
        assertTrue(gob.getAttributes().contains(attribute));
    }

    /** */
    @Test
    public void testRemoveAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);

        gob.removeAttribute(attribute);
        assertEquals(0, gob.getAttributes().size());
    }

    /** */
    @Test
    public void testHasAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertTrue(gob.hasAttribute(attribute));
    }

    /** */
    @Test
    public void testHasAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertTrue(gob.hasAttributes());
    }

    /** */
    @Test
    public void testHasNoAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertFalse(gob.hasAttributes());
    }

    /** */
    @Test
    public void testGetParentDbName() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals("DBNAME", finder.getDbName(gob));
    }

    /** */
    @Test
    public void testFindInParentBySurnameNullParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        gob.findInParentBySurname("FOO");
    }

    /** */
    @Test
    public void testGetNullParentDbName() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull(finder.getDbName(gob));
    }

    /** */
    @Test
    @SuppressWarnings("PMD.EqualsNull")
    public void testEqualsObject() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        assertEquals(gob, gob1);
        assertNotNull(gob);
        final Attribute attr1 = new Attribute(gob, "A");
        final Attribute attr2 = new Attribute(gob1, "A");
        assertEquals(gob, gob1);
        gob.insert(attr1);
        gob1.insert(attr2);
        assertEquals(gob, gob1);
        final Attribute attr3 = new Attribute(gob1, "B");
        gob1.insert(attr3);
        // Explicitly testing the equals method.
        final boolean equals = gob.equals(gob1);
        assertTrue("Expected the objects to be equal", equals);
        gob1.removeAttribute(attr3);
        assertEquals(gob, gob1);
        gob1.setParent(root);
        assertNotEquals(gob, gob1);
        assertFalse(gob.equals(null));
        //
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), GOB_TAG);
        gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob, gob1);
        //
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), null);
        gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob, gob1);
        assertNotEquals(gob1, gob);
    }

    /** */
    @Test
    public void testFind() {
        gob = new GedObjectWrapper(root);
        assertNull(gob.find(GOB_TAG));
        gob.setString(GOB_TAG);
        assertNull(gob.find(GOB_TAG));
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(GOB_TAG, gob);
        assertEquals(gob, gob.find(GOB_TAG));
        root.insert("I1", person);
        assertEquals(person, gob.find("I1"));

        gob = new GedObjectWrapper(null);
        assertNull(gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testGetFilename() {
        gob = new GedObjectWrapper(root);
        assertNull(gob.getFilename());
        root.setFilename("filename.ged");
        root.setDbName("filename");
        assertEquals("filename.ged", gob.getFilename());
        assertEquals("filename", root.getDbName());
        gob = new GedObjectWrapper(null);
        assertNull(gob.getFilename());
    }

    /** */
    @Test
    public void testGetAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertEquals(1, gob.getAttributes().size());
        assertTrue(gob.getAttributes().contains(attribute));
    }
}
