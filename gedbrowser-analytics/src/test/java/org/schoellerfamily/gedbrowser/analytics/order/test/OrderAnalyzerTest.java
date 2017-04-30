package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObject;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
public final class OrderAnalyzerTest implements AnalyzerTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final OrderAnalyzerTestHelper helper =
            new OrderAnalyzerTestHelper();
    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /**
     * Converts AbstractGedLine hierarchy to GedObject hierarchy.
     */
    private final transient GedLineToGedObject g2g = new GedLineToGedObject();

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder personBuilder() {
        return builder.getPersonBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder familyBuilder() {
        return builder.getFamilyBuilder();
    }

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
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when there are only undated events",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyOneEventIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "8 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when there is only 1 event",
                result.isCorrect());
    }
    /** */
    @Test
    public void testPersonWithOnlyTwoEventsSameDayIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when events are same date",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyTwoEventsOutOfOrderIsNotOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected bad order when events are out of order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyTwoEventsInOrderIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected good order when events are in order",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithOnlyTwoEventsOutOfOrderHasMismatchEntry() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected mismatch strings when events are out of order",
                result.getMismatches().isEmpty());
    }

    /** */
    @Test
    public void testPersonWithTwoEventsOutOfOrderHasExpectedTextInMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = helper.analyze(person);
        final String expected = "Date order: Occupation dated  on 2017-01-07 "
                + "occurs after Education dated  on 2017-01-08";
        final String actual = result.getMismatches().get(0);
        assertEquals("Expected mismatch strings when events are out of order",
                expected, actual);
    }

    /** */
    @Test
    public void testPersonWithBirthBeforeNonBirthMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Education");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct when birth events before others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBaptismAfterNonBirthMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, "Baptism");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithChristeningAfterNonBirthMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, "Christening");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithNamingAfterNonBirthMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthAfterNonBirthMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, "Birth");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthAfterNonBirthMismatchString() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, "Birth");
        final OrderAnalyzerResult result = helper.analyze(person);
        final String expected = "Logical order: Birth (undated) after non "
                + "birth event, Education";
        final String actual = result.getMismatches().get(0);
        assertEquals("Expected mismatch string with birth events after others",
                expected, actual);
    }

    /** */
    @Test
    public void testPersonWithBirthAfterBaptismMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Baptism");
        personBuilder().createPersonEvent(person, "Birth");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertFalse("Expected incorrect with birth events are after others",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthBaptismNamingMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Baptism");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with birth, baptism, naming",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithBirthBirthNamingMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with birth, baptism, naming",
                result.isCorrect());
    }

    /** */
    @Test
    public void testPersonWithNullNonnullNullDateMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Birth", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = helper.analyze(person);
        assertTrue("Expected correct with birth, birth date, naming",
                result.isCorrect());
    }

    //    /** */
//    @Test
//    public void testPersonWithNonDeathAfterDeathMismatchString() {
//        final Person person = builder.createPerson1();
//        builder.createPersonEvent(person, "Education");
//        builder.createPersonEvent(person, "Birth");
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

    /**
     * This test hits a big data set. The main thing that it detects is poorly
     * parsed dates. Since the data set in question has weird date formats.
     * Some of that is inevitable.
     *
     * @throws IOException if failed to read file
     */
    @Test
    public void testFactoryGedFile() throws IOException {
        final AbstractGedLine top =
                ReaderHelper.readFileTestSource(this, "gl120368.ged");
        final Root root = g2g.create(top);
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    assertAnalysisSane(person);
                }
            }
        }
    }

    /**
     * Run analysis on one person and assess the results.
     *
     * @param person the person
     */
    private void assertAnalysisSane(final Person person) {
        final LocalDate today = new LocalDate();
        final String indexName = person.getIndexName();
        final OrderAnalyzer orderAnalyzer = createAnalyzer(
                person);
        final OrderAnalyzerResult result = orderAnalyzer.analyze();
        if (!result.isCorrect()) {
            logger.info(indexName);
            for (final String message : result.getMismatches()) {
                logger.info("   " + message);
                assertFalse(
                        "If has today (" + today.toString()
                        + ") something is wrong",
                        message.contains(today.toString()));
            }
        }
    }

    /**
     * @param person the person
     * @return the analyzer
     */
    private OrderAnalyzer createAnalyzer(final Person person) {
        return new OrderAnalyzer(person);
    }
}
