package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class DatedDataController extends AbstractController {
    /** */
    @Autowired
    private transient CalendarProvider provider;

    /**
     * Return the calendar provider.
     *
     * @return the provider
     */
    protected final CalendarProvider calendarProvider() {
        return provider;
    }
}
