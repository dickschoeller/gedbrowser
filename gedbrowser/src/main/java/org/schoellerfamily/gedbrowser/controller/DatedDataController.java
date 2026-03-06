package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * Base class for controllers that need dated data.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class DatedDataController extends AbstractController {
    /**
     * All arguments constructor.
     *
     * @param appInfo the application info
     * @param users info about the known application users
     * @param loader enable loading gedcom files
     * @param provider enable calendar processing
     * @param repositoryManager enable data storage
     */
    protected DatedDataController(final ApplicationInfo appInfo,
            final Users<? extends User> users,
            final GedObjectFileLoader loader,
            final CalendarProvider provider,
            final RepositoryManagerMongo repositoryManager) {
        super(appInfo, users, loader, provider, repositoryManager);
    }
    /**
     * Return the calendar provider.
     *
     * @return the provider
     */
    protected final CalendarProvider calendarProvider() {
        return getProvider();
    }
}
