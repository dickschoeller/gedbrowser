package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.renderer.ErrorRenderer;
import org.schoellerfamily.gedbrowser.renderer.Renderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for my error.
 *
 * @author Richard Schoeller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class MyErrorController implements ErrorController {
    /** Provides configured information about the application. */
    private final ApplicationInfo appInfo;

    /**
     * Handle display of error conditions.
     * This endpoint must handle errors from all HTTP methods (GET, POST, PUT, DELETE, etc.),
     * not just GET requests, as it serves as the application's error handler.
     *
     * @param model Spring connection between the data model wrapper
     * @return error
     */
    @SuppressWarnings("java:S3752")
    @RequestMapping(value = "/error")
    public final String error(final Model model) {
        log.debug("Entering error");
        final Renderer renderer = new ErrorRenderer(appInfo);
        model.addAttribute("error", renderer);
        log.debug("Exiting error");
        return "error";
    }
}
