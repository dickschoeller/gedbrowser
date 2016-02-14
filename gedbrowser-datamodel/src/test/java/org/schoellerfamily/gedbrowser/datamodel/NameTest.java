package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class NameTest {
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Person person = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Name name1 = new Name(person, "Karl/Schoeller/Jr.");
    /** */
    private final transient Name name2 = new Name(person, "Karl/Schoeller/");
    /** */
    private final transient Name name3 = new Name(person, "Wingnut");
    /** */
    private final transient Name name4 = new Name(person, "/Noodle/");
    /** */
    private final transient Name name5 = new Name(person, "/Wang/Foo");

    /** */
    @Before
    public void setUp() {
        root.insert("I1", person);
        person.insert(name1);
        person.insert(name2);
        person.insert(name3);
        person.insert(name4);
        person.insert(name5);
    }

    /** */
    @Test
    public void testGetName() {
        assertEquals(name1, name1.getName());
        assertEquals(name2, name2.getName());
        assertEquals(name3, name3.getName());
        assertEquals(name1, person.getName());
    }

    /** */
    @Test
    public void testGetIndexName() {
        assertEquals("Schoeller, Karl, Jr.", name1.getIndexName());
        assertEquals("Schoeller, Karl", name2.getIndexName());
        assertEquals("?, Wingnut", name3.getIndexName());
        assertEquals("Noodle, ?", name4.getIndexName());
        assertEquals("Wang Foo", name5.getIndexName());
    }

    /** */
    @Test
    public void testGetSurname() {
        assertEquals("Schoeller", name1.getSurname());
        assertEquals("Schoeller", name2.getSurname());
        assertEquals("?", name3.getSurname());
        assertEquals("Noodle", name4.getSurname());
        assertEquals("Wang", name5.getSurname());
    }

    /** */
    @Test
    public void testGetPrefix() {
        assertEquals("Karl", name1.getPrefix());
        assertEquals("Karl", name2.getPrefix());
        assertEquals("Wingnut", name3.getPrefix());
        assertEquals("", name4.getPrefix());
        assertEquals("", name5.getPrefix());
    }

    /** */
    @Test
    public void testGetSuffix() {
        assertEquals("Jr.", name1.getSuffix());
        assertEquals("", name2.getSuffix());
        assertEquals("", name3.getSuffix());
        assertEquals("", name4.getSuffix());
        assertEquals("", name4.getSuffix());
        assertEquals("Foo", name5.getSuffix());
    }

    /** */
    @Test
    public void testNameGedObject() {
        final Root localRoot = new Root(null, "Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert("I1", localPerson);
        final Name name = new Name(localPerson);
        localPerson.insert(name);
        assertEquals(localPerson, name.getParent());
        assertEquals("", name.getString());
    }

    /** */
    @Test
    public void testNameGedObjectString() {
        final Root localRoot = new Root(null, "Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert("I1", localPerson);
        final Name name = new Name(localPerson, "Karl/Schoeller/Jr.");
        localPerson.insert(name);
        assertEquals(localPerson, name.getParent());
        assertEquals("Karl/Schoeller/Jr.", name.getString());
        assertEquals("Karl", name.getPrefix());
        assertEquals("Schoeller", name.getSurname());
        assertEquals("Jr.", name.getSuffix());
    }
}
