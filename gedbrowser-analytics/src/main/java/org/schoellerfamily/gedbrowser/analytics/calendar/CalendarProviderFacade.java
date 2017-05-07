package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

/**
 * Provides means for a class to be a facade for CalendarProvider. A class can
 * be a facade for CalendarProvider by doing the following:
 * <ul>
 * <li>implements CalendarProviderFacade</li>
 * <li>keeping an instance of a CalendarProvider</li>
 * <li>providing the method getCalendarProvider to get at it</li>
 * </ul>
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public interface CalendarProviderFacade extends CalendarProvider {
    /**
     * @return the calendar provider implementation
     */
    CalendarProvider getCalendarProvider();

    /**
     * {@inheritDoc}
     */
    @Override
    default Calendar now() {
        return getCalendarProvider().now();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default LocalDate nowDate() {
        return getCalendarProvider().nowDate();
    }
}
