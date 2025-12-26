package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.IndexByPlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * Listen for requests for the place index page.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class PlaceIndexController extends DatedDataController {
    private final GeoServiceClient client;

    private final String gedbrowserHome;

    /**
     * Constructor.
     *
     * @param appInfo the application info
     * @param users info about the known application users
     * @param loader enable loading gedcom files
     * @param provider enable calendar processing
     * @param repositoryManager enable data storage
     * @param client enable interaction with geoservice
     * @param gedbrowserHome location of data files for initialization
     */
    public PlaceIndexController(final ApplicationInfo appInfo,
            final Users<? extends User> users,
            final GedObjectFileLoader loader,
            final CalendarProvider provider,
            final RepositoryManagerMongo repositoryManager,
            final GeoServiceClient client,
            @Value("${gedbrowser.home}")
            final String gedbrowserHome) {
        super(appInfo, users, loader, provider, repositoryManager);
        this.client = client;
        this.gedbrowserHome = gedbrowserHome;
    }

    /**
     * Connects HTML template file with data for the surnames index page. The
     * page displays the surnames that begin with the provided letter.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/places")
    public final String places(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering surnames");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();

        final IndexByPlaceRenderer gedRenderer = new IndexByPlaceRenderer(root,
                client, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("model", gedRenderer);
        model.addAttribute("appInfo", appInfo);

        return "places";
    }
}
