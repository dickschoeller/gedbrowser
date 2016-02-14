package org.schoellerfamily.gedbrowser.datamodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class GedObjectTest { // NOPMD
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
    private static class GedObjectWrapper extends GedObject {
        /**
         * @param parent
         *            parent object of this object
         */
        public GedObjectWrapper(final GedObject parent) {
            super(parent);
        }

        /**
         * @param parent
         *            parent object of this object
         * @param string
         *            long version of type string
         */
        public GedObjectWrapper(final GedObject parent, final String string) {
            super(parent, string);
        }
    }

    /** */
    @Before
    public void setUp() {
        root = new Root(null, "Root");
    }

    /** */
    @Test
    public void testGedObjectGedObject() {
        gob = new GedObjectWrapper(root);
        root.insert(GOB_TAG, gob);
        Assert.assertEquals(gob, gob.find(GOB_TAG));
        Assert.assertEquals("", gob.toString());
    }

    /** */
    @Test
    public void testGedObjectGedObjectString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(null, gob);
        Assert.assertEquals(gob, gob.find(GOB_TAG));
        Assert.assertEquals(GOB_TAG, gob.toString());
    }

    /** */
    @Test
    public void testGetString() {
        gob = new GedObjectWrapper(root);
        Assert.assertEquals("", gob.getString());

        gob = new GedObjectWrapper(root, GOB_TAG);
        Assert.assertEquals(GOB_TAG, gob.getString());
    }

    /** */
    @Test
    public void testSetString() {
        gob = new GedObjectWrapper(root);
        Assert.assertEquals("", gob.getString());
        gob.setString(GOB_TAG);
        Assert.assertEquals(GOB_TAG, gob.getString());

        gob = new GedObjectWrapper(root, GOB_TAG);
        Assert.assertEquals(GOB_TAG, gob.getString());
        gob.setString("BOG");
        Assert.assertEquals("BOG", gob.getString());

        gob = new GedObjectWrapper(root, GOB_TAG);
        Assert.assertEquals(GOB_TAG, gob.getString());
        gob.setString(null);
        Assert.assertEquals("", gob.getString());
    }

    /** */
    @Test
    public void testAppendString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        Assert.assertEquals(GOB_TAG, gob.getString());
        gob.appendString("?");
        Assert.assertEquals("GOB?", gob.getString());
    }

    /** */
    @Test
    public void testHashCode() {
        gob = createGedObject();
        Assert.assertEquals(HASH_CODE_1, gob.hashCode());

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);

        Assert.assertEquals(HASH_CODE_1, gob.hashCode());
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
        Assert.assertEquals(root, gob.getParent());

        gob = new GedObjectWrapper(null, GOB_TAG);
        Assert.assertEquals(null, gob.getParent());
    }

    /** */
    @Test
    public void testSetParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        Assert.assertEquals(null, gob.getParent());
        gob.setParent(root);
        Assert.assertEquals(root, gob.getParent());
    }

    /** */
    @Test
    public void testAddAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        Assert.assertEquals(0, gob.getAttributes().size());

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        Assert.assertEquals(1, gob.getAttributes().size());
        Assert.assertTrue(gob.getAttributes().contains(attribute));
    }

    /** */
    @Test
    public void testRemoveAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);

        gob.removeAttribute(attribute);
        Assert.assertEquals(0, gob.getAttributes().size());
    }

    /** */
    @Test
    public void testHasAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        Assert.assertTrue(gob.hasAttribute(attribute));
    }

    /** */
    @Test
    public void testEqualsObject() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        Assert.assertEquals(gob, gob1);
        Assert.assertNotNull(gob);
        final Attribute attr1 = new Attribute(gob, "A");
        final Attribute attr2 = new Attribute(gob1, "A");
        Assert.assertEquals(gob, gob1);
        gob.insert(attr1);
        gob1.insert(attr2);
        Assert.assertEquals(gob, gob1);
        final Attribute attr3 = new Attribute(gob1, "B");
        gob1.insert(attr3);
        Assert.assertTrue(gob.equals(gob1));
        gob1.removeAttribute(attr3);
        Assert.assertEquals(gob, gob1);
        gob1.setParent(root);
        Assert.assertNotEquals(gob, gob1);
        Assert.assertFalse(gob.equals(null)); // NOPMD
        //
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), GOB_TAG);
        gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        Assert.assertNotEquals(gob, gob1);
        //
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), null);
        gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        Assert.assertNotEquals(gob, gob1);
        Assert.assertNotEquals(gob1, gob);
    }

    /** */
    @Test
    public void testFind() {
        gob = new GedObjectWrapper(root);
        Assert.assertNull(gob.find(GOB_TAG));
        gob.setString(GOB_TAG);
        Assert.assertNull(gob.find(GOB_TAG));
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(GOB_TAG, gob);
        Assert.assertEquals(gob, gob.find(GOB_TAG));
        root.insert("I1", person);
        Assert.assertEquals(person, gob.find("I1"));

        gob = new GedObjectWrapper(null);
        Assert.assertNull(gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testGetFilename() {
        gob = new GedObjectWrapper(root);
        Assert.assertNull(gob.getFilename());
        root.setFilename("filename.ged");
        root.setDbName("filename");
        Assert.assertEquals("filename.ged", gob.getFilename());
        Assert.assertEquals("filename", root.getDbName());
        gob = new GedObjectWrapper(null);
        Assert.assertNull(gob.getFilename());
    }

    /** */
    @Test
    public void testGetAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        Assert.assertEquals(1, gob.getAttributes().size());
        Assert.assertTrue(gob.getAttributes().contains(attribute));
    }
}
