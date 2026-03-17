package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittersRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for submitters.
 *
 * @author Richard Schoeller
 */
@Controller
@Slf4j
public class SubmittersController extends DatedDataController {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private final String gedbrowserHome;

    /**
     * Creates a new SubmittersController.
     *
     * @param appInfo the app info
     * @param users the users
     * @param loader the loader
     * @param provider the provider
     * @param repositoryManager the repository manager
     * @param gedbrowserHome the gedbrowser home directory
     */
    public SubmittersController(final ApplicationInfo appInfo,
            final Users<? extends User> users,
            final GedObjectFileLoader loader,
            final CalendarProvider provider,
            final RepositoryManagerMongo repositoryManager,
            @Value("${gedbrowser.home:/var/lib/gedbrowser}")
            final String gedbrowserHome) {
        super(appInfo, users, loader, provider, repositoryManager);
        this.gedbrowserHome = gedbrowserHome;
    }

    /**
     * Connects HTML template file with data for the submitters index page.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @GetMapping("/submitters")
    public final String submitters(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering submitters");

        final Root root = fetchRoot(dbName);

        final GedRenderer<?> gedRenderer = new SubmittersRenderer(root,
                createRenderingContext());

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("model", gedRenderer);
        model.addAttribute("appInfo", getAppInfo());

        log.debug("Exiting submitters");
        return "submitters";
    }
}
