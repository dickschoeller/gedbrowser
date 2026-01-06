package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * Listen for requests for the head page.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class HeadController extends DatedDataController {
    /** */
    private final String gedbrowserHome;

    /**
     * Constructor.
     *
     * @param appInfo the application info
     * @param users info about the known application users
     * @param loader enable loading gedcom files
     * @param provider enable calendar processing
     * @param repositoryManager enable data storage
     * @param gedbrowserHome location of data files for initialization
     */
    public HeadController(final ApplicationInfo appInfo,
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
     * Connects HTML template file with data for the person page.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/head")
    public final String person(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering head");

        final RenderingContext context = createRenderingContext();

        final Head head = fetchHead(dbName);

        final String filename = gedbrowserHome + "/" + dbName + ".ged";
        model.addAttribute("filename", filename);
        model.addAttribute("model", headRenderer(context, head));
        model.addAttribute("appInfo", getAppInfo());

        return "head";
    }

    /**
     * @param dbName the name of the database
     * @return the head
     */
    private Head fetchHead(final String dbName) {
        final Root root = fetchRoot(dbName);
        return (Head) root.find("Header");
    }

    /**
     * @param context the rendering context
     * @param head the head object being rendered
     * @return the person renderer
     */
    private GedRenderer<?> headRenderer(final RenderingContext context,
            final Head head) {
        return new GedRendererFactory().create(head, context);
    }
}
