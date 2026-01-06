package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @BeforeEach
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();

        final Person person1 = builder.createPerson(
                "I1", "J. Random/Schoeller/");
        final Person person2 = builder.createPerson(
                "I2", "Anonymous/Schoeller/");
        person3 = builder.createPerson(
                "I3", "Anonymous/Jones/");
        family1 = builder.createFamily("F1");
        final Family family = family1;
        builder.addHusbandToFamily(family, person1);
        final Family family2 = family1;
        builder.addWifeToFamily(family2, person2);
        final Family family3 = family1;
        final Person person = person3;
        child1 = builder.addChildToFamily(family3, person);
    }

    /** */
    @Test
    public void testGetChildrenSize() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> list = navigator.getChildren();
        assertEquals(1, list.size(), "Expected only 1 person");
    }

    /** */
    @Test
    public void testGetChildrenContains() {
        final FamilyNavigator navigator = new FamilyNavigator(family1);
        final List<Person> list = navigator.getChildren();
        assertTrue(list.contains(person3), "Contents mismatch");
    }

    /** */
    @Test
    public void testGetChildMatch() {
        assertSame(person3, child1.getChild(), "Mismatched person");
    }

    /** */
    @Test
    public void testGetChildNullParentUnset() {
        assertFalse(new Child().getChild().isSet(), "Expected null object");
    }

    /** */
    @Test
    public void testGetFatherUnspecifiedParentUnset() {
        final FamilyNavigator navigator = new FamilyNavigator(new Child());
        assertFalse(navigator.getFather().isSet(), "Expected null object");
    }

    /** */
    @Test
    public void testGetMotherUnsetParentUnset() {
        final FamilyNavigator navigator = new FamilyNavigator(new Child());
        assertFalse(navigator.getMother().isSet(), "Expected null object");
    }

    /** */
    @Test
    public void testChildUnspecifiedFatherShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        final FamilyNavigator navigator = new FamilyNavigator(child);
        assertFalse(navigator.getFather().isSet(), "Expected null object");
    }

    /** */
    @Test
    public void testChildUnspecifiedMotherShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        final FamilyNavigator navigator = new FamilyNavigator(child);
        assertFalse(navigator.getMother().isSet(), "Expected null object");
    }

    /** */
    @Test
    public void testChildUnspecifiedPersonShouldBeUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Family family = builder.createFamily("F1");
        final Child child = new Child(family, "Child", new ObjectId("I3"));
        assertFalse(child.getChild().isSet(), "Expected null object");
    }

    /** */
    @Test
    public void testChildNullParentFind() {
        final Child child = new Child();
        assertNull(child.find("I1"), "Expected to not find");
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
        assertEquals(idString, child.getString(), "Mismatched ID");
        assertTrue(child.getToString().isEmpty(), "Expected empty string");
        assertNull(child.find(idString), "Expected to not find");
    }

    /** */
    @Test
    public void testChildEmptyTailAfterInsert() {
        final Root localRoot = new Root("Root");
        final Child child2 = new Child(localRoot, "I1", new ObjectId("I1"));
        localRoot.insert(child2);
        assertEquals(child2, child2.find("I1"), "Mismatched child");
    }
}
