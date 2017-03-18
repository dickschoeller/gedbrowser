package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonComparator;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
public class PersonComparatorTest {
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

    /** */
    @Before
    public void init() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson1();
        person2 = builder.createPerson2();
        person3 = builder.createPerson("I3", "Richard/Schoeller/");
        final Attribute birth3 = builder.createPersonEvent(person3, "Birth");
        builder.addDateToGedObject(birth3, "12 OCT 1492");
        person4 = builder.createPerson("I4", "Richard/Schoeller/");
        final Attribute birth4 = builder.createPersonEvent(person4, "Birth");
        builder.addDateToGedObject(birth4, "12 OCT 1492");
        person5 = builder.createPerson("I5", "Richard/Schoeller/");
        final Attribute birth5 = builder.createPersonEvent(person5, "Birth");
        builder.addDateToGedObject(birth5, "12 OCT 1942");
        comparator = new PersonComparator();
    }

    /** */
    @Test
    public void testSamePerson() {
        assertEquals("Same person should match",
                0, comparator.compare(person1, person1));
    }

    /** */
    @Test
    public void testDifferentNamePositive() {
        final String person1IndexName = reportString(person1);
        final String person2IndexName = reportString(person2);
        assertTrue(person1IndexName + " should be after " + person2IndexName,
                comparator.compare(person1, person2) > 0);
    }

    /** */
    @Test
    public void testDifferentNameNegative() {
        final String person1IndexName = reportString(person1);
        final String person2IndexName = reportString(person2);
        assertTrue(person2IndexName + " should be before " + person1IndexName,
                comparator.compare(person2, person1) < 0);
    }

    /** */
    @Test
    public void testSameNameDifferentDatePositive() {
        final String person4IndexName = reportString(person4);
        final String person5IndexName = reportString(person5);
        assertTrue(person5IndexName + " should be after " + person4IndexName,
                comparator.compare(person5, person4) > 0);
    }

    /** */
    @Test
    public void testSameNameDifferentDateNegative() {
        final String person4IndexName = reportString(person4);
        final String person5IndexName = reportString(person5);
        assertTrue(person4IndexName + " should be before " + person5IndexName,
                comparator.compare(person4, person5) < 0);
    }

    /** */
    @Test
    public void testSameNameSameDatePositive() {
        final String person3IndexName = reportString(person3);
        final String person4IndexName = reportString(person4);
        assertTrue(person4IndexName + " should be after " + person3IndexName,
                comparator.compare(person4, person3) > 0);
    }

    /** */
    @Test
    public void testSameNameSameDateNegative() {
        final String person3IndexName = reportString(person3);
        final String person4IndexName = reportString(person4);
        assertTrue(person3IndexName + " should be before " + person4IndexName,
                comparator.compare(person3, person4) < 0);
    }

    /**
     * @param person the person
     * @return a string describing the person
     */
    private String reportString(final Person person) {
        final GetDateVisitor visitor = new GetDateVisitor();
        person.accept(visitor);
        final String idString = person.getString();
        final String birthDate = visitor.getDate();
        final String indexName = person.getName().getIndexName();
        return idString + " " + indexName + " " + birthDate;
    }
}
