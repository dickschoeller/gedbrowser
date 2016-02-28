package org.schoellerfamily.gedbrowser.controller;

import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.IndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dick Schoeller
 */
@Controller
public class IndexController {
    /** */
    @Autowired
    private transient GedFileLoader loader;

    /** */
    @Autowired
    private transient Users users;

    /**
     * Connects HTML template file with data for the surnames
     * index page. The page displays the surnames that begin
     * with the provided letter.
     *
     * @param letter the letter that we're displaying
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/surnames")
    public final String surnames(
            @RequestParam(value = "letter",
                required = false,
                defaultValue = "?") final String letter,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        Logger.getGlobal().entering("IndexController", "surnames");
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        final User user = users.get(authentication.getName());
        final RenderingContext renderingContext =
                new RenderingContextBuilder(authentication, user).build();

        final String filename = "/var/lib/gedbrowser/" + dbName + ".ged";

        loader.reset();

        final Root root = (Root) loader.load(dbName);
        GedRenderer<?> gedRenderer;
        if (root == null) {
            // TODO introduce a null IndexRenderer?
            gedRenderer =
                    new GedRendererFactory().create(null, renderingContext);
        } else {
            gedRenderer = new IndexRenderer(root, letter, renderingContext);
        }

        model.addAttribute("filename", filename);
        model.addAttribute("index", gedRenderer);
        model.addAttribute("appInfo", new ApplicationInfo());

        Logger.getGlobal().exiting("IndexController", "surnames");
        return "surnames";
    }
}
