package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class NameTest {
    /** */
    private transient Person person;
    /** */
    private transient Name name1;
    /** */
    private transient Name name2;
    /** */
    private transient Name name3;
    /** */
    private transient Name name4;
    /** */
    private transient Name name5;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.getPersonBuilder().createPerson("I1");
        final Person person1 = person;
        name1 = builder.getPersonBuilder().addNameToPerson(
                person1, "Karl/Schoeller/Jr.");
        final Person person2 = person;
        name2 = builder.getPersonBuilder().addNameToPerson(
                person2, "Karl/Schoeller/");
        final Person person3 = person;
        name3 = builder.getPersonBuilder().addNameToPerson(
                person3, "Wingnut");
        final Person person4 = person;
        name4 = builder.getPersonBuilder().addNameToPerson(
                person4, "/Noodle/");
        final Person person5 = person;
        name5 = builder.getPersonBuilder().addNameToPerson(
                person5, "/Wang/Foo");
    }

    /** */
    @Test
    public void testGetName1() {
        assertEquals("Name string mismatch", name1, name1.getName());
    }

    /** */
    @Test
    public void testGetName2() {
        assertEquals("Name string mismatch", name2, name2.getName());
    }

    /** */
    @Test
    public void testGetName3() {
        assertEquals("Name string mismatch", name3, name3.getName());
    }

    /** */
    @Test
    public void testGetNameP() {
        assertEquals("Name string mismatch", name1, person.getName());
    }

    /** */
    @Test
    public void testGetIndexName1() {
        assertEquals("Index name string mismatch",
                "Schoeller, Karl, Jr.", name1.getIndexName());
    }

    /** */
    @Test
    public void testGetIndexName2() {
        assertEquals("Index name string mismatch",
                "Schoeller, Karl", name2.getIndexName());
    }

    /** */
    @Test
    public void testGetIndexName3() {
        assertEquals("Index name string mismatch",
                "?, Wingnut", name3.getIndexName());
    }

    /** */
    @Test
    public void testGetIndexName4() {
        assertEquals("Index name string mismatch",
                "Noodle, ?", name4.getIndexName());
    }

    /** */
    @Test
    public void testGetIndexName5() {
        assertEquals("Index name string mismatch",
                "Wang Foo", name5.getIndexName());
    }

    /** */
    @Test
    public void testGetSurname1() {
        assertEquals("Surname string mismatch",
                "Schoeller", name1.getSurname());
    }

    /** */
    @Test
    public void testGetSurname2() {
        assertEquals("Surname string mismatch",
                "Schoeller", name2.getSurname());
    }

    /** */
    @Test
    public void testGetSurname3() {
        assertEquals("Surname string mismatch",
                "?", name3.getSurname());
    }

    /** */
    @Test
    public void testGetSurname4() {
        assertEquals("Surname string mismatch",
                "Noodle", name4.getSurname());
    }

    /** */
    @Test
    public void testGetSurname5() {
        assertEquals("Surname string mismatch",
                "Wang", name5.getSurname());
    }

    /** */
    @Test
    public void testGetPrefix1() {
        assertEquals("Prefix string mismatch",
                "Karl", name1.getPrefix());
    }

    /** */
    @Test
    public void testGetPrefix2() {
        assertEquals("Prefix string mismatch",
                "Karl", name2.getPrefix());
    }

    /** */
    @Test
    public void testGetPrefix3() {
        assertEquals("Prefix string mismatch",
                "Wingnut", name3.getPrefix());
    }

    /** */
    @Test
    public void testGetPrefix4() {
        assertEquals("Prefix string mismatch",
                "", name4.getPrefix());
    }

    /** */
    @Test
    public void testGetPrefix5() {
        assertEquals("Prefix string mismatch",
                "", name5.getPrefix());
    }

    /** */
    @Test
    public void testGetSuffix() {
        assertEquals("Suffix string mismatch",
                "Jr.", name1.getSuffix());
    }

    /** */
    @Test
    public void testGetSuffix1() {
        assertEquals("Suffix string mismatch",
                "", name2.getSuffix());
    }

    /** */
    @Test
    public void testGetSuffix2() {
        assertEquals("Suffix string mismatch",
                "", name3.getSuffix());
    }

    /** */
    @Test
    public void testGetSuffix3() {
        assertEquals("Suffix string mismatch",
                "", name4.getSuffix());
    }

    /** */
    @Test
    public void testGetSuffix4() {
        assertEquals("Suffix string mismatch",
                "", name4.getSuffix());
    }

    /** */
    @Test
    public void testGetSuffix5() {
        assertEquals("Suffix string mismatch",
                "Foo", name5.getSuffix());
    }

    /** */
    @Test
    public void testNameGedObject() {
        final Root localRoot = new Root("Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(localPerson);
        final Name name = new Name(localPerson);
        localPerson.insert(name);
        assertMatch(localPerson, name, "", "", "?", "");
    }

    /** */
    @Test
    public void testNameGedObjectString() {
        final Root localRoot = new Root("Root");
        final Person localPerson = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(localPerson);
        final Name name = new Name(localPerson, "Karl/Schoeller/Jr.");
        localPerson.insert(name);
        assertMatch(localPerson, name, "Karl/Schoeller/Jr.", "Karl",
                "Schoeller", "Jr.");
    }

    /**
     * @param localPerson the person to test
     * @param name the name object
     * @param expectedString the expected getString
     * @param expectedPrefix the expected getPrefix
     * @param expectedSurname the expected getSurname
     * @param expectedSuffix the expected getSuffix
     */
    private void assertMatch(final Person localPerson, final Name name,
            final String expectedString, final String expectedPrefix,
            final String expectedSurname, final String expectedSuffix) {
        assertEquals("Parent mismatch", localPerson, name.getParent());
        assertEquals("Name string mismatch", expectedString,
                name.getString());
        assertEquals("Prefix mismatch", expectedPrefix, name.getPrefix());
        assertEquals("Surname mismatch", expectedSurname, name.getSurname());
        assertEquals("Suffix mismatch", expectedSuffix, name.getSuffix());
    }
}
