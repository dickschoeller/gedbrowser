package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount",
    "PMD.GodClass",
    "PMD.TooManyFields" })
public final class PersonTest {
    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";
    /** */
    private transient Person person1;
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient Person person4;
    /** */
    private transient Person person5;
    /** */
    private transient Family family6;
    /** */
    private transient Person person6;
    /** */
    private transient Person person7;

    /** */
    @Before
    public void setUp() {
        final Root root = new Root();
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        person1 = builder.createPerson("I1", "Richard John/Schoeller/");
        final Attribute attr =
                new Attribute(person1, "Restriction", "confidential");
        person1.insert(attr);

        person2 = builder.createPerson("I2", "Lisa Hope/Robinson/");
        person3 = builder.createPerson("I3", "Karl Frederick/Schoeller/Jr.");
        person4 = builder.createPerson("I4");
        builder.createPersonEvent(person4, "Birth");
        builder.createPersonEvent(person4, "Death");

        person5 = builder.createPerson("I5", "Whosis/Schoeller/Jr./Huh?");
        builder.createPersonEvent(person5, "Birth", "1 JAN 1900");
        builder.createPersonEvent(person5, "Death", "1 JAN 1950");

        family6 = builder.createFamily("F6");
        builder.addChildToFamily(family6, person3);

        person6 = builder.createPerson("I6");
        person7 = builder.createPerson("I7");

        builder.addHusbandToFamily(family6, person6);
        builder.addWifeToFamily(family6, person7);

        final Person person8 = builder.createPerson("I8", "Same/Name/");
        builder.createPersonEvent(person8, "Birth", "1 JAN 1950");
        final Person person9 = builder.createPerson("I9", "Same/Name/");
        builder.createPersonEvent(person9, "Birth", "1 JAN 1940");
        final Person person10 = builder.createPerson("I10", "Same/Name/");
        builder.createPersonEvent(person10, "Birth", "1 JAN 1950");
        final Person person11 = builder.createPerson("I11", "Different/Name/");
        builder.createPersonEvent(person11, "Birth", "1 JAN 1930");
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
        assertEquals("Expected empty birth date", "", getBirthDate(person1));
    }

    /** */
    @Test
    public void testUnknownGetBirthDate() {
        assertEquals("Expected empty birth date", "", getBirthDate(person4));
    }

    /** */
    @Test
    public void testWhosisGetBirthDate() {
        assertEquals("Birth date mismatch",
                "1 JAN 1900", getBirthDate(person5));
    }

    /** */
    @Test
    public void testDickGetBirthYear() {
        assertEquals("Expected empty birth year", "", getBirthDate(person1));
    }

    /** */
    @Test
    public void testGetUnknownBirthYear() {
        assertEquals("Expected empty birth year", "", getBirthDate(person4));
    }

    /** */
    @Test
    public void testWhosisGetBirthYear() {
        assertEquals("Birth year mismatch", "1900", getBirthYear(person5));
    }

    /** */
    @Test
    public void testDickGetSortDate() {
        assertEquals("Expected empty sort date", "", getBirthDate(person1));
    }

    /** */
    @Test
    public void testUnknownGetSortDate() {
        assertEquals("Expected empty sort date", "", getBirthDate(person4));
    }

    /** */
    @Test
    public void testWhosisGetSortDate() {
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        person5.accept(visitor);
        assertEquals("Sort date mismatch", "19000101", visitor.getSortDate());
    }

    /** */
    @Test
    public void testDickGetDeathDate() {
        assertEquals("Expected emtpy death date", "", getDeathDate(person1));
    }

    /** */
    @Test
    public void testUnknownGetDeathDate() {
        assertEquals("Expected emtpy death date", "", getDeathDate(person4));
    }

    /** */
    @Test
    public void testWhosisGetDeathDate() {
        assertEquals("Death date mismatch", "1 JAN 1950",
                getDeathDate(person5));
    }

    /** */
    @Test
    public void testDickGetDeathYear() {
        assertEquals("Expected empty death year", "", getDeathYear(person1));
    }

    /** */
    @Test
    public void testUnknownGetDeathYear() {
        assertEquals("Expected empty death year", "", getDeathYear(person4));
    }

    /** */
    @Test
    public void testWhosisGetDeathYear() {
        assertEquals("Death year mismatch", "1950", getDeathYear(person5));
    }

    /** */
    @Test
    public void testGetFather() {
        final PersonNavigator navigator = new PersonNavigator(person3);
        assertEquals("Expected to find father", person6, navigator.getFather());
    }

    /** */
    @Test
    public void testGetMother() {
        final PersonNavigator navigator = new PersonNavigator(person3);
        assertEquals("Expected to find mother", person7, navigator.getMother());
    }

