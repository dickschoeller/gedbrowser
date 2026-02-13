package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
final class AttributeTest {
    /** */
    private static final String DUMMY = "Dummy";
    /** */
    private static final String HUNDRED_DAY = "31 July 2090";
    /** */
    private static final String POTTER_DAY = "31 July 1990";
    /** */
    private transient Person person1;

    /** */
    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person1 = builder.createPerson("I1", "J. Random/Schoeller/");
    }

    /** */
    @Test
    void testString() {
        final Attribute job = new Attribute(person1, "Job", "Worked at SAP");
        assertEquals("Worked at SAP", job.getTail(), "In should match out");
    }

    /** */
    @Test
    void testAppendString() {
        final Attribute job = new Attribute(person1, "Job", "Worked at SAP");
        job.appendString(" for 5 years");
        assertEquals("Worked at SAP for 5 years", job.getTail(),
            "Should have concatenated the segments");
    }

    /** */
    @Test
    void testNoBirthNoDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        dummy.accept(visitor);
        assertEquals("", visitor.getDate(), "Expected empty string");
    }

    /** */
    @Test
    void testGetBirthDateNotInserted() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute birth = new Attribute(person1, "Birth");
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        birth.accept(visitor);
        assertEquals("", visitor.getDate(), "Expected empty string");
    }

    /** */
    @Test
    void testGetBirthDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute birth = new Attribute(person1, "Birth");
        final Date date = new Date(birth, POTTER_DAY);
        birth.insert(date);
        final GetDateVisitor visitor = new GetDateVisitor("Birth");
        birth.accept(visitor);
        assertEquals(POTTER_DAY, visitor.getDate(), "Date's filled in. Should match");
    }

    /** */
    @Test
    void testNoDeathNoDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final GetDateVisitor visitor = new GetDateVisitor("Death");
        dummy.accept(visitor);
        assertEquals("", visitor.getDate(), "Expected empty string");
    }

    /** */
    @Test
    void testGetDeathDateNotInserted() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute death = new Attribute(person1, "Death");
        final GetDateVisitor visitor = new GetDateVisitor("Death");
        death.accept(visitor);
        assertEquals("", visitor.getDate(), "Expected empty string");
    }

    /** */
    @Test
    void testGetDeathDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        dummy.insert(dummyDate);
        final Attribute death = new Attribute(person1, "Death");
        final Date date = new Date(death, HUNDRED_DAY);
        death.insert(date);
        final GetDateVisitor visitor = new GetDateVisitor("Death");
        death.accept(visitor);
        assertEquals(HUNDRED_DAY, visitor.getDate(), "Date's filled in. Should match");
    }

    /** */
    @Test
    void testGetDate() {
        final Attribute dummy = new Attribute(person1, DUMMY);
        final Date dummyDate = new Date(dummy, POTTER_DAY);
        // TODO this should become unnecessary if I can further restrict the
        // children of an attribute.
        dummy.insert(new Person());
        dummy.insert(dummyDate);
        final GetDateVisitor visitor = new GetDateVisitor();
        dummy.accept(visitor);
        assertEquals(POTTER_DAY, visitor.getDate(), "Date mismatch");
    }

    /** */
    @Test
    void testGetDateNullDateString() {
        final Attribute dummy1 = new Attribute(person1, DUMMY);
        final Date dummyDate1 = new Date(dummy1, null);
        dummy1.insert(dummyDate1);
        final GetDateVisitor visitor = new GetDateVisitor();
        dummy1.accept(visitor);
        assertEquals("", visitor.getDate(), "Expected empty date string");
    }

    /** */
    @Test
    void testGetDeathDateNoDateString() {
        final Attribute death = new Attribute(person1, "Death");
        final GetDateVisitor visitor = new GetDateVisitor();
        death.accept(visitor);
        assertEquals("", visitor.getDate(), "Expected empty date string");
    }

    /**
     * Parameterized test replacing several similar tail tests.
     * Arguments are: initialTail, doSecondSet, secondTail, expectedTail
     *
     * @param initialTail initial tail value to set
     * @param doSecondSet whether to set the tail a second time
     * @param secondTail second tail value to set (may be null)
     * @param expectedTail expected final tail value
     */
    @ParameterizedTest
    @MethodSource("tailCases")
    void testTailVariants(final String initialTail, final boolean doSecondSet,
            final String secondTail, final String expectedTail) {
        final Attribute attribute = new Attribute(null, null, null);
        attribute.setTail(initialTail);
        if (doSecondSet) {
            attribute.setTail(secondTail);
        }
        assertMatch(attribute, null, "", expectedTail);
    }

    static Stream<Arguments> tailCases() {
        return Stream.of(
                // Only set once
                Arguments.of("test 1", false, null, "test 1"),
                // Reset to a new string
                Arguments.of("test 1", true, "test 2", "test 2"),
                // Reset explicitly to null => expect empty string
                Arguments.of("test 1", true, null, ""),
                // Reset to empty string => expect empty string
                Arguments.of("test 1", true, "", "")
        );
    }

    /**
     * Fails an assertion if one of the values doesn't match.
     *
     * @param attribute the attribute to test
     * @param expParent expected parent value
     * @param expString expected string value
     * @param expTail   expected tail value
     */
    private void assertMatch(final Attribute attribute, final GedObject expParent,
        final String expString, final String expTail) {
        assertEquals(expParent, attribute.getParent(), "Parent mismatch");
        assertEquals(expString, attribute.getString(), "String mismatch");
        assertEquals(expTail, attribute.getTail(), "Tail mismatch");
    }
}
