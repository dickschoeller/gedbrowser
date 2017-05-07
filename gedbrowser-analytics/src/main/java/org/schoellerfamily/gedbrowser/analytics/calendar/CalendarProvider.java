package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

/**
 * @author Dick Schoeller
 */
public interface CalendarProvider {
    /**
     * @return the provider's understanding of now as a Calendar
     */
    Calendar now();

    /**
     * @return the provider's understanding of now as a LocalDate
     */
    LocalDate nowDate();
}
