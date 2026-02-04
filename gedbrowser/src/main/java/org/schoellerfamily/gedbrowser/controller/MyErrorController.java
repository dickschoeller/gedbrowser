package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.renderer.ErrorRenderer;
import org.schoellerfamily.gedbrowser.renderer.Renderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listen for requests for the error page.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class MyErrorController implements ErrorController {
    /** Provides configured information about the application. */
    private final ApplicationInfo appInfo;

    /**
     * Handle display of error conditions.
     *
     * @param model Spring connection between the data model wrapper
     * @return error
     */
    @GetMapping(value = "/error")
    public final String error(final Model model) {
        log.debug("Entering error");
        final Renderer renderer = new ErrorRenderer(appInfo);
        model.addAttribute("error", renderer);
        log.debug("Exiting error");
        return "error";
    }
}
