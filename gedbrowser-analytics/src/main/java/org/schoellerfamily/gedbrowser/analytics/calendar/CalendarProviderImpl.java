package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

import lombok.NoArgsConstructor;



/**
 * Provides calendar values to calling code.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class CalendarProviderImpl implements CalendarProvider {
    /**
     * Returns the calendar.
     *
     * @return the resulting calendar
     */
    @Override
    public Calendar now() {
        return Calendar.getInstance();
    }

    /**
     * Creates and returns a new local date.
     *
     * @return the resulting local date
     */
    @Override
    public LocalDate nowDate() {
        return new LocalDate();
    }
}
