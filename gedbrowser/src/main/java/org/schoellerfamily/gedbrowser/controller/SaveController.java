package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.writer.GedWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Emits GEDCOM to the HTTP connection to download the GEDCOM state.
 *
 * @author Dick Schoeller
 */
@RestController
@Slf4j
public class SaveController extends AbstractController {

    /**
     * Creates a new SaveController.
     *
     * @param appInfo the app info
     * @param users the users
     * @param loader the loader
     * @param provider the provider
     * @param repositoryManager the repository manager
     */
    public SaveController(final ApplicationInfo appInfo,
            final Users<? extends User> users,
            final GedObjectFileLoader loader,
            final CalendarProvider provider,
            final RepositoryManagerMongo repositoryManager) {
        super(appInfo, users, loader, provider, repositoryManager);
    }

    /**
     * Connects HTML template file with data for saving the GEDCOM file.
     *
     * @param dbName name of database for the lookup
     * @param response the servlet response object, needed for tweaking headers
     * @return a string identifying which HTML template to use.
     */
    @GetMapping(value = "/save")
    public final String save(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final HttpServletResponse response) {
        log.debug("Entering save");

        final RenderingContext context = createRenderingContext();

        final Root root = fetchRoot(dbName);

        setHeaders(response, root);

        if (!context.isAdmin()) {
            return "Sorry, you aren't authorized to do that!";
        }

        final String contents = new GedWriter(root).writeString();
        log.debug("Exiting save");
        return contents;
    }

    private void setHeaders(final HttpServletResponse response,
            final Root root) {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition",
                "attachment; filename=" + getSaveFilename(root));
    }

    private String getSaveFilename(final Root root) {
        final String filename = root.getFilename();
        final int lastIndexOf = filename.lastIndexOf("/");
        if (lastIndexOf == -1) {
            return filename;
        }
        return filename.substring(lastIndexOf + 1);
    }
}
