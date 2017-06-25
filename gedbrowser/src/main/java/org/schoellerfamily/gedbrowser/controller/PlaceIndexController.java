package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.IndexByPlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
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
public class PlaceIndexController extends DatedDataController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;
    /** */
    @Autowired
    private transient GeoServiceClient client;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Connects HTML template file with data for the surnames index page. The
     * page displays the surnames that begin with the provided letter.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/places")
    public final String places(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        logger.debug("Entering surnames");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();

        final IndexByPlaceRenderer gedRenderer = new IndexByPlaceRenderer(root,
                client, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("model", gedRenderer);
        model.addAttribute("appInfo", appInfo);

        return "places";
    }
}
