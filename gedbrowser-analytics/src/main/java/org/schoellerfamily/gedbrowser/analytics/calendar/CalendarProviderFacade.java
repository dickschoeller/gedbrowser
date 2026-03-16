package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;



/**
 * Provides a simplified interface for calendar provider operations.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public interface CalendarProviderFacade extends CalendarProvider {
    /**
     * Get the wrapped provider.
     *
     * @return the calendar provider implementation
     */
    CalendarProvider getCalendarProvider();

    @Override
    default Calendar now() {
        return getCalendarProvider().now();
    }

    @Override
    default LocalDate nowDate() {
        return getCalendarProvider().nowDate();
    }
}
