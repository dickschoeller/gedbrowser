package org.schoellerfamily.gedbrowser.analytics.order.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.analytics.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.FamilyBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@Slf4j
final class OrderAnalyzerTest implements AnalyzerTest {

    /** */
    @Autowired
    private OrderAnalyzerTestWrapper wrapper;
    /** */
    @Autowired
    private GedObjectBuilder builder;
    /** */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    @Override
    public PersonBuilder personBuilder() {
        return builder;
    }

    @Override
    public FamilyBuilder familyBuilder() {
        return builder;
    }

    @Test
    void testEmptyPersonIsOK() {
        final Person person = personBuilder().createPerson();
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected good order when there are no events");
    }

    @Test
    void testPersonWithOnlyUndatedEventIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected good order when there are only undated events");
    }

    @Test
    void testPersonWithOnlyOneEventIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "8 JAN 2017");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected good order when there is only 1 event");
    }

    @Test
    void testPersonWithOnlyTwoEventsSameDayIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected good order when events are same date");
    }

    @Test
    void testPersonWithOnlyTwoEventsOutOfOrderIsNotOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected bad order when events are out of order");
    }

    @Test
    void testPersonWithOnlyTwoEventsInOrderIsOK() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected good order when events are in order");
    }

    @Test
    void testPersonWithOnlyTwoEventsOutOfOrderHasMismatchEntry() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.getMismatches().isEmpty(),
            "Expected mismatch strings when events are out of order");
    }

    @Test
    void testPersonWithTwoEventsOutOfOrderHasExpectedTextInMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Occupation", "7 JAN 2017");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        final String expected = "Date order: Occupation dated  on 2017-01-07 "
            + "occurs after Education dated  on 2017-01-08";
        final String actual = result.getMismatches().get(0);
        assertEquals(expected, actual, "Expected mismatch strings when events are out of order");
    }

    @Test
    void testPersonWithBirthBeforeNonBirthMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Education");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct when birth events before others");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Baptism",
        "Christening",
        "Naming",
        "Birth"
    })
    void testBirthEventAfterNonBirthMismatch(final String birthEventType) {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, birthEventType);
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with birth events are after others");
    }

    @Test
    void testPersonWithBirthAfterNonBirthMismatchString() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Education");
        personBuilder().createPersonEvent(person, "Birth");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        final String expected = "Logical order: Birth (undated) after non "
            + "birth event, Education";
        final String actual = result.getMismatches().get(0);
        assertEquals(expected, actual, "Expected mismatch string with birth events after others");
    }

    @Test
    void testPersonWithBirthAfterBaptismMismatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Baptism");
        personBuilder().createPersonEvent(person, "Birth");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertFalse(result.isCorrect(), "Expected incorrect with birth events are after others");
    }

    @Test
    void testPersonWithBirthBaptismNamingMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Baptism");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with birth, baptism, naming");
    }

    @Test
    void testPersonWithBirthBirthNamingMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with birth, baptism, naming");
    }

    @Test
    void testPersonWithNullNonnullNullDateMatch() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth");
        personBuilder().createPersonEvent(person, "Birth", "8 JAN 2017");
        personBuilder().createPersonEvent(person, "Naming");
        final OrderAnalyzerResult result = wrapper.analyze(person);
        assertTrue(result.isCorrect(), "Expected correct with birth, birth date, naming");
    }

    @Test
    void testMinDateFirstNull() {
        final int year = 2017;
        final LocalDate date2 = new LocalDate(year, 1, 2);
        final LocalDate minDate = OrderAnalyzer.minDate(null, date2);
        assertEquals(date2, minDate, "Date should match");
    }

    @Test
    void testMinDateSecondNull() {
        final int year = 2017;
        final LocalDate date1 = new LocalDate(year, 1, 1);
        final LocalDate minDate = OrderAnalyzer.minDate(date1, null);
        assertEquals(date1, minDate, "Date should match");
    }

    @Test
    void testMinDateBothNull() {
        final LocalDate minDate = OrderAnalyzer.minDate(null, null);
        assertNull(minDate, "Date should be null");
    }

    @Test
    void testMinDateFirstIsMin() {
        final int year = 2017;
        final LocalDate date1 = new LocalDate(year, 1, 1);
        final LocalDate date2 = new LocalDate(year, 1, 2);
        final LocalDate minDate = OrderAnalyzer.minDate(date1, date2);
        assertEquals(date1, minDate, "Date should match");
    }

    @Test
    void testMinDateSecondIsMin() {
        final int year = 2017;
        final LocalDate date1 = new LocalDate(year, 1, 2);
        final LocalDate date2 = new LocalDate(year, 1, 1);
        final LocalDate minDate = OrderAnalyzer.minDate(date1, date2);
        assertEquals(date2, minDate, "Date should match");
    }

    // TODO will need checks of marriages before birth or after death
    // TODO will need checks of kids born before birth or after death of mother
    // TODO will need checks of kids born too long after death of father

    /**
     * This test hits a big data set. The main thing that it detects is poorly
     * parsed dates. Since the data set in question has weird date formats. Some of
     * that is inevitable.
     *
     * @throws IOException if failed to read file
     */
    @Test
    void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = TestResourceReader.readFileTestSource("gl120368.ged");
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
        final OrderAnalyzer orderAnalyzer = createAnalyzer(person);
        final OrderAnalyzerResult result = orderAnalyzer.analyze();
        if (!result.isCorrect()) {
            log.info(indexName);
            for (final String message : result.getMismatches()) {
                log.info("   {}", message);
                assertFalse(message.contains(today.toString()),
                    "If has today (" + today.toString() + ") something is wrong");
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
