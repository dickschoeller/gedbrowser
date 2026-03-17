package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonComparator;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Contains tests for person comparator.
 *
 * @author Richard Schoeller
 */
class PersonComparatorTest {
    /** */
    private Person person1;
    /** */
    private Person person2;
    /** */
    private Person person3;
    /** */
    private Person person4;
    /** */
    private Person person5;
    /** */
    private PersonComparator comparator;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson("I1",
                "J. Random/Schoeller/");
        person2 = builder.createPerson("I2",
                "Anonymous/Schoeller/");
        person3 = builder.createPerson("I3",
                "Richard/Schoeller/");
        final Person person = person3;
        final Attribute birth3 = builder
                .createPersonEvent(person, "Birth");
        builder.addDateToGedObject(birth3, "12 OCT 1492");
        person4 = builder.createPerson("I4",
                "Richard/Schoeller/");
        final Person person6 = person4;
        final Attribute birth4 = builder
                .createPersonEvent(person6, "Birth");
        builder.addDateToGedObject(birth4, "12 OCT 1492");
        person5 = builder.createPerson("I5",
                "Richard/Schoeller/");
        final Person person7 = person5;
        final Attribute birth5 = builder
                .createPersonEvent(person7, "Birth");
        builder.addDateToGedObject(birth5, "12 OCT 1942");
        comparator = new PersonComparator();
    }

    @Test
    void testSamePerson() {
        assertEquals(0, comparator.compare(person1, person1), "Same person should match");
    }

    @Test
    void testDifferentNamePositive() {
        final String person1IndexName = reportString(person1);
        final String person2IndexName = reportString(person2);
        assertTrue(comparator.compare(person1, person2) > 0,
                person1IndexName + " should be after " + person2IndexName);
    }

    @Test
    void testDifferentNameNegative() {
        final String person1IndexName = reportString(person1);
        final String person2IndexName = reportString(person2);
        assertTrue(comparator.compare(person2, person1) < 0,
                person2IndexName + " should be before " + person1IndexName);
    }

    @Test
    void testSameNameDifferentDatePositive() {
        final String person4IndexName = reportString(person4);
        final String person5IndexName = reportString(person5);
        assertTrue(comparator.compare(person5, person4) > 0,
                person5IndexName + " should be after " + person4IndexName);
    }

    @Test
    void testSameNameDifferentDateNegative() {
        final String person4IndexName = reportString(person4);
        final String person5IndexName = reportString(person5);
        assertTrue(comparator.compare(person4, person5) < 0,
                person4IndexName + " should be before " + person5IndexName);
    }

    @Test
    void testSameNameSameDatePositive() {
        final String person3IndexName = reportString(person3);
        final String person4IndexName = reportString(person4);
        assertTrue(comparator.compare(person4, person3) > 0,
                person4IndexName + " should be after " + person3IndexName);
    }

    @Test
    void testSameNameSameDateNegative() {
        final String person3IndexName = reportString(person3);
        final String person4IndexName = reportString(person4);
        assertTrue(comparator.compare(person3, person4) < 0,
                person3IndexName + " should be before " + person4IndexName);
    }

    private String reportString(final Person person) {
        final GetDateVisitor visitor = new GetDateVisitor();
        person.accept(visitor);
        final String idString = person.getString();
        final String birthDate = visitor.getDate();
        final String indexName = person.getName().getIndexName();
        return idString + " " + indexName + " " + birthDate;
    }
}
