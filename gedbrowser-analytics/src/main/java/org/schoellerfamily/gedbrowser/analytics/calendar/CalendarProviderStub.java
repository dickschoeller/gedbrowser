package org.schoellerfamily.gedbrowser.analytics.calendar;

import java.util.Calendar;
import java.util.Locale;

import org.joda.time.LocalDate;

import lombok.NoArgsConstructor;

/**
 * A version of CalendarProvider that always returns the same values for testing.
 *
 * @author Dick Schoeller
 */
@NoArgsConstructor
public final class CalendarProviderStub implements CalendarProvider {
    @Override
    public Calendar now() {
        final int birthYear = 2015;
        final int birthDay = 14;
        final Calendar referenceCalendar = Calendar.getInstance(Locale.US);
        referenceCalendar.set(Calendar.YEAR, birthYear);
        referenceCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        referenceCalendar.set(Calendar.DAY_OF_MONTH, birthDay);
        return referenceCalendar;
    }

    @Override
    public LocalDate nowDate() {
        return new LocalDate(now());
    }
}
