package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
public final class PersonTest {
    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";
    /** */
    private final transient Root root = new Root(null);
    /** */
    private final transient Person person1 = new Person(null);
    /** */
    private final transient Name name1 = new Name(person1,
            "Richard John/Schoeller/");
    /** */
    private final transient Person person2 = new Person(root);
    /** */
    private final transient Name name2 = new Name(person2,
            "Lisa Hope/Robinson/");
    /** */
    private final transient Person person3 = new Person(root);
    /** */
    private final transient Name name3 = new Name(person3,
            "Karl Frederick/Schoeller/Jr.");
    /** */
    private final transient Person person4 = new Person(root);
    /** */
    private final transient Attribute birth4 = new Attribute(person4, "Birth");
    /** */
    private final transient Attribute death4 = new Attribute(person4, "Death");
    /** */
    private final transient Person person5 = new Person(root);
    /** */
    private final transient Name name5 = new Name(person5,
            "Whosis/Schoeller/Jr./Huh?");
    /** */
    private final transient Attribute birth5 = new Attribute(person5, "Birth");
    /** */
    private final transient Date birthDate5 =
            new Date(birth5, "1 JAN 1900");
    /** */
    private final transient Attribute death5 = new Attribute(person5, "Death");
    /** */
    private final transient Date deathDate5 =
            new Date(death5, "1 JAN 1950");
    /** */
    private final transient Family family6 = new Family(root,
            new ObjectId("F6"));
    /** */
    private final transient FamC famc3 = new FamC(person3, "FamC",
            new ObjectId("F6"));
    /** */
    private final transient Person person6 = new Person(root);
    /** */
    private final transient FamS fams6 = new FamS(person6, "FamS",
            new ObjectId("F6"));
    /** */
    private final transient Person person7 = new Person(root);
    /** */
    private final transient FamS fams7 = new FamS(person7, "FamS",
            new ObjectId("F6"));
    /** */
    private final transient Husband husband6 = new Husband(family6, "Husband",
            new ObjectId("I6"));
    /** */
    private final transient Wife wife7 = new Wife(family6, "Wife",
            new ObjectId("I7"));
    /** */
    private final transient Child child3 = new Child(family6, "Child",
            new ObjectId("I3"));
    /** */
    private final transient Attribute attr = new Attribute(person1,
            "Restriction", "confidential");

    /** */
    @Before
    public void setUp() {
        root.insert("I1", person1);
        person1.insert(name1);
        person1.insert(attr);
        root.insert("I2", person2);
        person2.insert(name2);
        root.insert("I3", person3);
        person3.insert(name3);
        root.insert("I4", person4);
        person4.insert(birth4);
        person4.insert(death4);
        root.insert("I5", person5);
        person5.insert(name5);
        person5.insert(birth5);
        person5.insert(death5);
        birth5.insert(birthDate5);
        death5.insert(deathDate5);
        // Make people and family findable.
        root.insert("I6", person6);
        root.insert("I7", person7);
        root.insert("F6", family6);
        // People point to family.
        person3.insert(famc3);
        person6.insert(fams6);
        person7.insert(fams7);
        // Family points to people.
        family6.insert(husband6);
        family6.insert(wife7);
        family6.insert(child3);
    }

    /** */
    @Test
    public void testDickGetIndexName() {
        assertEquals(UNEXPECTED_STRING, "Schoeller, Richard John",
                person1.getIndexName());
    }

    /** */
    @Test
    public void testLisaGetIndexName() {
        assertEquals(UNEXPECTED_STRING, "Robinson, Lisa Hope",
                person2.getIndexName());
    }

    /** */
    @Test
    public void testKarlGetIndexName() {
        assertEquals(UNEXPECTED_STRING, "Schoeller, Karl Frederick, Jr.",
                person3.getIndexName());
    }

    /** */
    @Test
    public void testUnknownGetIndexName() {
        assertEquals(UNEXPECTED_STRING, "?, ?", person4.getIndexName());
    }

