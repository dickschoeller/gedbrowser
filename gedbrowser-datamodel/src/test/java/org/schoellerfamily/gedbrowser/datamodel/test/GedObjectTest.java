package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
@SuppressWarnings({ "PMD.TooManyStaticImports", "PMD.ExcessivePublicCount" })
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
    public void testGedObjectFind() {
        gob = new GedObjectWrapper(root);
        root.insert(GOB_TAG, gob);
        assertEquals("Find should have matched",
                gob, gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testGedObjectDefaultEmptyString() {
        gob = new GedObjectWrapper(root);
        root.insert(GOB_TAG, gob);
        assertEquals("Expected empty string",
                "", gob.toString());
    }

    /** */
    @Test
    public void testGedObjectStringFind() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(null, gob);
        assertEquals("Find should have matched", gob, gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testGedObjectGedObjectString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(null, gob);
        assertEquals("String mismatch", GOB_TAG, gob.toString());
    }

    /** */
    @Test
    public void testGetStringEmtpy() {
        gob = new GedObjectWrapper(root);
        assertEquals("Expected empty string", "", gob.getString());
    }

    /** */
    @Test
    public void testGetString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals("String mismatch",
                GOB_TAG, gob.getString());
    }

    /** */
    @Test
    public void testSetStringFromEmpty() {
        gob = new GedObjectWrapper(root);
        gob.setString(GOB_TAG);
        assertEquals("String mismatch", GOB_TAG, gob.getString());
    }

    /** */
    @Test
    public void testSetStringFromString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        gob.setString("BOG");
        assertEquals("String mismatch", "BOG", gob.getString());
    }

    /** */
    public void testSetStringNullGivesEmpty() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        gob.setString(null);
        assertEquals("Expected empty string", "", gob.getString());
    }

    /** */
    @Test
    public void testAppendString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        gob.appendString("?");
        assertEquals("String mismatch", "GOB?", gob.getString());
    }

    /** */
    @Test
    public void testHashCode() {
        gob = createGedObject();
        assertEquals("Mismatched hash code", HASH_CODE_1, gob.hashCode());
    }

    /** */
    @Test
    public void testAttributeDoesNotAffectHashCode() {
        gob = createGedObject();
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertEquals("Mismatched hash code", HASH_CODE_1, gob.hashCode());
    }

    /** */
    @Test
    public void testInsertFamily() {
        final Family family = new Family(root);
        family.setString("F777");
        root.insert(family);
        assertEquals("Should have found same family",
                family, finder.find(family, "F777", Family.class));
    }

    /**
     * @return the new GedObject
     */
    private GedObject createGedObject() {
        return new GedObjectWrapper(root, GOB_TAG);
    }

    /** */
    @Test
    public void testGetParentAfterCreate() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals("Parent should be root", root, gob.getParent());
    }

    /** */
    @Test
    public void testGetNullParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull("Parent should be null", gob.getParent());
    }

    /** */
    @Test
    public void testSetParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        gob.setParent(root);
        assertEquals("Parent should be root", root, gob.getParent());
    }

    /** */
    @Test
    public void testAddAttributesInitiallyEmpty() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertTrue("List should be empty", gob.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testAddAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        final List<GedObject> attributes = gob.getAttributes();
        assertTrue("Attribute list should only contain new attribute",
                attributes.contains(attribute) && attributes.size() == 1);
    }

    /** */
    @Test
    public void testRemoveAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);

        gob.removeAttribute(attribute);
        assertTrue("Add remove attribute should be empty",
                gob.getAttributes().isEmpty());
    }

    /** */
    @Test
    public void testHasAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertTrue("Should have attribute", gob.hasAttribute(attribute));
    }

    /** */
    @Test
    public void testHasAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertTrue("Should have attributes", gob.hasAttributes());
    }

    /** */
    @Test
    public void testHasNoAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertFalse("Should have atttributes", gob.hasAttributes());
    }

    /** */
    @Test
    public void testGetParentDbName() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals("DB name mismatch", "DBNAME", finder.getDbName(gob));
    }

    /** */
    @Test
    public void testFindInParentBySurnameNullParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull("Should not have found anything",
                gob.findInParentBySurname("FOO"));
    }

    /** */
    @Test
    public void testGetNullParentDbName() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull("DB name should be null", finder.getDbName(gob));
    }

    /** */
    @Test
    public void testEqualsObject() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        assertEquals("Objects should be equal", gob, gob1);
    }

    /** */
    @Test
    public void testNotNull() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNotNull("Object should not be null", gob);
    }

    /** */
    @SuppressWarnings("PMD.EqualsNull")
    public void testEqualsWithAttributesNotInserted() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        new Attribute(gob, "A");
        new Attribute(gob1, "A");
        assertEquals("Objects should be equal", gob, gob1);
    }

    /** */
    @Test
    public void testEqualsWithAttributesInserted() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        final Attribute attr1 = new Attribute(gob, "A");
        final Attribute attr2 = new Attribute(gob1, "A");
        gob.insert(attr1);
        gob1.insert(attr2);
        assertEquals("Objects should be equal", gob, gob1);
    }

    /** */
    @Test
    public void testEqualsWithAttributesDifferentAttributes() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        final Attribute attr1 = new Attribute(gob, "A");
        final Attribute attr2 = new Attribute(gob1, "A");
        gob.insert(attr1);
        gob1.insert(attr2);
        final Attribute attr3 = new Attribute(gob1, "B");
        gob1.insert(attr3);
        // Explicitly testing the equals method.
        final boolean equals = gob.equals(gob1);
        assertTrue("Expected the objects to be equal", equals);
    }

    /** */
    @Test
    public void testEqualsWithAttributesAfterRemove() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        final Attribute attr1 = new Attribute(gob, "A");
        final Attribute attr2 = new Attribute(gob1, "A");
        gob.insert(attr1);
        gob1.insert(attr2);
        final Attribute attr3 = new Attribute(gob1, "B");
        gob1.insert(attr3);
        gob1.removeAttribute(attr3);
        assertEquals("Objects should be equal", gob, gob1);
    }

    /** */
    @Test
    public void testNotEqualsWithDifferentParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        final Attribute attr1 = new Attribute(gob, "A");
        final Attribute attr2 = new Attribute(gob1, "A");
        gob.insert(attr1);
        gob1.insert(attr2);
        final Attribute attr3 = new Attribute(gob1, "B");
        gob1.insert(attr3);
        gob1.removeAttribute(attr3);
        gob1.setParent(root);
        assertNotEquals("Expected mismatch with different parents", gob, gob1);
    }

    /** */
    @Test
    @SuppressWarnings("PMD.EqualsNull")
    public void testEqualsNull() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertFalse("Expected not equals", gob.equals(null));
    }

    /** */
    @Test
    public void testNotEquals() {
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(
                new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals("Expected not equals", gob, gob1);
    }

    /** */
    @Test
    public void testNotEqualsOrder1() {
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), null);
        final GedObject gob1 = new GedObjectWrapper(
                new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob, gob1);
    }

    /** */
    @Test
    public void testNotEqualsOrder2() {
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), null);
        final GedObject gob1 = new GedObjectWrapper(
                new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob1, gob);
    }

    /** */
    @Test
    public void testFindMostlyEmpty() {
        gob = new GedObjectWrapper(root);
        assertNull("Expected null result", gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testFindUnaffectedBySetString() {
        gob = new GedObjectWrapper(root);
        gob.setString(GOB_TAG);
        assertNull("Expected null result", gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testFindAfterInsert() {
        gob = new GedObjectWrapper(root);
        root.insert(GOB_TAG, gob);
        assertEquals("Object mismatch", gob, gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testFindPersonWithProperId() {
        gob = new GedObjectWrapper(root);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert("I1", person);
        assertEquals("Person mismatch", person, gob.find("I1"));
    }

    /** */
    @Test
    public void testFindNullWithNullParent() {
        gob = new GedObjectWrapper(root);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert("I1", person);
        gob = new GedObjectWrapper(null);
        assertNull("Expected null", gob.find(GOB_TAG));
    }

    /** */
    @Test
    public void testGetFilenameNullOnBasicCreation() {
        gob = new GedObjectWrapper(root);
        assertNull("Expected null", gob.getFilename());
    }

    /** */
    @Test
    public void testGetFilenameAfterSet() {
        gob = new GedObjectWrapper(root);
        root.setFilename("filename.ged");
        assertEquals("filename mismatch", "filename.ged", gob.getFilename());
    }

    /** */
    @Test
    public void testGetDbName() {
        gob = new GedObjectWrapper(root);
        root.setDbName("filename");
        assertEquals("db name mismatch", "filename", root.getDbName());
    }

    /** */
    @Test
    public void testGetFilenameNullOnNullParent() {
        gob = new GedObjectWrapper(null);
        assertNull("expected null", gob.getFilename());
    }

    /** */
    @Test
    public void testGetAttributesOnNormalCreation() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        final List<GedObject> attributes = gob.getAttributes();
        assertTrue("Expected only the one attribute",
                attributes.contains(attribute) && attributes.size() == 1);
    }
}
