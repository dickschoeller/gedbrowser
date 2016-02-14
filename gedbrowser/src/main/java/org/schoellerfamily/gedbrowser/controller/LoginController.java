package org.schoellerfamily.gedbrowser.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dick Schoeller
 */
@Controller
public class LoginController {
    /**
     * @return a string identifying which template to use.
     */
    @RequestMapping("/login")
    public final String login() {
        Logger.getGlobal().info("Got to LoginController.login");
        return "login";
    }

    /**
     * @return a string identifying which template to use.
     */
    @RequestMapping("/logout")
    public final String logout() {
        Logger.getGlobal().info("Got to LoginController.logout");
        return "login";
    }
}
