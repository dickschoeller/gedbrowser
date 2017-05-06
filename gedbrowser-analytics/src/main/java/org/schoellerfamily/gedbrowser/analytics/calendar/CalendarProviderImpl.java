package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

/**
 * @author Dick Schoeller
 */
public final class CalendarProviderImpl implements CalendarProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar now() {
        return Calendar.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate nowDate() {
        return new LocalDate();
    }
}
