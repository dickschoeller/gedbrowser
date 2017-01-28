package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

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
        assertEquals("Name string mismatch", name1, name1.getName());
        assertEquals("Name string mismatch", name2, name2.getName());
        assertEquals("Name string mismatch", name3, name3.getName());
        assertEquals("Name string mismatch", name1, person.getName());
    }

    /** */
    @Test
    public void testGetIndexName() {
        assertEquals("Index name string mismatch",
                "Schoeller, Karl, Jr.", name1.getIndexName());
        assertEquals("Index name string mismatch",
                "Schoeller, Karl", name2.getIndexName());
        assertEquals("Index name string mismatch",
                "?, Wingnut", name3.getIndexName());
        assertEquals("Index name string mismatch",
                "Noodle, ?", name4.getIndexName());
        assertEquals("Index name string mismatch",
                "Wang Foo", name5.getIndexName());
    }

    /** */
    @Test
    public void testGetSurname() {
        assertEquals("Surname string mismatch",
                "Schoeller", name1.getSurname());
        assertEquals("Surname string mismatch",
                "Schoeller", name2.getSurname());
        assertEquals("Surname string mismatch",
                "?", name3.getSurname());
        assertEquals("Surname string mismatch",
                "Noodle", name4.getSurname());
        assertEquals("Surname string mismatch",
                "Wang", name5.getSurname());
    }

    /** */
    @Test
    public void testGetPrefix() {
        assertEquals("Prefix string mismatch",
                "Karl", name1.getPrefix());
        assertEquals("Prefix string mismatch",
                "Karl", name2.getPrefix());
        assertEquals("Prefix string mismatch",
                "Wingnut", name3.getPrefix());
        assertEquals("Prefix string mismatch",
                "", name4.getPrefix());
        assertEquals("Prefix string mismatch",
                "", name5.getPrefix());
    }

    /** */
    @Test
    public void testGetSuffix() {
        assertEquals("Suffix string mismatch",
                "Jr.", name1.getSuffix());
        assertEquals("Suffix string mismatch",
                "", name2.getSuffix());
        assertEquals("Suffix string mismatch",
                "", name3.getSuffix());
        assertEquals("Suffix string mismatch",
                "", name4.getSuffix());
        assertEquals("Suffix string mismatch",
                "", name4.getSuffix());
        assertEquals("Suffix string mismatch",
                "Foo", name5.getSuffix());
    }

    /** */
    @Test
    public void testNameGedObject() {
        final Root localRoot = new Root(null, "Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert("I1", localPerson);
        final Name name = new Name(localPerson);
        localPerson.insert(name);
        assertEquals("Parent mismatch", localPerson, name.getParent());
        assertEquals("Name string mismatch", "", name.getString());
    }

    /** */
    @Test
    public void testNameGedObjectString() {
        final Root localRoot = new Root(null, "Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert("I1", localPerson);
        final Name name = new Name(localPerson, "Karl/Schoeller/Jr.");
        localPerson.insert(name);
        assertEquals("Parent mismatch", localPerson, name.getParent());
        assertEquals("Name string mismatch", "Karl/Schoeller/Jr.",
                name.getString());
        assertEquals("Prefix mismatch", "Karl", name.getPrefix());
        assertEquals("Surname mismatch", "Schoeller", name.getSurname());
        assertEquals("Suffix mismatch", "Jr.", name.getSuffix());
    }
}
