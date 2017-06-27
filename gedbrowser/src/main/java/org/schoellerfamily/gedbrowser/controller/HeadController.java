package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
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
public class HeadController extends DatedDataController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

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
        logger.debug("Entering head");

        final RenderingContext context = createRenderingContext();

        final Head head = fetchHead(dbName);

        final String filename = gedbrowserHome + "/" + dbName + ".ged";
        model.addAttribute("filename", filename);
        model.addAttribute("model", headRenderer(context, head));
        model.addAttribute("appInfo", appInfo);

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
