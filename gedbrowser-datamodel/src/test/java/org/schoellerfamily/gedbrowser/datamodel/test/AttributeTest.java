package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.GetDateVisitor;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class AttributeTest {
    /** */
    private static final String DUMMY = "Dummy";
    /** */
    private static final String HUNDRED_DAY = "31 July 2090";
    /** */
    private static final String POTTER_DAY = "31 July 1990";
    /** */
    private transient Person person1;

    /** */
    @Before
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson1();
    }

    /** */
    @Test
    public void testString() {
        final Attribute job = new Attribute(person1, "Job", "Worked at SAP");
        assertEquals("In should match out", "Worked at SAP", job.getTail());
    }

    /** */
    @Test
    public void testAppendString() {
        final Attribute job = new Attribute(person1, "Job", "Worked at SAP");
        job.appendString(" for 5 years");
        assertEquals("Should have concatenated the segments",
                "Worked at SAP for 5 years", job.getTail());
    }

    /** */
    @Test
    public void testNoBirthNoDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        dummy.accept(visitor);
        assertEquals("Expected empty string", "", visitor.getDate());
    }

    /** */
    @Test
    public void testGetBirthDateNotInserted() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute birth = new Attribute(person1, "Birth");
        new Date(birth, POTTER_DAY);
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        birth.accept(visitor);
        assertEquals("Expected empty string", "", visitor.getDate());
    }

    /** */
    @Test
    public void testGetBirthDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute birth = new Attribute(person1, "Birth");
        final Date date = new Date(birth, POTTER_DAY);
        birth.insert(date);
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        birth.accept(visitor);
        assertEquals("Date's filled in. Should match", POTTER_DAY,
                visitor.getDate());
    }

    /** */
    @Test
    public void testNoDeathNoDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final GetDateVisitor visitor = new GetDateVisitor("Death");
        dummy.accept(visitor);
        assertEquals("Expected empty string", "", visitor.getDate());
    }

    /** */
    @Test
    public void testGetDeathDateNotInserted() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute death = new Attribute(person1, "Death");
        final GetDateVisitor visitor = new GetDateVisitor("Death");
        death.accept(visitor);
        assertEquals("Expected empty string", "", visitor.getDate());
    }

    /** */
    @Test
    public void testGetDeathDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute death = new Attribute(person1, "Death");
        final Date date = new Date(death, HUNDRED_DAY);
        death.insert(date);
        final GetDateVisitor visitor = new GetDateVisitor("Death");
        death.accept(visitor);
        assertEquals("Date's filled in. Should match", HUNDRED_DAY,
                visitor.getDate());
    }

    /** */
    @Test
    public void testGetDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        // TODO this should become unnecessary if I can further restrict the
        // children of an attribute.
        dummy.insert(new Person());
        dummy.insert(dummyDate);
        final GetDateVisitor visitor = new GetDateVisitor();
        dummy.accept(visitor);
        assertEquals("Date mismatch", POTTER_DAY, visitor.getDate());
    }

    /** */
    @Test
    public void testGetDateNullDateString() {
        final Attribute dummy1 = new Attribute(person1, DUMMY);
        final Date dummyDate1 = new Date(dummy1, null);
        dummy1.insert(dummyDate1);
        final GetDateVisitor visitor = new GetDateVisitor();
        dummy1.accept(visitor);
        assertEquals("Expected empty date string", "", visitor.getDate());
    }

    /** */
    @Test
    public void testGetDeathDateNoDateString() {
        final Attribute death = new Attribute(person1, "Death");
        final GetDateVisitor visitor = new GetDateVisitor();
        death.accept(visitor);
        assertEquals("Expected empty date string", "", visitor.getDate());
    }

    /** */
    @Test
    public void testSetGetTail() {
        Attribute attribute;
        attribute = new Attribute(null, null, null);
        attribute.setTail("test 1");
        assertMatch(attribute, null, "", "test 1");
    }

    /** */
    @Test
    public void testResetGetTail() {
        Attribute attribute;
        attribute = new Attribute(null, null, null);
        attribute.setTail("test 1");
        attribute.setTail("test 2");
        assertMatch(attribute, null, "", "test 2");
    }

    /** */
    @Test
    public void testResetToNullGetTail() {
        Attribute attribute;
        attribute = new Attribute(null, null, null);
        attribute.setTail("test 1");
        attribute.setTail(null);
        assertMatch(attribute, null, "", "");
    }

    /** */
    @Test
    public void testResetToEmptyGetTail() {
        Attribute attribute;
        attribute = new Attribute(null, null, null);
        attribute.setTail("test 1");
        attribute.setTail("");
        assertMatch(attribute, null, "", "");
    }

    /**
     * Fails an assertion if one of the values doesn't match.
     *
     * @param attribute the attribute to test
     * @param expParent expected parent value
     * @param expString expected string value
     * @param expTail expected tail value
     */
    private void assertMatch(final Attribute attribute,
            final GedObject expParent, final String expString,
            final String expTail) {
        assertEquals("Parent mismatch", expParent, attribute.getParent());
        assertEquals("String mismatch", expString, attribute.getString());
        assertEquals("Tail mismatch", expTail, attribute.getTail());
    }
}
