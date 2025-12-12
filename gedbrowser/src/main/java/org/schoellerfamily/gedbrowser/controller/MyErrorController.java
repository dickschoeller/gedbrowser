package org.schoellerfamily.gedbrowser.controller;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.renderer.ErrorRenderer;
import org.schoellerfamily.gedbrowser.renderer.Renderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class MyErrorController implements ErrorController {

    /** */
    @Autowired
    private transient ApplicationInfo applicationInfo;

    /**
     * @param model Spring connection between the data model wrapper
     * @return error
     */
    @RequestMapping(value = "/error")
    public final String error(final Model model) {
        log.debug("Entering error");
        final Renderer renderer = new ErrorRenderer(applicationInfo);
        model.addAttribute("error", renderer);
        log.debug("Exiting error");
        return "error";
    }
}
