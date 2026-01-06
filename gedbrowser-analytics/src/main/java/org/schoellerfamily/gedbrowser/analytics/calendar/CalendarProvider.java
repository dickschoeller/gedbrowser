package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

/**
 * Interface for providing the current date as a Calendar or LocalDate.
 *
 * @author Dick Schoeller
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