    /** */
    @Test
    public void testWhosisGetIndexName() {
        assertEquals(UNEXPECTED_STRING, "Schoeller, Whosis, Jr./Huh?",
                person5.getIndexName());
    }

    /** */
    @Test
    public void testDickGetSurname() {
        assertEquals(UNEXPECTED_STRING, "Schoeller", person1.getSurname());
    }

    /** */
    @Test
    public void testLisaGetSurname() {
        assertEquals(UNEXPECTED_STRING, "Robinson", person2.getSurname());
    }

    /** */
    @Test
    public void testKarlGetSurname() {
        assertEquals(UNEXPECTED_STRING, "Schoeller", person3.getSurname());
    }

    /** */
    @Test
    public void testUnknownGetSurname() {
        assertEquals(UNEXPECTED_STRING, "?", person4.getSurname());
    }

    /** */
    @Test
    public void testWhosisGetSurname() {
        assertEquals(UNEXPECTED_STRING, "Schoeller", person5.getSurname());
    }

    /** */
    @Test
    public void testDickGetSurnameLetter() {
        assertEquals(UNEXPECTED_STRING, "S", person1.getSurnameLetter());
    }

    /** */
    @Test
    public void testLisaGetSurnameLetter() {
        assertEquals(UNEXPECTED_STRING, "R", person2.getSurnameLetter());
    }

    /** */
    @Test
    public void testKarlGetSurnameLetter() {
        assertEquals(UNEXPECTED_STRING, "S", person3.getSurnameLetter());
    }

    /** */
    @Test
    public void testUnknownGetSurnameLetter() {
        assertEquals(UNEXPECTED_STRING, "?", person4.getSurnameLetter());
    }

    /** */
    @Test
    public void testWhosisGetSurnameLetter() {
        assertEquals(UNEXPECTED_STRING, "S", person5.getSurnameLetter());
    }

    /** */
    @Test
    public void testDickGetBirthDate() {
        // TODO put dates on Dick and then block with confidentiality.
        assertEquals("", person1.getBirthDate());
    }

    /** */
    @Test
    public void testUnknownGetBirthDate() {
        assertEquals("", person4.getBirthDate());
    }

    /** */
    @Test
    public void testWhosisGetBirthDate() {
        assertEquals("1 JAN 1900", person5.getBirthDate());
    }

    /** */
    @Test
    public void testDickGetBirthYear() {
        assertEquals("", person1.getBirthDate());
    }

    /** */
    @Test
    public void testGetUnknownBirthYear() {
        assertEquals("", person4.getBirthDate());
    }

    /** */
    @Test
    public void testWhosisGetBirthYear() {
        assertEquals("1900", person5.getBirthYear());
    }

    /** */
    @Test
    public void testDickGetSortDate() {
        assertEquals("", person1.getBirthDate());
    }

    /** */
    @Test
    public void testUnknownGetSortDate() {
        assertEquals("", person4.getBirthDate());
    }

    /** */
    @Test
    public void testWhosisGetSortDate() {
        assertEquals("19000101", person5.getSortDate());
    }

    /** */
    @Test
    public void testDickGetDeathDate() {
        assertEquals("", person1.getDeathDate());
    }

    /** */
    @Test
    public void testUnknownGetDeathDate() {
        assertEquals("", person4.getDeathDate());
    }

    /** */
    @Test
    public void testWhosisGetDeathDate() {
        assertEquals("1 JAN 1950", person5.getDeathDate());
    }

    /** */
    @Test
    public void testDickGetDeathYear() {
        assertEquals("", person1.getDeathDate());
    }

    /** */
    @Test
    public void testUnknownGetDeathYear() {
        assertEquals("", person4.getDeathDate());
    }

    /** */
    @Test
    public void testWhosisGetDeathYear() {
        assertEquals("1950", person5.getDeathYear());
    }

