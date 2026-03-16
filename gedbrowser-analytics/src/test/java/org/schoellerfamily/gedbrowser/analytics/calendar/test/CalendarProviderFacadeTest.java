package org.schoellerfamily.gedbrowser.analytics.calendar.test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderFacade;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderImpl;

/**
 * @author Dick Schoeller
 */
class CalendarProviderFacadeTest {
    private final Calendar calendar = Calendar.getInstance();
    private final LocalDate localDate = new LocalDate();
    /** */
    private CalendarProvider mockProvider;
    /** */
    private CalendarProvider implProvider;

    /**
     * This mock of CalendarProvider allows us to see that the facade is a real
     * pass-through. Because we will assertSame on the results against the fields of
     * the test class.
     *
     * @author Dick Schoeller
     */
    private final class CalendarProviderMock implements CalendarProvider {
        /**
         * Returns the calendar.
         *
         * @return the resulting calendar
         */
        @Override
        public Calendar now() {
            return calendar;
        }

        /**
         * Returns the local date.
         *
         * @return the resulting local date
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
    private final class CalendarProviderFacadeImpl implements CalendarProviderFacade {
        /** */
        private final CalendarProvider provider;

        CalendarProviderFacadeImpl(final CalendarProvider provider) {
            this.provider = provider;
        }

        /**
         * Returns the calendar provider.
         *
         * @return the calendar provider
         */
        @Override
        public CalendarProvider getCalendarProvider() {
            return provider;
        }
    }

    @BeforeEach
    void setUp() {
        mockProvider = new CalendarProviderFacadeImpl(new CalendarProviderMock());
        implProvider = new CalendarProviderFacadeImpl(new CalendarProviderImpl());
    }

    @Test
    void testFacadeNow() {
        assertSame(calendar, mockProvider.now(), "Should have passed through to the provider mock");
    }

    @Test
    void testFacadeNowDate() {
        assertSame(localDate, mockProvider.nowDate(),
            "Should have passed through to the provider mock");
    }

    @Test
    void testImplNow() throws InterruptedException {
        final Calendar actual = implProvider.now();
        final long difference = actual.getTimeInMillis() - calendar.getTimeInMillis();
        final long expected = 10;
        assertLessThan(
            "Difference should be less than " + expected + " milliseconds, is: " + difference,
            expected, difference);
    }

    @Test
    void testImplNowDate() {
        final LocalDate actual = implProvider.nowDate();
        assertTrue(actual.equals(localDate) || actual.equals(localDate.plusDays(1)),
            "actual should be same as localDate, or is 1 day higher if test at midnight");
    }

    private void assertLessThan(final String message, final long expected, final long actual) {
        assertTrue(actual < expected, message);
    }
}
