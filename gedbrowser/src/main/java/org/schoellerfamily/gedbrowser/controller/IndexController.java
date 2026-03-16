package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.IndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * Listen for requests for the surnames index page.
 *
 * @author Dick Schoeller
 */
@Controller
@SuppressWarnings("PMD.CommentSize")
@Slf4j
public class IndexController extends DatedDataController {
    /** */
    private final String gedbrowserHome;

    /**
     * Creates a new IndexController.
     *
     * @param appInfo the app info
     * @param users the users
     * @param loader the loader
     * @param provider the provider
     * @param repositoryManager the repository manager
     */
    public IndexController(final ApplicationInfo appInfo,
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
     * Connects HTML template file with data for the surnames index page. The
     * page displays the surnames that begin with the provided letter.
     *
     * @param letter the letter that we're displaying
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @GetMapping("/surnames")
    public final String surnames(
            @RequestParam(value = "letter",
                required = false,
                defaultValue = "?") final String letter,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering surnames");

        final Root root = fetchRoot(dbName);

        final GedRenderer<?> gedRenderer = new IndexRenderer(root, letter,
                createRenderingContext());

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("model", gedRenderer);
        model.addAttribute("appInfo", getAppInfo());

        log.debug("Exiting surnames");
        return "surnames";
    }
}
