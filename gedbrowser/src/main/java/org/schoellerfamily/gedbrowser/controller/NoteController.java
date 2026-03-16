package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.controller.exception.NoteNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Note;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for note.
 *
 * @author Richard Schoeller
 */
@Controller
@Slf4j
public class NoteController extends DatedDataController {
    /** */
    private final String gedbrowserHome;

    /**
     * Creates a new NoteController.
     *
     * @param appInfo the app info
     * @param users the users
     * @param loader the loader
     * @param provider the provider
     * @param repositoryManager the repository manager
     * @param gedbrowserHome the gedbrowser home
     */
    public NoteController(final ApplicationInfo appInfo, final Users<? extends User> users,
        final GedObjectFileLoader loader, final CalendarProvider provider,
        final RepositoryManagerMongo repositoryManager,
        @Value("${gedbrowser.home:/var/lib/gedbrowser}") final String gedbrowserHome) {
        super(appInfo, users, loader, provider, repositoryManager);
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
    @GetMapping("/note")
    public final String note(
        @RequestParam(value = "id", required = false, defaultValue = "N0")
        final String idString,
        @RequestParam(value = "db", required = false, defaultValue = "schoeller")
        final String dbName,
        final Model model) {
        log.debug("Entering source");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();
        final Note note = (Note) root.find(idString);
        if (note == null) {
            throw new NoteNotFoundException("Note %s not found".formatted(idString), idString,
                dbName, context);
        }

        final GedRenderer<?> noteRenderer = new GedRendererFactory().create(note, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("noteString", note.getString());
        model.addAttribute("model", noteRenderer);
        model.addAttribute("appInfo", getAppInfo());
        log.debug("Exiting source");
        return "note";
    }
}
