package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.controller.exception.SubmitterNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * Listens for requests for submitter pages.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class SubmitterController extends GeoDataController {
    /** Location of gedbrowser configuration files. */
    private final String gedbrowserHome;

    /**
     * Constructor.
     *
     * @param appInfo           the application info
     * @param users             info about the known application users
     * @param loader            enable loading gedcom files
     * @param provider          enable calendar processing
     * @param repositoryManager enable data storage
     * @param client            enable interaction with geoservice
     * @param keyManager        enable interacting with google
     * @param gedbrowserHome    location of data files for initialization
     */
    @SuppressWarnings("checkstyle:parameternumber")
    public SubmitterController(final ApplicationInfo appInfo, final Users<? extends User> users,
        final GedObjectFileLoader loader, final CalendarProvider provider,
        final RepositoryManagerMongo repositoryManager, final GeoServiceClient client,
        final KeyManager keyManager,
        @Value("${gedbrowser.home:/var/lib/gedbrowser}") final String gedbrowserHome) {
        super(appInfo, users, loader, provider, repositoryManager, client, keyManager);
        this.gedbrowserHome = gedbrowserHome;
    }

    /**
     * Connects HTML template file with data for the source page.
     *
     * @param idString id URL argument.
     * @param dbName   name of database for the lookup.
     * @param model    Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/submitter")
    public final String source(
        @RequestParam(value = "id", required = false, defaultValue = "SUBM1")
        final String idString,
        @RequestParam(value = "db", required = false, defaultValue = "schoeller")
        final String dbName,
        final Model model) {
        log.debug("Entering submitter");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();
        final Submitter submitter = (Submitter) root.find(idString);
        if (submitter == null) {
            throw new SubmitterNotFoundException("Submitter %s not found".formatted(idString),
                idString, dbName, context);
        }

        final GedRenderer<?> submitterRenderer = new GedRendererFactory().create(submitter,
            context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("sourceString", submitter.getString());
        model.addAttribute("model", submitterRenderer);
        model.addAttribute("appInfo", getAppInfo());
        log.debug("Exiting submitter");
        return "submitter";
    }
}
