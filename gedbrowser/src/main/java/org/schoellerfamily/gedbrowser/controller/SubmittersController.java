package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittersRenderer;
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
public class SubmittersController extends DatedDataController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Connects HTML template file with data for the submitters index page.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/submitters")
    public final String submitters(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        logger.debug("Entering submitters");

        final Root root = fetchRoot(dbName);

        final GedRenderer<?> gedRenderer = new SubmittersRenderer(root,
                createRenderingContext());

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("model", gedRenderer);
        model.addAttribute("appInfo", appInfo);

        logger.debug("Exiting submitters");
        return "submitters";
    }
}
