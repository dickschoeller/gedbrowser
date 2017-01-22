package org.schoellerfamily.gedbrowser.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.controller.exception.PersonNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.keys.KeyManager;
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
public class PersonController extends AbstractController {
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

    /** */
    @Autowired
    private transient GeoServiceClient client;

    /** */
    @Autowired
    private transient KeyManager keyManager;

    /**
     * Connects HTML template file with data for the person page.
     *
     * @param idString id URL argument.
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/person")
    public final String person(
            @RequestParam(value = "id",
                required = false,
                defaultValue = "I0") final String idString,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        logger.debug("Entering person");

        final Root root = (Root) loader.load(dbName);
        if (root == null) {
            throw new DataSetNotFoundException(
                    "Data set " + dbName + " not found", dbName);
        }
        final Person person = (Person) root.find(idString);
        if (person == null) {
            throw new PersonNotFoundException(
                    "Person " + idString + " not found", idString, dbName);
        }

        final RenderingContext renderingContext =
                createRenderingContext(users);

        final PlaceListRenderer pl = new PlaceListRenderer(person, client,
                renderingContext);
        final List<PlaceInfo> places = pl.render();
        for (final PlaceInfo place : places) {
            this.logger.info(place);
        }
        final String key = keyManager.getMapsKey();
        final Boolean showMap = !places.isEmpty();

        final GedRenderer<?> nameRenderer =
                new GedRendererFactory().create(
                        person.getName(), renderingContext);
        final GedRenderer<?> gedRenderer =
                new GedRendererFactory().create(
                        person, renderingContext);

        final String filename = gedbrowserHome + "/" + dbName + ".ged";
        model.addAttribute("filename", filename);
        model.addAttribute("name", nameRenderer.getNameHtml());
        model.addAttribute("person", gedRenderer);
        model.addAttribute("places", places);
        model.addAttribute("key", key);
        model.addAttribute("showMap", showMap);
        model.addAttribute("appInfo", new ApplicationInfo());

        logger.debug("Exiting person");
        return "person";
    }
}
