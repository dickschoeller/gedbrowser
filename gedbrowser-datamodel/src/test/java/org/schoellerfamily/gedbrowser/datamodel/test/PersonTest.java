package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.GodClass", "PMD.TooManyFields" })
final class PersonTest {
    /** */
    private static final String UNEXPECTED_STRING = "Unexpected string returned";
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
    private transient Person person6;
    /** */
    private transient Person person7;

    private final GedObjectBuilder builder = new GedObjectBuilder();

    @BeforeEach
    void setUp() {
        person1 = builder.createPerson("I1", "Richard John/Schoeller/");
        final Attribute attr = new Attribute(person1, "Restriction", "confidential");
        person1.insert(attr);

        person2 = builder.createPerson("I2", "Lisa Hope/Robinson/");
        person3 = builder.createPerson("I3", "Karl Frederick/Schoeller/Jr.");
        person4 = builder.createPerson("I4");

        final Person person13 = person4;
        builder.createPersonEvent(person13, "Birth");

        final Person person14 = person4;
        builder.createPersonEvent(person14, "Death");

        person5 = builder.createPerson("I5", "Whosis/Schoeller/Jr./Huh?");

        final Person person = person5;
        builder.createPersonEvent(person, "Birth", "1 JAN 1900");

        final Person person12 = person5;
        builder.createPersonEvent(person12, "Death", "1 JAN 1950");

        final Family family6 = builder.createFamily("F6");
        final Person person17 = person3;
        builder.addChildToFamily(family6, person17);

        person6 = builder.createPerson("I6");
        person7 = builder.createPerson("I7");
        final Person person15 = person6;

        builder.addHusbandToFamily(family6, person15);
        final Person person16 = person7;
        builder.addWifeToFamily(family6, person16);

        final Person person8 = builder.createPerson("I8", "Same/Name/");
        builder.createPersonEvent(person8, "Birth", "1 JAN 1950");

        final Person person9 = builder.createPerson("I9", "Same/Name/");
        builder.createPersonEvent(person9, "Birth", "1 JAN 1940");

        final Person person10 = builder.createPerson("I10", "Same/Name/");
        builder.createPersonEvent(person10, "Birth", "1 JAN 1950");

        final Person person11 = builder.createPerson("I11", "Different/Name/");
        builder.createPersonEvent(person11, "Birth", "1 JAN 1930");
    }

    @Test
    void testDickGetIndexName() {
        assertEquals("Schoeller, Richard John", person1.getIndexName(), UNEXPECTED_STRING);
    }

    @Test
    void testLisaGetIndexName() {
        assertEquals("Robinson, Lisa Hope", person2.getIndexName(), UNEXPECTED_STRING);
    }

    @Test
    void testKarlGetIndexName() {
        assertEquals("Schoeller, Karl Frederick, Jr.", person3.getIndexName(), UNEXPECTED_STRING);
    }

    @Test
    void testUnknownGetIndexName() {
        assertEquals("?, ?", person4.getIndexName(), UNEXPECTED_STRING);
    }

    @Test
    void testWhosisGetIndexName() {
        assertEquals("Schoeller, Whosis, Jr./Huh?", person5.getIndexName(), UNEXPECTED_STRING);
    }

    @Test
    void testDickGetSurname() {
        assertEquals("Schoeller", person1.getSurname(), UNEXPECTED_STRING);
    }

    @Test
    void testLisaGetSurname() {
        assertEquals("Robinson", person2.getSurname(), UNEXPECTED_STRING);
    }

    @Test
    void testKarlGetSurname() {
        assertEquals("Schoeller", person3.getSurname(), UNEXPECTED_STRING);
    }

    @Test
    void testUnknownGetSurname() {
        assertEquals("?", person4.getSurname(), UNEXPECTED_STRING);
    }

    @Test
    void testWhosisGetSurname() {
        assertEquals("Schoeller", person5.getSurname(), UNEXPECTED_STRING);
    }

    @Test
    void testDickGetSurnameLetter() {
        assertEquals("S", person1.getSurnameLetter(), UNEXPECTED_STRING);
    }

    @Test
    void testLisaGetSurnameLetter() {
        assertEquals("R", person2.getSurnameLetter(), UNEXPECTED_STRING);
    }

    @Test
    void testKarlGetSurnameLetter() {
        assertEquals("S", person3.getSurnameLetter(), UNEXPECTED_STRING);
    }