    /** */
    @Test
    public void testGetFatherMother() {
        assertEquals(person6, person3.getFather());
        assertEquals(person7, person3.getMother());
        assertFalse(person6.getMother().isSet());
        assertFalse(person6.getFather().isSet());
        // TODO need tests with person in a FamC with father and no mother and
        // visa versa.
    }

    /** */
    @Test
    public void testGetFamilies() {
        // This is associated with families in which this person is a spouse.
        final List<Family> list6 = person6.getFamilies(new ArrayList<Family>());
        assertTrue(list6.contains(family6));

        final List<Family> list7 = person7.getFamilies(new ArrayList<Family>());
        assertTrue(list7.contains(family6));
    }

    /** */
    @Test
    public void testGetSpousesFailsWithoutPersonStrings() {
        final List<Person> list6 = person6.getSpouses(new ArrayList<Person>(),
                person6);
        assertEquals(0, list6.size());
    }

    /** */
    @Test
    public void testGetSpouses() {
        person6.setString("I6");
        person7.setString("I7");
        final List<Person> list6 = person6.getSpouses(new ArrayList<Person>(),
                person6);
        assertTrue(list6.contains(person7));
        person6.setString("");
        person7.setString("");
    }

    /** */
    @Test
    public void testGetChildren() {
        List<Person> list6 = person6.getChildren();
        assertTrue(list6.contains(person3));
        list6 = person6.getChildren();
        assertTrue(list6.contains(person3));
        final List<Person> list7 = person7.getChildren();
        assertTrue(list7.contains(person3));
    }

    /** */
    @Test
    public void testPersonGedObject() {
        final Root localRoot = new Root(null, "Root");
        final Person person = new Person(localRoot);
        localRoot.insert("I1", person);
        assertTrue("Person string should be empty", person.getString()
                .isEmpty());
        assertEquals(person, localRoot.find("I1"));
        assertFalse(person.getFather().isSet());
        assertFalse(person.getMother().isSet());
    }

    /** */
    @Test
    public void testPersonGedObjectString() {
        final Root localRoot = new Root(null, "Root");
        final Person person = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert("I1", person);
        assertEquals("I1", person.getString());
        assertEquals(person, localRoot.find("I1"));
    }

    /** */
    @Test
    public void testLetterFinder() {
        final String[] expected = {
                "?", "R", "S"
        };
        final Collection<String> letters = person6.findSurnameInitialLetters();
        int i = 0;
        for (final String letter : letters) {
            assertEquals(expected[i++], letter);
        }
    }

    /** */
    @Test
    public void testBySurnameFinderFound() {
        final String[] expected = {
                "Schoeller, Karl Frederick, Jr.",
                "Schoeller, Richard John",
                "Schoeller, Whosis, Jr./Huh?",
        };
        final Collection<Person> persons = person6.findBySurname("Schoeller");
        int i = 0;
        for (final Person person : persons) {
            assertEquals(expected[i++], person.getIndexName());
        }
    }

    /** */
    @Test
    public void testBySurnameFinderNotFound() {
        final Collection<Person> persons = person6.findBySurname("Mumble");
        assertEquals(0, persons.size());
    }

    /** */
    @Test
    public void testBeginsWithFound() {
        final String[] expected = {
                "Schoeller",
        };
        final Collection<String> surnames = person6
                .findBySurnamesBeginWith("S");
        int i = 0;
        for (final String surname : surnames) {
            assertEquals(expected[i++], surname);
        }
    }

    /** */
    @Test
    public void testBeginsWithNotFound() {
        final Collection<String> surnames = person6
                .findBySurnamesBeginWith("Q");
        assertEquals(0, surnames.size());
    }

    /** */
    @Test
    public void testIsConfidential() {
        assertTrue("Should be confidential", person1.isConfidential());
    }

    /** */
    @Test
    public void testIsNotConfidential() {
        assertFalse("Should not be confidential", person2.isConfidential());
    }
}
