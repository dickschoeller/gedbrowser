package org.schoellerfamily.gedbrowser.analytics.calendar.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderFacade;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderImpl;

/**
 * @author Dick Schoeller
 */
public class CalendarProviderFacadeTest {
    /** */
    private final Calendar calendar = Calendar.getInstance();
    /** */
    private final LocalDate localDate = new LocalDate();
    /** */
    private CalendarProvider mockProvider;
    /** */
    private CalendarProvider implProvider;

    /**
     * This mock of CalendarProvider allows us to see that the facade is a
     * real pass-through. Because we will assertSame on the results against
     * the fields of the test class.
     *
     * @author Dick Schoeller
     */
    private final class CalendarProviderMock implements CalendarProvider {
        /**
         * {@inheritDoc}
         */
        @Override
        public Calendar now() {
            return calendar;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LocalDate nowDate() {
            return localDate;
        }
    }

    /**
     * Simplest possible implementation of the facade class with an injected
     * provider.
     *
     * @author Dick Schoeller
     */
    private final class CalendarProviderFacadeImpl
            implements CalendarProviderFacade {
        /** */
        private final CalendarProvider provider;

        /**
         * @param provider the provider to facade.
         */
        CalendarProviderFacadeImpl(final CalendarProvider provider) {
            this.provider = provider;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public CalendarProvider getCalendarProvider() {
            return provider;
        }
    }

    /**
     * Prepare for the test.
     */
    @Before
    public void setUp() {
        mockProvider =
                new CalendarProviderFacadeImpl(new CalendarProviderMock());
        implProvider =
                new CalendarProviderFacadeImpl(new CalendarProviderImpl());
    }

    /**
     * Simply test the pass through of the facade.
     */
    @Test
    public void testFacadeNow() {
        assertSame("Should have passed through to the provider mock",
                calendar, mockProvider.now());
    }

    /**
     * Simply test the pass through of the facade.
     */
    @Test
    public void testFacadeNowDate() {
        assertSame("Should have passed through to the provider mock",
                localDate, mockProvider.nowDate());
    }

    /**
     * Simply test the pass through of the facade.
     * @throws InterruptedException if thread interrupted
     */
    @Test
    public void testImplNow() throws InterruptedException {
        final Calendar actual = implProvider.now();
        final long difference =
                actual.getTimeInMillis() - calendar.getTimeInMillis();
        final long expected = 10;
        assertLessThan(
                "Difference should be less than " + expected
                        + " milliseconds, is: " + difference,
                expected, difference);
    }

    /**
     * Simply test the pass through of the facade.
     */
    @Test
    public void testImplNowDate() {
        final LocalDate actual = implProvider.nowDate();
        assertTrue(
                "actual and localDate should be the same,"
                + " or actual is 1 day higher if test right at midnight",
                actual.equals(localDate)
                        || actual.equals(localDate.plusDays(1)));
    }

    /**
     * @param message message to display on failure
     * @param expected expected limit
     * @param actual actual value
     */
    private void assertLessThan(final String message, final long expected,
            final long actual) {
        assertTrue(message, actual < expected);
    }
}