    /** */
    @Test
    public void testGetFatherUnset() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        assertFalse("Expected not to find father",
                navigator.getFather().isSet());
    }

    /** */
    @Test
    public void testGetMotherUnset() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        assertFalse("Expected not to find mother",
                navigator.getMother().isSet());
    }

    /** */
    @Test
    public void testGetHusbandsFamily() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        final List<Family> list6 = navigator.getFamilies();
        assertTrue(
                "Should have found husband's family", list6.contains(family6));
    }

    /** */
    @Test
    public void testGetWifesFamily() {
        final PersonNavigator navigator = new PersonNavigator(person7);
        final List<Family> list7 = navigator.getFamilies();
        assertTrue("Should have found wife's family", list7.contains(family6));
    }

    /** */
    @Test
    public void testGetSpousesFailsWithoutPersonStrings() {
        // Make these persons malformed.
        person6.setString("");
        person7.setString("");
        final PersonNavigator navigator = new PersonNavigator(person7);
        final List<Person> list6 = navigator.getSpouses();
        assertEquals("Screwing with ID strings should have hidden these",
                0, list6.size());
    }

    /** */
    @Test
    public void testGetSpouses() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        final List<Person> list6 = navigator.getSpouses();
        assertTrue("Should have found spouse", list6.contains(person7));
    }

    /** */
    @Test
    public void testGetFathersChildren() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        final List<Person> list6 = navigator.getChildren();
        assertTrue("Expected to find person3", list6.contains(person3));
    }

    /** */
    @Test
    public void testGetMothersChildren() {
        final PersonNavigator navigator = new PersonNavigator(person7);
        final List<Person> list7 = navigator.getChildren();
        assertTrue("Expected to find person3", list7.contains(person3));
    }

    /** */
    @Test
    public void testPersonGedObjectMissingID() {
        final Person person = new Person();
        assertTrue("Person string should be empty", person.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testPersonGedObjectMissingIDNoFather() {
        final PersonNavigator navigator = new PersonNavigator(new Person());
        assertFalse("Expected no father", navigator.getFather().isSet());
    }

    /** */
    @Test
    public void testPersonGedObjectMissingIDNoMother() {
        final PersonNavigator navigator = new PersonNavigator(new Person());
        assertFalse("Expected no mother", navigator.getMother().isSet());
    }

    /** */
    @Test
    public void testPersonGedObjectString() {
        final Root localRoot = new Root("Root");
        final Person person = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(person);
        assertEquals("Expected ID to match", "I1", person.getString());
    }

    /** */
    @Test
    public void testPersonFindGedObjectString() {
        final Root localRoot = new Root("Root");
        final Person person = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(person);
        assertEquals("Expected to find person by ID",
                person, localRoot.find("I1"));
    }

    /** */
    @Test
    public void testLetterFinder() {
        final String[] expected = {
                "?", "N", "R", "S"
        };
        final Collection<String> letters = person6.findSurnameInitialLetters();
        int i = 0;
        for (final String letter : letters) {
            assertEquals(
                    "Expected to find " + expected[i] + " among letters",
                    expected[i++], letter);
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
            assertEquals(
                    "Expected to find " + expected[i] + " among Schoellers",
                    expected[i++], person.getIndexName());
        }
    }

    /** */
    @Test
    public void testBySurnameFinderNotFound() {
        final Collection<Person> persons = person6.findBySurname("Mumble");
        assertEquals("Should have found no persons named Mumble",
                0, persons.size());
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
            assertEquals(
                    "Expected to find " + expected[i] + " among S surnames",
                    expected[i++], surname);
        }
    }

    /** */
    @Test
    public void testOrder() {
        // Note I10 sorts ahead of I8.
        final String[] expected = {"I11", "I9", "I10", "I8"};
        final Collection<Person> persons = person6.findBySurname("Name");
        int i = 0;
        for (final Person person : persons) {
            assertEquals("ID out of order", expected[i++], person.getString());
        }
    }

    /** */
    @Test
    public void testBeginsWithNotFound() {
        final Collection<String> surnames = person6
                .findBySurnamesBeginWith("Q");
        assertTrue("Expected no surnames beginning with Q",
                surnames.isEmpty());
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

    /** */
    @Test
    public void testHasNotDeathAttribute() {
        assertFalse("Should not have death attribute",
                person2.hasDeathAttribute());
    }

    /** */
    @Test
    public void testEmptyDeathYear() {
        assertTrue("Should not have death year",
                getDeathYear(person2).isEmpty());
    }

    /** */
    @Test
    public void testEmptyDeathDate() {
        assertTrue("Should not have death date",
                getDeathDate(person2).isEmpty());
    }

    /** */
    @Test
    public void testEmptyBirthDate() {
        assertTrue("Should not have birth date",
                getBirthDate(person2).isEmpty());
    }

    /** */
    @Test
    public void testEmptySortDate() {
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        person2.accept(visitor);
        assertTrue("Should not have sort date",
                visitor.getSortDate().isEmpty());
    }

    /** */
    @Test
    public void testHasDeathAttribute() {
        assertTrue("Should have death attribute", person4.hasDeathAttribute());
    }

    /** */
    @Test
    public void testGetFamiliesCWith() {
        final PersonNavigator navigator = new PersonNavigator(person3);
        assertFalse("Should have a FAMC",
                navigator.getFamiliesC().isEmpty());
    }

    /** */
    @Test
    public void testGetFamiliesCWithout() {
        final PersonNavigator navigator = new PersonNavigator(person1);
        assertTrue("Should not have a FAMC",
                navigator.getFamiliesC().isEmpty());
    }

    /**
     * @param person the person we're checking
     * @return the date string
     */
    private String getBirthDate(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Birth");
        person.accept(birthVisitor);
        return birthVisitor.getDate();
    }

    /**
     * @param person the person we're checking
     * @return the year string
     */
    private String getBirthYear(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Birth");
        person.accept(birthVisitor);
        return birthVisitor.getYear();
    }

    /**
     * @param person the person we're checking
     * @return the date string
     */
    private String getDeathDate(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Death");
        person.accept(birthVisitor);
        return birthVisitor.getDate();
    }

    /**
     * @param person the person we're checking
     * @return the year string
     */
    private String getDeathYear(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Death");
        person.accept(birthVisitor);
        return birthVisitor.getYear();
    }
}