    @Test
    void testUnknownGetSurnameLetter() {
        assertEquals("?", person4.getSurnameLetter(), UNEXPECTED_STRING);
    }

    @Test
    void testWhosisGetSurnameLetter() {
        assertEquals("S", person5.getSurnameLetter(), UNEXPECTED_STRING);
    }

    @Test
    void testDickGetBirthDate() {
        assertEquals("", getBirthDate(person1), "Expected empty birth date");
    }

    @Test
    void testUnknownGetBirthDate() {
        assertEquals("", getBirthDate(person4), "Expected empty birth date");
    }

    @Test
    void testWhosisGetBirthDate() {
        assertEquals("1 JAN 1900", getBirthDate(person5), "Birth date mismatch");
    }

    @Test
    void testDickGetBirthYear() {
        assertEquals("", getBirthDate(person1), "Expected empty birth year");
    }

    @Test
    void testGetUnknownBirthYear() {
        assertEquals("", getBirthDate(person4), "Expected empty birth year");
    }

    @Test
    void testWhosisGetBirthYear() {
        assertEquals("1900", getBirthYear(person5), "Birth year mismatch");
    }

    @Test
    void testDickGetSortDate() {
        assertEquals("", getBirthDate(person1), "Expected empty sort date");
    }

    @Test
    void testUnknownGetSortDate() {
        assertEquals("", getBirthDate(person4), "Expected empty sort date");
    }

    @Test
    void testWhosisGetSortDate() {
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        person5.accept(visitor);
        assertEquals("19000101", visitor.getSortDate(), "Sort date mismatch");
    }

    @Test
    void testDickGetDeathDate() {
        assertEquals("", getDeathDate(person1), "Expected emtpy death date");
    }

    @Test
    void testUnknownGetDeathDate() {
        assertEquals("", getDeathDate(person4), "Expected emtpy death date");
    }

    @Test
    void testWhosisGetDeathDate() {
        assertEquals("1 JAN 1950", getDeathDate(person5), "Death date mismatch");
    }

    @Test
    void testDickGetDeathYear() {
        assertEquals("", getDeathYear(person1), "Expected empty death year");
    }

    @Test
    void testUnknownGetDeathYear() {
        assertEquals("", getDeathYear(person4), "Expected empty death year");
    }

    @Test
    void testWhosisGetDeathYear() {
        assertEquals("1950", getDeathYear(person5), "Death year mismatch");
    }

    @Test
    void testGetSpousesFailsWithoutPersonStrings() {
        // Make these persons malformed.
        person6.setString("");
        person7.setString("");
        final PersonNavigator navigator = new PersonNavigator(person7);
        final List<Person> list6 = navigator.getSpouses();
        assertEquals(0, list6.size(), "Screwing with ID strings should have hidden these");
    }

