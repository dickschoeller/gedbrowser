package org.schoellerfamily.gedbrowser.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.ErrorRenderer;
import org.schoellerfamily.gedbrowser.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dick Schoeller
 */
@Controller
public class MyErrorController implements ErrorController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo applicationInfo;

    /**
     * @param model Spring connection between the data model wrapper
     * @return error
     */
    @RequestMapping(value = "/error")
    public final String error(final Model model) {
        logger.debug("Entering error");
        final Renderer renderer = new ErrorRenderer(applicationInfo);
        model.addAttribute("error", renderer);
        logger.debug("Exiting error");
        return "error";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
