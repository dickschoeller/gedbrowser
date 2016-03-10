package org.schoellerfamily.gedbrowser.controller;

import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.schoellerfamily.gedbrowser.persistence.repository.
//    RootDocumentRepository;

/**
 * @author Dick Schoeller
 */
@Controller
public class PersonController extends AbstractController {
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
        Logger.getGlobal().entering("PersonController", "person");

        final RenderingContext renderingContext = createRenderingContext(users);

        String nameString;

        loader.reset();

        final Root root = (Root) loader.load(dbName);
        GedRenderer<?> gedRenderer;
        if (root == null) {
            nameString = "Data Set Not Found";
            gedRenderer =
                    new GedRendererFactory().create(null, renderingContext);
        } else {
            Person person = (Person) root.find(idString);
            if (person == null) {
                nameString = "Person Not Found";
                person = new Person(root);
                person.setString(idString);
                gedRenderer =
                        new GedRendererFactory().create(
                                person, renderingContext);
            } else {
                final Name name = person.getName();
                if (name == null) {
                    nameString = "Name Not Found";
                } else {
                    final NameRenderer nameRenderer =
                            (NameRenderer) new GedRendererFactory().create(
                                    name, renderingContext);
                    nameString = nameRenderer.getNameHtml();
                }
                gedRenderer =
                        new GedRendererFactory().create(
                                person, renderingContext);
            }
        }

        final String filename = gedbrowserHome + "/" + dbName + ".ged";
        model.addAttribute("filename", filename);
        model.addAttribute("name", nameString);
        model.addAttribute("person", gedRenderer);
        model.addAttribute("appInfo", new ApplicationInfo());

        Logger.getGlobal().exiting("PersonController", "person");
        return "person";
    }

}
