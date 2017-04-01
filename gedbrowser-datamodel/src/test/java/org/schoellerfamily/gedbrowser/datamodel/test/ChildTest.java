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
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
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
        family1 = builder.createFamily("F1");
        builder.addHusbandToFamily(family1, person1);
        builder.addWifeToFamily(family1, person2);
        child1 = builder.addChildToFamily(family1, person3);
    }

    /** */
    @Test
    public void testGetChildrenSize() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> list = navigator.getChildren();
        assertEquals("Expected only 1 person", 1, list.size());
    }

    /** */
    @Test
    public void testGetChildrenContains() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> list = navigator.getChildren();
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
        assertFalse("Expected null object", new Child().getChild().isSet());
    }

    /** */
    @Test
    public void testGetFatherUnspecifiedParentUnset() {
        final FamilyNavigator navigator = new FamilyNavigator(new Child());
        assertFalse("Expected null object", navigator.getFather().isSet());
    }

    /** */
    @Test
    public void testGetMotherUnsetParentUnset() {
        final FamilyNavigator navigator = new FamilyNavigator(new Child());
        assertFalse("Expected null object", navigator.getMother().isSet());
    }

    /** */
    @Test
    public void testChildUnspecifiedFatherShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        final FamilyNavigator navigator = new FamilyNavigator(child);
        assertFalse("Expected null object", navigator.getFather().isSet());
    }

    /** */
    @Test
    public void testChildUnspecifiedMotherShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        final FamilyNavigator navigator = new FamilyNavigator(child);
        assertFalse("Expected null object", navigator.getMother().isSet());
    }

    /** */
    @Test
    public void testChildUnspecifiedPersonShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertFalse("Expected null object", child.getChild().isSet());
    }

    /** */
    @Test
    public void testChildNullParentFind() {
        final Child child = new Child();
        assertNull("Expected to not find", child.find("I1"));
    }

    /** */
    @Test
    public void testChildEmptyTail() {
        final Root localRoot = new Root("Root");
        final Child child = new Child(localRoot, "I1", new ObjectId(""));
        assertPerson1(child, "I1");
    }

    /** */
    @Test
    public void testChildEmptyTailStrip() {
        final Root localRoot = new Root("Root");
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
        final Root localRoot = new Root("Root");
        final Child child2 = new Child(localRoot, "I1", new ObjectId("I1"));
        localRoot.insert(child2);
        assertEquals("Mismatched child", child2, child2.find("I1"));
    }
}
