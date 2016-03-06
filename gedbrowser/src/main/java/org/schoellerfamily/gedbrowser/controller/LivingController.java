package org.schoellerfamily.gedbrowser.controller;

import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.LivingRenderer;
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
public class LivingController {
    /** */
    @Autowired
    private transient GedFileLoader loader;

    /** */
    @Autowired
    private transient Users users;

    /**
     * Connects the HTML template file with the data for displaying
     * the page of people who are estimated to still be living.
     *
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/living")
    public final String living(@RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        Logger.getGlobal().entering("LivingController", "living");
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
            // TODO introduce a null LivingRenderer?
            gedRenderer =
                    new GedRendererFactory().create(null, renderingContext);
        } else {
            gedRenderer = new LivingRenderer(root, renderingContext);
        }

        model.addAttribute("filename", filename);
        model.addAttribute("living", gedRenderer);
        model.addAttribute("appInfo", new ApplicationInfo());

        Logger.getGlobal().exiting("LivingController", "living");
        return "living";
    }
}
