package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

/**
 * Provides calendar values to calling code.
 *
 * @author Richard Schoeller
 */
public interface CalendarProvider {
    /**
     * Get a Calendar representing the current date and time.
     *
     * @return the provider's understanding of now as a Calendar
     */
    Calendar now();

    /**
     * Get now as a LocalDate.
     *
     * @return the provider's understanding of now as a LocalDate
     */
    LocalDate nowDate();
}
