package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;

import org.joda.time.LocalDate;

import lombok.NoArgsConstructor;

/**
 * The default implementation of CalendarProvider.
 *
 * @author Dick Schoeller
 */
@NoArgsConstructor
public final class CalendarProviderImpl implements CalendarProvider {
    @Override
    public Calendar now() {
        return Calendar.getInstance();
    }

    @Override
    public LocalDate nowDate() {
        return new LocalDate();
    }
}
