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
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyStaticImports" })
public final class ChildTest {
    /** */
    private static final String CHILD_TAG = "CHIL";
    /** */
    private static final String PERSON_3 = "I3";
    /** */
    private static final ObjectId XREF_PER_3 = new ObjectId(PERSON_3);
    /** */
    private static final String PERSON_2 = "I2";
    /** */
    private static final ObjectId XREF_PER_2 = new ObjectId(PERSON_2);
    /** */
    private static final String PERSON_1 = "I1";
    /** */
    private static final ObjectId XREF_PER_1 = new ObjectId(PERSON_1);
    /** */
    private static final String FAMILY_1 = "F1";
    /** */
    private static final ObjectId XREF_FAM_1 = new ObjectId(FAMILY_1);
    /** */
    private static final String ROOT_NAME = "Root";

    /** */
    private final transient Root root = new Root(null, ROOT_NAME);
    /** */
    private final transient Family family1 = new Family(root, XREF_FAM_1);
    /** */
    private final transient Person person1 = new Person(root, XREF_PER_1);
    /** */
    private final transient Person person2 = new Person(root, XREF_PER_2);
    /** */
    private final transient Person person3 = new Person(root, XREF_PER_3);
    /** */
    private final transient Husband husband1 = new Husband(family1, "HUSB",
            new ObjectId("@I1@"));
    /** */
    private final transient Wife wife1 = new Wife(family1, "WIFE",
            new ObjectId("@I2@"));
    /** */
    private final transient Child child1 = new Child(family1, "CHILD",
            new ObjectId("@I3@"));

    /**
     */
    @Before
    public void setUp() {
        root.insert(PERSON_1, person1);
        root.insert(PERSON_2, person2);
        root.insert(PERSON_3, person3);
        root.insert(FAMILY_1, family1);

        person1.insert(new FamS(person1, "FAMS", XREF_FAM_1));
        person2.insert(new FamS(person2, "FAMS", XREF_FAM_1));
        person3.insert(new FamC(person3, "FAMC", XREF_FAM_1));

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
    }

    /** */
    @Test
    public void testGetChildren() {
        List<Person> list = family1.getChildren();
        assertEquals(1, list.size());
        assertTrue(list.contains(person3));
        list = family1.getChildren();
        assertEquals(1, list.size());
        assertTrue(list.contains(person3));

        assertSame(person3, child1.getChild());
    }

    /** */
    @Test
    public void testGetChildUnset() {
        assertFalse(new Child(null).getChild().isSet());
        assertFalse(new Child().getChild().isSet());
    }

    /** */
    @Test
    public void testGetFatherUnset() {
        assertFalse(new Child(null).getFather().isSet());
        assertFalse(new Child().getFather().isSet());
    }

    /** */
    @Test
    public void testGetMotherUnset() {
        assertFalse(new Child(null).getMother().isSet());
        assertFalse(new Child().getMother().isSet());
    }

    /** */
    @Test
    public void testChildGedObject() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Family family = new Family(localRoot, XREF_FAM_1);
        localRoot.insert(FAMILY_1, family);
        final Child child = new Child(family);
        assertTrue("Child string should be empty", child.getString().isEmpty());
        assertEquals("", child.getToString());
        assertEquals(FAMILY_1, child.getFromString());
        assertFalse(child.getChild().isSet());
    }

    /** */
    @Test
    public void testChildGedObjectString() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Family family = new Family(localRoot, XREF_FAM_1);
        localRoot.insert(FAMILY_1, family);
        final Child child = new Child(family, CHILD_TAG);
        assertEquals(CHILD_TAG, child.getString());
        assertEquals("", child.getToString());
        assertEquals(FAMILY_1, child.getFromString());
        assertFalse(child.getChild().isSet());
    }

    /** */
    @Test
    public void testChildGedObjectStringString() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Family family = new Family(localRoot, XREF_FAM_1);
        localRoot.insert(FAMILY_1, family);
        final Child child = new Child(family, CHILD_TAG, new ObjectId("@I3@"));

        assertEquals(CHILD_TAG, child.getString());
        assertEquals(PERSON_3, child.getToString());
        assertEquals(FAMILY_1, child.getFromString());
        assertFalse(child.getFather().isSet());
        assertFalse(child.getMother().isSet());
        assertFalse(child.getChild().isSet());
    }

    /** */
    @Test
    public void testChildNullParentFind() {
        Child child;
        child = new Child(null);
        assertNull(child.find(PERSON_1));
    }

    /** */
    @Test
    public void testChildNullString() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Child child = new Child(localRoot, null);
        assertEquals("", child.getString());
        assertEquals("", child.getToString());
        assertNull(child.find(PERSON_1));
    }

    /** */
    @Test
    public void testChildEmptyString() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Child child = new Child(localRoot, "");
        assertEquals("", child.getString());
        assertEquals("", child.getToString());
        assertNull(child.find(PERSON_1));
    }

    /** */
    @Test
    public void testChildEmptyTail() {
        final Root localRoot = new Root(null, ROOT_NAME);
        final Child child3 =
                new Child(localRoot, PERSON_1, new ObjectId("@@"));
        assertEquals(PERSON_1, child3.getString());
        assertEquals("", child3.getToString());
        assertNull(child3.find(PERSON_1));
        final Child child2 = new Child(localRoot, PERSON_1, new ObjectId(""));
        assertEquals(PERSON_1, child2.getString());
        assertEquals("", child2.getToString());
        assertNull(child2.find(PERSON_1));
        localRoot.insert(PERSON_1, child2);
        assertEquals(child2, child2.find(PERSON_1));
    }
}
