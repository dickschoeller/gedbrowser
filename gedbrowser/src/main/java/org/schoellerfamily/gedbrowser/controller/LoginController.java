package org.schoellerfamily.gedbrowser.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dick Schoeller
 */
@Controller
public class LoginController {
    /**
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which template to use.
     */
    @RequestMapping("/login")
    public final String login(final Model model) {
        Logger.getGlobal().info("Got to LoginController.login");
        model.addAttribute("appInfo", new ApplicationInfo());
        return "login";
    }

    /**
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which template to use.
     */
    @RequestMapping("/logout")
    public final String logout(final Model model) {
        Logger.getGlobal().info("Got to LoginController.logout");
        model.addAttribute("appInfo", new ApplicationInfo());
        return "login";
    }
}
