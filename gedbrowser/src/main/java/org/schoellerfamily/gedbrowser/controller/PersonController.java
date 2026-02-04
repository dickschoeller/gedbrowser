package org.schoellerfamily.gedbrowser.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.controller.exception.PersonNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * Listen for requests for the person page.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class PersonController extends GeoDataController {
    /** */
    private final String gedbrowserHome;

    /**
     * Constructor.
     *
     * @param appInfo           the application info
     * @param users             info about the known application users
     * @param loader            enable loading gedcom files
     * @param provider          enable calendar processing
     * @param repositoryManager enable data storage
     * @param client            enable interaction with geoservice
     * @param keyManager        enable interacting with google
     * @param gedbrowserHome    location of data files for initialization
     */
    @SuppressWarnings("checkstyle:parameternumber")
    public PersonController(final ApplicationInfo appInfo, final Users<? extends User> users,
        final GedObjectFileLoader loader, final CalendarProvider provider,
        final RepositoryManagerMongo repositoryManager, final GeoServiceClient client,
        final KeyManager keyManager,
        @Value("${gedbrowser.home:/var/lib/gedbrowser}") final String gedbrowserHome) {
        super(appInfo, users, loader, provider, repositoryManager, client, keyManager);
        this.gedbrowserHome = gedbrowserHome;
    }

    /**
     * Connects HTML template file with data for the person page.
     *
     * @param idString id URL argument.
     * @param dbName   name of database for the lookup.
     * @param model    Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @GetMapping("/person")
    public final String person(
        @RequestParam(value = "id", required = false, defaultValue = "I0")
        final String idString,
        @RequestParam(value = "db", required = false, defaultValue = "schoeller")
        final String dbName,
        final Model model) {
        log.debug("Entering person");

        final RenderingContext context = createRenderingContext();

        final Person person = fetchPerson(dbName, idString, context);

        final List<PlaceInfo> places = fetchPlaces(person, context);
        final Boolean showMap = !places.isEmpty();

        final String filename = gedbrowserHome + "/" + dbName + ".ged";
        model.addAttribute("filename", filename);
        model.addAttribute("name", nameHtml(context, person));
        model.addAttribute("model", personRenderer(context, person));
        model.addAttribute("places", places);
        try {
            model.addAttribute("key", getMapsKey());
        } catch (final Exception e) {
            log.error("Couldn't get maps key: {}", e.getMessage());
            model.addAttribute("key", "");
        }
        model.addAttribute("showMap", showMap);
        model.addAttribute("appInfo", getAppInfo());

        log.debug("Exiting person");
        return "person";
    }

    /**
     * @param dbName   the name of the database
     * @param idString the ID of the person
     * @param context  the rendering context
     * @return the person
     */
    private Person fetchPerson(final String dbName, final String idString,
        final RenderingContext context) {
        final Root root = fetchRoot(dbName);
        final Person person = (Person) root.find(idString);
        if (person == null) {
            throw new PersonNotFoundException("Person %s not found".formatted(idString), idString,
                root.getDbName(), context);
        }
        return person;
    }

    /**
     * @param context the rendering context
     * @param person  the person being rendered
     * @return the person renderer
     */
    private GedRenderer<?> personRenderer(final RenderingContext context, final Person person) {
        return new GedRendererFactory().create(person, context);
    }

    /**
     * Get the name string in an html fragment format.
     *
     * @param context the rendering context
     * @param person  the person being rendered
     * @return the name string
     */
    private String nameHtml(final RenderingContext context, final Person person) {
        return nameRenderer(context, person).getNameHtml();
    }

    /**
     * @param context the rendering context
     * @param person  the person being rendered
     * @return the name renderer
     */
    private GedRenderer<?> nameRenderer(final RenderingContext context, final Person person) {
        return new GedRendererFactory().create(person.getName(), context);
    }
}
