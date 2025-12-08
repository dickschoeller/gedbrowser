package org.schoellerfamily.gedbrowser.controller;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourcesRenderer;
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
@Slf4j
public class SourcesController extends DatedDataController {

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Connects HTML template file with data for the sources index page.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/sources")
    public final String sources(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering sources");

        final Root root = fetchRoot(dbName);

        final GedRenderer<?> gedRenderer = new SourcesRenderer(root,
                createRenderingContext());

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("model", gedRenderer);
        model.addAttribute("appInfo", appInfo);

        log.debug("Exiting sources");
        return "sources";
    }
}
