package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class OrderAnalyzerTest {
    /** */
    private final OrderAnalyzerTestHelper helper =
            new OrderAnalyzerTestHelper();

    /** */
    @Test
    public void testEmptyPersonIsOK() {
        final Person person = new Person();
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when there are no events",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyUndatedEventIsOK() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Occupation", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when there are only undated events",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyOneEventIsOK() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Occupation", "8 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when there is only 1 event",
                result.isCorrect());
    }
    /** */
    @Test
    public void testPersonWithOnlyTwoEventsSameDayIsOK() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Occupation", "8 JAN 2017");
        builder.createPersonEvent(person, "Education", "8 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when events are same date",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyTwoEventsOutOfOrderIsNotOK() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", "8 JAN 2017");
        builder.createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected bad order when events are out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyTwoEventsInOrderIsOK() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Occupation", "7 JAN 2017");
        builder.createPersonEvent(person, "Education", "8 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when events are in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyTwoEventsOutOfOrderHasMismatchEntry() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", "8 JAN 2017");
        builder.createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected mismatch strings when events are out of order",
                result.getMismatches().isEmpty());
    }

    /** */
    @Test
    public void testPersonWithTwoEventsOutOfOrderHasExpectedTextInMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", "8 JAN 2017");
        builder.createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        final String actual = result.getMismatches().get(0);
        assertEquals("Expected mismatch strings when events are out of order",
                "Occupation on 2017-01-07 before event,"
                        + " Education on 2017-01-08",
                actual);
    }

    /** */
    @Test
    public void testPersonWithBirthBeforeNonBirthMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Birth", null);
        builder.createPersonEvent(person, "Education", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct when birth events before others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBaptismAfterNonBirthMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Baptism", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithChristeningAfterNonBirthMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Christening", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithNamingAfterNonBirthMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Naming", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthAfterNonBirthMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Birth", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthAfterNonBirthMismatchString() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Education", null);
        builder.createPersonEvent(person, "Birth", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        final String actual = result.getMismatches().get(0);
        assertEquals("Expected mismatch string with birth events after others",
                "Birth (undated) after non birth event, Education", actual);
    }

    /** */
    @Test
    public void testPersonWithBirthAfterBaptismMismatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Baptism", null);
        builder.createPersonEvent(person, "Birth", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthBaptismNamingMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Birth", null);
        builder.createPersonEvent(person, "Baptism", null);
        builder.createPersonEvent(person, "Naming", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with birth, baptism, naming",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthBirthNamingMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Birth", null);
        builder.createPersonEvent(person, "Birth", null);
        builder.createPersonEvent(person, "Naming", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with birth, baptism, naming",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithNullNonnullNullDateMatch() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Birth", null);
        builder.createPersonEvent(person, "Birth", "8 JAN 2017");
        builder.createPersonEvent(person, "Naming", null);
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with birth, birth date, naming",
                result.isCorrect());
    }

    //    /** */
//    @Test
//    public void testPersonWithNonDeathAfterDeathMismatchString() {
//        final GedObjectBuilder builder = new GedObjectBuilder();
//        final Person person = builder.createPerson1();
//        builder.createPersonEvent(person, "Education", null);
//        builder.createPersonEvent(person, "Birth", null);
//        final OrderAnalyzer orderAnalyzer = new OrderAnalyzer(person);
//        final OrderAnalyzerResult result = orderAnalyzer.analyze();
//        final String actual = result.getMismatches().get(0);
//        assertTrue("Expected mismatch string with birth events after others",
//                actual.contains("after non birth events"));
//    }
    /** */
    @Test
    public void testMinDateFirstNull() {
        final int year = 2017;
        final LocalDate date2 = new LocalDate(year, 1, 2);
        final LocalDate minDate = OrderAnalyzer.minDate(null, date2);
        assertEquals("Date should match", date2, minDate);
    }

    /** */
    @Test
    public void testMinDateSecondNull() {
        final int year = 2017;
        final LocalDate date1 = new LocalDate(year, 1, 1);
        final LocalDate minDate = OrderAnalyzer.minDate(date1, null);
        assertEquals("Date should match", date1, minDate);
    }

    /** */
    @Test
    public void testMinDateBothNull() {
        final LocalDate minDate = OrderAnalyzer.minDate(null, null);
        assertNull("Date should be null", minDate);
    }

    /** */
    @Test
    public void testMinDateFirstIsMin() {
        final int year = 2017;
        final LocalDate date1 = new LocalDate(year, 1, 1);
        final LocalDate date2 = new LocalDate(year, 1, 2);
        final LocalDate minDate = OrderAnalyzer.minDate(date1, date2);
        assertEquals("Date should match", date1, minDate);
    }

    /** */
    @Test
    public void testMinDateSecondIsMin() {
        final int year = 2017;
        final LocalDate date1 = new LocalDate(year, 1, 2);
        final LocalDate date2 = new LocalDate(year, 1, 1);
        final LocalDate minDate = OrderAnalyzer.minDate(date1, date2);
        assertEquals("Date should match", date2, minDate);
    }

    // TODO will need checks of marriages before birth or after death
    // TODO will need checks of kids born before birth or after death of mother
    // TODO will need checks of kids born too long after death of father

}