    @Test
    void testGetSpouses() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        final List<Person> list6 = navigator.getSpouses();
        assertTrue(list6.contains(person7), "Should have found spouse");
    }

    @Test
    void testGetFathersChildren() {
        final PersonNavigator navigator = new PersonNavigator(person6);
        final List<Person> list6 = navigator.getChildren();
        assertTrue(list6.contains(person3), "Expected to find person3");
    }

    @Test
    void testGetMothersChildren() {
        final PersonNavigator navigator = new PersonNavigator(person7);
        final List<Person> list7 = navigator.getChildren();
        assertTrue(list7.contains(person3), "Expected to find person3");
    }

    @Test
    void testPersonGedObjectMissingID() {
        final Person person = new Person();
        assertTrue(person.getString().isEmpty(), "Person string should be empty");
    }

    @Test
    void testPersonGedObjectMissingIDNoFather() {
        final PersonNavigator navigator = new PersonNavigator(new Person());
        assertFalse(navigator.getFather().isSet(), "Expected no father");
    }

    @Test
    void testPersonGedObjectMissingIDNoMother() {
        final PersonNavigator navigator = new PersonNavigator(new Person());
        assertFalse(navigator.getMother().isSet(), "Expected no mother");
    }

    @Test
    void testPersonGedObjectString() {
        final Root localRoot = new Root("Root");
        final Person person = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(person);
        assertEquals("I1", person.getString(), "Expected ID to match");
    }

    @Test
    void testPersonFindGedObjectString() {
        final Root localRoot = new Root("Root");
        final Person person = new Person(localRoot, new ObjectId("I1"));
        localRoot.insert(person);
        assertEquals(person, localRoot.find("I1"), "Expected to find person by ID");
    }

    @Test
    void testLetterFinder() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expected = { "?", "N", "R", "S" };
        final Collection<String> letters = person6.findSurnameInitialLetters();
        int i = 0;
        for (final String letter : letters) {
            assertEquals(expected[i++], letter,
                "Expected to find " + expected[i - 1] + " among letters");
        }
    }

    @Test
    void testBySurnameFinderFound() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expected = { "Schoeller, Karl Frederick, Jr.", "Schoeller, Richard John",
            "Schoeller, Whosis, Jr./Huh?", };
        final Collection<Person> persons = person6.findBySurname("Schoeller");
        int i = 0;
        for (final Person person : persons) {
            assertEquals(expected[i++], person.getIndexName(),
                "Expected to find " + expected[i - 1] + " among Schoellers");
        }
    }

    @Test
    void testBySurnameFinderNotFound() {
        final Collection<Person> persons = person6.findBySurname("Mumble");
        assertEquals(0, persons.size(), "Should have found no persons named Mumble");
    }

    @Test
    void testBeginsWithFound() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expected = { "Schoeller", };
        final Collection<String> surnames = person6.findBySurnamesBeginWith("S");
        int i = 0;
        for (final String surname : surnames) {
            assertEquals(expected[i++], surname,
                "Expected to find " + expected[i - 1] + " among S surnames");
        }
    }

    @Test
    void testOrder() {
        // Note I10 sorts ahead of I8.
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expected = { "I11", "I9", "I10", "I8" };
        final Collection<Person> persons = person6.findBySurname("Name");
        int i = 0;
        for (final Person person : persons) {
            assertEquals(expected[i++], person.getString(), "ID out of order");
        }
    }

    @Test
    void testBeginsWithNotFound() {
        final Collection<String> surnames = person6.findBySurnamesBeginWith("Q");
        assertTrue(surnames.isEmpty(), "Expected no surnames beginning with Q");
    }

    @Test
    void testIsConfidential() {
        final PersonVisitor visitor = new PersonVisitor();
        person1.accept(visitor);
        assertTrue(visitor.isConfidential(), "Should be confidential");
    }

    @Test
    void testIsNotConfidential() {
        final PersonVisitor visitor = new PersonVisitor();
        person2.accept(visitor);
        assertFalse(visitor.isConfidential(), "Should not be confidential");
    }

    @Test
    void testHasNotDeathAttribute() {
        final PersonVisitor visitor = new PersonVisitor();
        person2.accept(visitor);
        assertFalse(visitor.hasDeathAttribute(), "Should not have death attribute");
    }

    @Test
    void testEmptyDeathYear() {
        assertTrue(getDeathYear(person2).isEmpty(), "Should not have death year");
    }

    @Test
    void testEmptyDeathDate() {
        assertTrue(getDeathDate(person2).isEmpty(), "Should not have death date");
    }

    @Test
    void testEmptyBirthDate() {
        assertTrue(getBirthDate(person2).isEmpty(), "Should not have birth date");
    }

    @Test
    void testEmptySortDate() {
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        person2.accept(visitor);
        assertTrue(visitor.getSortDate().isEmpty(), "Should not have sort date");
    }

    @Test
    void testHasDeathAttribute() {
        final PersonVisitor visitor = new PersonVisitor();
        person4.accept(visitor);
        assertTrue(visitor.hasDeathAttribute(), "Should have death attribute");
    }

    @Test
    void testGetFamiliesCWith() {
        final PersonNavigator navigator = new PersonNavigator(person3);
        assertFalse(navigator.getFamiliesC().isEmpty(), "Should have a FAMC");
    }

    @Test
    void testGetFamiliesCWithout() {
        final PersonNavigator navigator = new PersonNavigator(person1);
        assertTrue(navigator.getFamiliesC().isEmpty(), "Should not have a FAMC");
    }

    private String getBirthDate(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Birth");
        person.accept(birthVisitor);
        return birthVisitor.getDate();
    }

    private String getBirthYear(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Birth");
        person.accept(birthVisitor);
        return birthVisitor.getYear();
    }

    private String getDeathDate(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Death");
        person.accept(birthVisitor);
        return birthVisitor.getDate();
    }

    private String getDeathYear(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Death");
        person.accept(birthVisitor);
        return birthVisitor.getYear();
    }
}
