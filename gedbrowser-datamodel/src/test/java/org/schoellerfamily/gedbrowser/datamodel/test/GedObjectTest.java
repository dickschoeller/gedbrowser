package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.finder.ParentFinder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

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
         * @param parent parent object of this object
         */
        GedObjectWrapper(final GedObject parent) {
            super(parent);
        }

        /**
         * @param parent parent object of this object
         * @param string long version of type string
         */
        GedObjectWrapper(final GedObject parent, final String string) {
            super(parent, string);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(final GedObjectVisitor visitor) {
            visitor.visit(this);
        }
    }

    /** */
    @BeforeEach
    public void setUp() {
        root = new Root("Root");
        root.setDbName("DBNAME");
    }

    /** */
    @Test
    public void testGedObjectDefaultEmptyString() {
        gob = new GedObjectWrapper(root);
        assertEquals("", gob.toString(), "Expected empty string");
    }

    /** */
    @Test
    public void testGedObjectStringFind() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(gob);
        assertEquals(gob, gob.find(GOB_TAG), "Find should have matched");
    }

    /** */
    @Test
    public void testGedObjectGedObjectString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(gob);
        assertEquals(GOB_TAG, gob.toString(), "String mismatch");
    }

    /** */
    @Test
    public void testGetStringEmtpy() {
        gob = new GedObjectWrapper(root);
        assertEquals("", gob.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testGetString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals(GOB_TAG, gob.getString(), "String mismatch");
    }

    /** */
    @Test
    public void testSetStringFromEmpty() {
        gob = new GedObjectWrapper(root);
        gob.setString(GOB_TAG);
        assertEquals(GOB_TAG, gob.getString(), "String mismatch");
    }

    /** */
    @Test
    public void testSetStringFromString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        gob.setString("BOG");
        assertEquals("BOG", gob.getString(), "String mismatch");
    }

    /** */
    public void testSetStringNullGivesEmpty() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        gob.setString(null);
        assertEquals("", gob.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testAppendString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        gob.appendString("?");
        assertEquals("GOB?", gob.getString(), "String mismatch");
    }

    /** */
    @Test
    public void testHashCode() {
        gob = createGedObject();
        assertEquals(HASH_CODE_1, gob.hashCode(), "Mismatched hash code");
    }

    /** */
    @Test
    public void testAttributeDoesNotAffectHashCode() {
        gob = createGedObject();
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertEquals(HASH_CODE_1, gob.hashCode(), "Mismatched hash code");
    }

    /** */
    @Test
    public void testInsertFamily() {
        final Family family = new Family(root, new ObjectId("F777"));
        root.insert(family);
        assertEquals(family, finder.find(family, "F777", Family.class),
            "Should have found same family");
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
        assertEquals(root, gob.getParent(), "Parent should be root");
    }

    /** */
    @Test
    public void testGetNullParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull(gob.getParent(), "Parent should be null");
    }

    /** */
    @Test
    public void testSetParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        gob.setParent(root);
        assertEquals(root, gob.getParent(), "Parent should be root");
    }

    /** */
    @Test
    public void testAddAttributesInitiallyEmpty() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertTrue(gob.getAttributes().isEmpty(), "List should be empty");
    }

    /** */
    @Test
    public void testAddAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        final List<GedObject> attributes = gob.getAttributes();
        assertTrue(attributes.contains(attribute) && attributes.size() == 1,
            "Attribute list should only contain new attribute");
    }

    /** */
    @Test
    public void testRemoveAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);

        gob.removeAttribute(attribute);
        assertTrue(gob.getAttributes().isEmpty(), "Add remove attribute should be empty");
    }

    /** */
    @Test
    public void testHasAttribute() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertTrue(gob.hasAttribute(attribute), "Should have attribute");
    }

    /** */
    @Test
    public void testHasAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);

        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        assertTrue(gob.hasAttributes(), "Should have attributes");
    }

    /** */
    @Test
    public void testHasNoAttributes() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertFalse(gob.hasAttributes(), "Should have atttributes");
    }

    /** */
    @Test
    public void testGetParentDbName() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertEquals("DBNAME", finder.getDbName(gob), "DB name mismatch");
    }

    /** */
    @Test
    public void testFindInParentBySurnameNullParent() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull(gob.findInParentBySurname("FOO"), "Should not have found anything");
    }

    /** */
    @Test
    public void testGetNullParentDbName() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNull(finder.getDbName(gob), "DB name should be null");
    }

    /** */
    @Test
    public void testEqualsObject() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        assertEquals(gob, gob1, "Objects should be equal");
    }

    /** */
    @Test
    public void testNotNull() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertNotNull(gob, "Object should not be null");
    }

    /** */
    @SuppressWarnings("PMD.EqualsNull")
    public void testEqualsWithAttributesNotInserted() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(null, GOB_TAG);
        new Attribute(gob, "A");
        new Attribute(gob1, "A");
        assertEquals(gob, gob1, "Objects should be equal");
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
        assertEquals(gob, gob1, "Objects should be equal");
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
        assertTrue(equals, "Expected the objects to be equal");
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
        assertEquals(gob, gob1, "Objects should be equal");
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
        assertNotEquals(gob, gob1, "Expected mismatch with different parents");
    }

    /** */
    @Test
    @SuppressWarnings("PMD.EqualsNull")
    public void testEqualsNull() {
        gob = new GedObjectWrapper(null, GOB_TAG);
        assertFalse(gob.equals(null), "Expected not equals");
    }

    /** */
    @Test
    public void testNotEquals() {
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), GOB_TAG);
        final GedObject gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob, gob1, "Expected not equals");
    }

    /** */
    @Test
    public void testNotEqualsOrder1() {
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), null);
        final GedObject gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob, gob1);
    }

    /** */
    @Test
    public void testNotEqualsOrder2() {
        gob = new GedObjectWrapper(new GedObjectWrapper(null, "bar"), null);
        final GedObject gob1 = new GedObjectWrapper(new GedObjectWrapper(null, "foo"), GOB_TAG);
        assertNotEquals(gob1, gob);
    }

    /** */
    @Test
    public void testFindMostlyEmpty() {
        gob = new GedObjectWrapper(root);
        assertNull(gob.find(GOB_TAG), "Expected null result");
    }

    /** */
    @Test
    public void testFindUnaffectedBySetString() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        assertNull(gob.find(GOB_TAG), "Expected null result");
    }

    /** */
    @Test
    public void testFindAfterInsert() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        root.insert(gob);
        assertEquals(gob, gob.find(GOB_TAG), "Object mismatch");
    }

    /** */
    @Test
    public void testFindPersonWithProperId() {
        gob = new GedObjectWrapper(root);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        assertEquals(person, gob.find("I1"), "Person mismatch");
    }

    /** */
    @Test
    public void testFindNullWithNullParent() {
        gob = new GedObjectWrapper(root);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        gob = new GedObjectWrapper(null);
        assertNull(gob.find(GOB_TAG), "Expected null");
    }

    /** */
    @Test
    public void testGetFilenameNullOnBasicCreation() {
        gob = new GedObjectWrapper(root);
        assertNull(gob.getFilename(), "Expected null");
    }

    /** */
    @Test
    public void testGetFilenameAfterSet() {
        gob = new GedObjectWrapper(root);
        root.setFilename("filename.ged");
        assertEquals("filename.ged", gob.getFilename(), "filename mismatch");
    }

    /** */
    @Test
    public void testGetDbName() {
        gob = new GedObjectWrapper(root);
        root.setDbName("filename");
        assertEquals("filename", root.getDbName(), "db name mismatch");
    }

    /** */
    @Test
    public void testGetFilenameNullOnNullParent() {
        gob = new GedObjectWrapper(null);
        assertNull(gob.getFilename(), "expected null");
    }

    /** */
    @Test
    public void testGetAttributesOnNormalCreation() {
        gob = new GedObjectWrapper(root, GOB_TAG);
        final Attribute attribute = new Attribute(gob, ATTR_TAG, ATTR_NAME);
        gob.insert(attribute);
        final List<GedObject> attributes = gob.getAttributes();
        assertTrue(attributes.contains(attribute) && attributes.size() == 1,
            "Expected only the one attribute");
    }
}
