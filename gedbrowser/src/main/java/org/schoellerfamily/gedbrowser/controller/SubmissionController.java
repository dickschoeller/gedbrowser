package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.controller.exception.SubmissionNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dick Schoeller
 */
@Controller
public class SubmissionController extends DatedDataController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Connects HTML template file with data for the submission page.
     *
     * @param idString id URL argument.
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/submission")
    public final String submission(
            @RequestParam(value = "id",
                required = false,
                defaultValue = "SUBN1") final String idString,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        logger.debug("Entering source");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();
        final Submission submission = (Submission) root.find(idString);
        if (submission == null) {
            throw new SubmissionNotFoundException(
                    "Submission " + idString + " not found", idString, dbName,
                    context);
        }

        final GedRenderer<?> submissionRenderer = new GedRendererFactory()
                .create(submission, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("submissionString", submission.getString());
        model.addAttribute("model", submissionRenderer);
        model.addAttribute("appInfo", appInfo);
        logger.debug("Exiting submission");
        return "submission";
    }
}
