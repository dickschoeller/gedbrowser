package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.controller.exception.SubmissionNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
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
 * Listen for requests for the submission page.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class SubmissionController extends DatedDataController {
    /** */
    private final String gedbrowserHome;

    /**
     * Creates a new SubmissionController.
     *
     * @param appInfo the app info
     * @param users the users
     * @param loader the loader
     * @param provider the provider
     * @param repositoryManager the repository manager
     */
    public SubmissionController(final ApplicationInfo appInfo,
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
     * Connects HTML template file with data for the submission page.
     *
     * @param idString id URL argument.
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @GetMapping("/submission")
    public final String submission(
            @RequestParam(value = "id",
                required = false,
                defaultValue = "SUBN1") final String idString,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering source");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();
        final Submission submission = (Submission) root.find(idString);
        if (submission == null) {
            throw new SubmissionNotFoundException("Submission %s not found".formatted(idString),
                idString, dbName, context);
        }

        final GedRenderer<?> submissionRenderer = new GedRendererFactory()
                .create(submission, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("submissionString", submission.getString());
        model.addAttribute("model", submissionRenderer);
        model.addAttribute("appInfo", getAppInfo());
        log.debug("Exiting submission");
        return "submission";
    }
}
