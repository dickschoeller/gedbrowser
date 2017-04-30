package org.schoellerfamily.gedbrowser.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.controller.exception.PersonNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
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
public class PersonController extends GeoDataController {
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

        final Person person = fetchPerson(dbName, idString);

        final RenderingContext context = createRenderingContext();

        final List<PlaceInfo> places = fetchPlaces(person, context);
        final Boolean showMap = !places.isEmpty();

        final String filename = gedbrowserHome + "/" + dbName + ".ged";
        model.addAttribute("filename", filename);
        model.addAttribute("name", nameHtml(context, person));
        model.addAttribute("person", personRenderer(context, person));
        model.addAttribute("places", places);
        model.addAttribute("key", getMapsKey());
        model.addAttribute("showMap", showMap);
        model.addAttribute("appInfo", appInfo);

        logger.debug("Exiting person");
        return "person";
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the person
     * @return the person
     */
    private Person fetchPerson(final String dbName, final String idString) {
        final Root root = fetchRoot(dbName);
        final Person person = (Person) root.find(idString);
        if (person == null) {
            throw new PersonNotFoundException(
                    "Person " + idString + " not found", idString,
                    root.getDbName());
        }
        return person;
    }

    /**
     * @param context the rendering context
     * @param person the person being rendered
     * @return the person renderer
     */
    private GedRenderer<?> personRenderer(final RenderingContext context,
            final Person person) {
        return new GedRendererFactory().create(
                person, context, calendarProvider());
    }

    /**
     * Get the name string in an html fragment format.
     *
     * @param context the rendering context
     * @param person the person being rendered
     * @return the name string
     */
    private String nameHtml(final RenderingContext context,
            final Person person) {
        return nameRenderer(context, person).getNameHtml();
    }

    /**
     * @param context the rendering context
     * @param person the person being rendered
     * @return the name renderer
     */
    private GedRenderer<?> nameRenderer(final RenderingContext context,
            final Person person) {
        return new GedRendererFactory().create(
                person.getName(), context, calendarProvider());
    }
}
