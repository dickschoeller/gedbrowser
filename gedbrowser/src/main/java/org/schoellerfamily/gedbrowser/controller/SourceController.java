package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.controller.exception.SourceNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
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
public class SourceController extends AbstractController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedFileLoader loader;

    /** */
    @Autowired
    private transient Users users;


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
    @RequestMapping("/source")
    public final String source(
            @RequestParam(value = "id",
                required = false,
                defaultValue = "S0") final String idString,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        logger.debug("Entering source");

        final Root root = (Root) loader.load(dbName);
        if (root == null) {
            throw new DataSetNotFoundException(
                    "Data set " + dbName + " not found");
        }

        final Source source = (Source) root.find(idString);
        if (source == null) {
            throw new SourceNotFoundException(
                    "Source " + idString + " not found");
        }

        final GedRenderer<?> gedRenderer = new GedRendererFactory()
                .create(source, createRenderingContext(users));

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("sourceString", source.getString());
        model.addAttribute("source", gedRenderer);
        model.addAttribute("appInfo", new ApplicationInfo());
        logger.debug("Exiting source");
        return "source";
    }
}
