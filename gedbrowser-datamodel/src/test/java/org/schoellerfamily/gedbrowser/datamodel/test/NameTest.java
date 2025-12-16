package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @BeforeEach
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        final Person person1 = person;
        name1 = builder.addNameToPerson(person1, "Karl/Schoeller/Jr.");
        final Person person2 = person;
        name2 = builder.addNameToPerson(person2, "Karl/Schoeller/");
        final Person person3 = person;
        name3 = builder.addNameToPerson(person3, "Wingnut");
        final Person person4 = person;
        name4 = builder.addNameToPerson(person4, "/Noodle/");
        final Person person5 = person;
        name5 = builder.addNameToPerson(person5, "/Wang/Foo");
    }

    /** */
    @Test
    public void testGetName1() {
        assertEquals(name1, name1.getName(), "Name string mismatch");
    }

    /** */
    @Test
    public void testGetName2() {
        assertEquals(name2, name2.getName(), "Name string mismatch");
    }

    /** */
    @Test
    public void testGetName3() {
        assertEquals(name3, name3.getName(), "Name string mismatch");
    }

    /** */
    @Test
    public void testGetNameP() {
        assertEquals(name1, person.getName(), "Name string mismatch");
    }

    /** */
    @Test
    public void testGetIndexName1() {
        assertEquals("Schoeller, Karl, Jr.", name1.getIndexName(), "Index name string mismatch");
    }

    /** */
    @Test
    public void testGetIndexName2() {
        assertEquals("Schoeller, Karl", name2.getIndexName(), "Index name string mismatch");
    }

    /** */
    @Test
    public void testGetIndexName3() {
        assertEquals("?, Wingnut", name3.getIndexName(), "Index name string mismatch");
    }

    /** */
    @Test
    public void testGetIndexName4() {
        assertEquals("Noodle, ?", name4.getIndexName(), "Index name string mismatch");
    }

    /** */
    @Test
    public void testGetIndexName5() {
        assertEquals("Wang Foo", name5.getIndexName(), "Index name string mismatch");
    }

    /** */
    @Test
    public void testGetSurname1() {
        assertEquals("Schoeller", name1.getSurname(), "Surname string mismatch");
    }

    /** */
    @Test
    public void testGetSurname2() {
        assertEquals("Schoeller", name2.getSurname(), "Surname string mismatch");
    }

    /** */
    @Test
    public void testGetSurname3() {
        assertEquals("?", name3.getSurname(), "Surname string mismatch");
    }

    /** */
    @Test
    public void testGetSurname4() {
        assertEquals("Noodle", name4.getSurname(), "Surname string mismatch");
    }

    /** */
    @Test
    public void testGetSurname5() {
        assertEquals("Wang", name5.getSurname(), "Surname string mismatch");
    }

    /** */
    @Test
    public void testGetPrefix1() {
        assertEquals("Karl", name1.getPrefix(), "Prefix string mismatch");
    }

    /** */
    @Test
    public void testGetPrefix2() {
        assertEquals("Karl", name2.getPrefix(), "Prefix string mismatch");
    }

    /** */
    @Test
    public void testGetPrefix3() {
        assertEquals("Wingnut", name3.getPrefix(), "Prefix string mismatch");
    }

    /** */
    @Test
    public void testGetPrefix4() {
        assertEquals("", name4.getPrefix(), "Prefix string mismatch");
    }

    /** */
    @Test
    public void testGetPrefix5() {
        assertEquals("", name5.getPrefix(), "Prefix string mismatch");
    }

    /** */
    @Test
    public void testGetSuffix() {
        assertEquals("Jr.", name1.getSuffix(), "Suffix string mismatch");
    }

    /** */
    @Test
    public void testGetSuffix1() {
        assertEquals("", name2.getSuffix(), "Suffix string mismatch");
    }

    /** */
    @Test
    public void testGetSuffix2() {
        assertEquals("", name3.getSuffix(), "Suffix string mismatch");
    }

    /** */
    @Test
    public void testGetSuffix3() {
        assertEquals("", name4.getSuffix(), "Suffix string mismatch");
    }

    /** */
    @Test
    public void testGetSuffix4() {
        assertEquals("", name4.getSuffix(), "Suffix string mismatch");
    }

    /** */
    @Test
    public void testGetSuffix5() {
        assertEquals("Foo", name5.getSuffix(), "Suffix string mismatch");
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
        assertEquals(localPerson, name.getParent(), "Parent mismatch");
        assertEquals(expectedString, name.getString(), "Name string mismatch");
        assertEquals(expectedPrefix, name.getPrefix(), "Prefix mismatch");
        assertEquals(expectedSurname, name.getSurname(), "Surname mismatch");
        assertEquals(expectedSuffix, name.getSuffix(), "Suffix mismatch");
    }
}