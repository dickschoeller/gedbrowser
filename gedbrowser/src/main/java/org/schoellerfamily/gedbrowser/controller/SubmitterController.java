package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.controller.exception.SubmitterNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class SubmitterController extends GeoDataController {

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Connects HTML template file with data for the source page.
     *
     * @param idString id URL argument.
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/submitter")
    public final String source(
            @RequestParam(value = "id",
                required = false,
                defaultValue = "SUBM1") final String idString,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering submitter");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();
        final Submitter submitter = (Submitter) root.find(idString);
        if (submitter == null) {
            throw new SubmitterNotFoundException("Submitter %s not found".formatted(idString), idString, dbName, context);
        }

        final GedRenderer<?> submitterRenderer = new GedRendererFactory()
                .create(submitter, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("sourceString", submitter.getString());
        model.addAttribute("model", submitterRenderer);
        model.addAttribute("appInfo", appInfo);
        log.debug("Exiting submitter");
        return "submitter";
    }
}
