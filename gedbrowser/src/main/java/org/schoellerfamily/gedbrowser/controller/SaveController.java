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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Emits GEDCOM to the HTTP connection to download the GEDCOM state.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class SaveController extends AbstractController {

    /**
     * Constructor.
     *
     * @param appInfo the application info
     * @param users info about the known application users
     * @param loader enable loading gedcom files
     * @param provider enable calendar processing
     * @param repositoryManager enable data storage
     */
    public SaveController(final ApplicationInfo applicationInfo,
            final Users<? extends User> users,
            final GedObjectFileLoader loader,
            final CalendarProvider provider,
            final RepositoryManagerMongo repositoryManager) {
        super(applicationInfo, users, loader, provider, repositoryManager);
    }

    /**
     * Connects HTML template file with data for saving the GEDCOM file.
     *
     * @param dbName name of database for the lookup
     * @param response the servlet response object, needed for tweaking headers
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping(value = "/save")
    @ResponseBody
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

    /**
     * Fill in response headers to make this save the file instead of
     * displaying it.
     *
     * @param response the servlet response
     * @param root the root of the dataset being saved
     */
    private void setHeaders(final HttpServletResponse response,
            final Root root) {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition",
                "attachment; filename=" + getSaveFilename(root));
    }

    /**
     * @param root the root object we are saving
     * @return the href string with the base filename.
     */
    private String getSaveFilename(final Root root) {
        final String filename = root.getFilename();
        final int lastIndexOf = filename.lastIndexOf("/");
        if (lastIndexOf == -1) {
            return filename;
        }
        return filename.substring(lastIndexOf + 1);
    }
}
