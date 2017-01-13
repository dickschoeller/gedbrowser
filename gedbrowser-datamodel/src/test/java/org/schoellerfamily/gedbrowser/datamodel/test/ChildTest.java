package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyStaticImports")
public final class ChildTest {
    /** */
    private transient Family family1;
    /** */
    private transient Person person3;
    /** */
    private transient Child child1;

    /**
     */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();

        final Person person1 = builder.createPerson1();
        final Person person2 = builder.createPerson2();
        person3 = builder.createPerson3();
        family1 = builder.createFamily1();
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        child1 = builder.addChildToFamily(family1, person3);
    }

    /** */
    @Test
    public void testGetChildrenSize() {
        final List<Person> list = family1.getChildren();
        assertEquals("Expected only 1 person", 1, list.size());
    }

    /** */
    @Test
    public void testGetChildrenContains() {
        final List<Person> list = family1.getChildren();
        assertTrue("Contents mismatch", list.contains(person3));
    }

    /** */
    @Test
    public void testGetChildMatch() {
        assertSame("Mismatched person", person3, child1.getChild());
    }

    /** */
    @Test
    public void testGetChildNullParentUnset() {
        assertFalse("Expected null object", new Child(null).getChild().isSet());
    }

    /** */
    @Test
    public void testGetChildUnspecifiedParentUnset() {
        assertFalse("Expected null object", new Child().getChild().isSet());
    }

    /** */
    @Test
    public void testGetFatherNullParentUnset() {
        assertFalse("Expected null object",
                new Child(null).getFather().isSet());
    }

    /** */
    @Test
    public void testGetFatherUnspecifiedParentUnset() {
        assertFalse("Expected null object", new Child().getFather().isSet());
    }

    /** */
    @Test
    public void testGetMotherNullParentUnset() {
        assertFalse("Expected null object",
                new Child(null).getMother().isSet());
    }

    /** */
    @Test
    public void testGetMotherUnsetParentUnset() {
        assertFalse("Expected null object", new Child().getMother().isSet());
    }

    /** */
    @Test
    public void testChildUnspecifiedIdStringShouldReturnEmpty() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family);
        assertTrue("Child string should be empty", child.getString().isEmpty());
    }

    /** */
    @Test
    public void testChildUnspecifiedIdStringToStringShouldBeEmpty() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family);
        assertTrue("Expected empty string", child.getToString().isEmpty());
    }

    /** */
    @Test
    public void testChildFamilyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family);
        assertEquals("Should match family ID", "F1", child.getFromString());
    }

    /** */
    @Test
    public void testChildUnspecifiedPersonShouldReturnUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family);
        assertFalse("Expected null object", child.getChild().isSet());
    }

    /** */
    @Test
    public void testChildWithTagShouldBeInToString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child");
        assertEquals("Expected matching tag", "Child", child.getString());
    }

    /** */
    @Test
    public void testChildTag() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertEquals("Expected matching tag", "Child", child.getString());
    }

    /** */
    @Test
    public void testChildPersonId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertEquals("Expected matching person ID", "I3", child.getToString());
    }

    /** */
    @Test
    public void testChildFamilyIdShouldBeDerivableFromParent() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertEquals("Expected matching family ID",
                "F1", child.getFromString());
    }

    /** */
    @Test
    public void testChildUnspecifiedFatherShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertFalse("Expected null object", child.getFather().isSet());
    }

    /** */
    @Test
    public void testChildUnspecifiedMotherShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertFalse("Expected null object", child.getMother().isSet());
    }

    /** */
    @Test
    public void testChildUnspecifiedPersonShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily1();
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertFalse("Expected null object", child.getChild().isSet());
    }

    /** */
    @Test
    public void testChildNullParentFind() {
        final Child child = new Child(null);
        assertNull("Expected to not find", child.find("I1"));
    }

    /** */
    @Test
    public void testChildNullString() {
        final Root localRoot = new Root(null, "Root");
        final Child child = new Child(localRoot, null);
        assertEmpty(child);
    }

    /** */
    @Test
    public void testChildEmptyString() {
        final Root localRoot = new Root(null, "Root");
        final Child child = new Child(localRoot, "");
        assertEmpty(child);
    }

    /**
     * Check for nothing set.
     *
     * @param child the child to check
     */
    private void assertEmpty(final Child child) {
        assertEquals("Expected empty string", "", child.getString());
        assertEquals("Expected empty string", "", child.getToString());
        assertNull("Expected not to find", child.find("I1"));
    }

    /** */
    @Test
    public void testChildEmptyTail() {
        final Root localRoot = new Root(null, "Root");
        final Child child = new Child(localRoot, "I1", new ObjectId(""));
        assertPerson1(child, "I1");
    }

    /** */
    @Test
    public void testChildEmptyTailStrip() {
        final Root localRoot = new Root(null, "Root");
        final Child child = new Child(localRoot, "I1", new ObjectId("@@"));
        assertPerson1(child, "I1");
    }

    /**
     * Child expected to match the ID but otherwise be empty.
     *
     * @param child the child to check
     * @param idString the expected ID string
     */
    private void assertPerson1(final Child child, final String idString) {
        assertEquals("Mismatched ID", idString, child.getString());
        assertTrue("Expected empty string", child.getToString().isEmpty());
        assertNull("Expected to not find", child.find(idString));
    }

    /** */
    @Test
    public void testChildEmptyTailAfterInsert() {
        final Root localRoot = new Root(null, "Root");
        final Child child2 = new Child(localRoot, "I1", new ObjectId(""));
        localRoot.insert("I1", child2);
        assertEquals("Mismatched child", child2, child2.find("I1"));
    }
}
