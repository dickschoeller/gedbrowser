package org.schoellerfamily.gedbrowser.api.datamodel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @author Dick Schoeller
 */
public class DateUtil {
    /**
     * @return a date attribute for today
     */
    public ApiAttribute todayDateAttribute() {
        final java.util.Date date = new java.util.Date();
        final LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        final int day = localDate.getDayOfMonth();
        final String month = localDate.getMonth()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toUpperCase(Locale.ENGLISH);
        final int year = localDate.getYear();
        final String todayString = twoDigit(day) + " " + month + " " + year;
        return new ApiAttribute("date", todayString);
    }

    /**
     * @param day a day number
     * @return as a two digit string
     */
    private String twoDigit(final int day) {
        final int ten = 10;
        if (day >= ten) {
            return Integer.toString(day);
        }
        return "0" + day;
    }
}
