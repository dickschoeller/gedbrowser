package org.schoellerfamily.gedbrowser.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dick Schoeller
 */
@Controller
public class LoginController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** Base path in URL. */
    @Value("${server.servlet-path}")
    private transient String servletPath;

    /**
     * @param model Spring connection between the data model wrapper.
     * @param request the servlet request
     * @return a string identifying which template to use.
     */
    @RequestMapping("/login")
    public final String login(final Model model,
            final HttpServletRequest request) {
        logger.debug("Entering login");
        final String referer = request.getHeader("referer");
        if (useReferer(referer)) {
            model.addAttribute("referer", referer);
        } else {
            // TODO haven't figured out how to retain referer after login error.
            logRequest(request);
            model.addAttribute("referer",
                    servletPath + "/person?db=schoeller&id=I1");
        }
        model.addAttribute("appInfo", new ApplicationInfo());
        logger.debug("Exiting login");
        return "login";
    }

    /**
     * @param request the request to log
     */
    private void logRequest(final HttpServletRequest request) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        logger.debug("headers");
        final Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            final String name = headers.nextElement();
            logger.debug("    " + name + " = " + request.getHeader(name));
        }
        logger.debug("parameters");
        for (final String key : request.getParameterMap().keySet()) {
            logger.debug("    " + key + " = " + request.getParameter(key));
        }
    }

    /**
     * @param model Spring connection between the data model wrapper.
     * @param request the servlet request
     * @return a string identifying which template to use.
     */
    @RequestMapping("/logout")
    public final String logout(final Model model,
            final HttpServletRequest request) {
        logger.debug("Entering logout");
        final String referer = request.getHeader("referer");
        if (useReferer(referer)) {
            model.addAttribute("referer", referer);
        } else {
            logRequest(request);
            model.addAttribute("referer",
                    servletPath + "/person?db=schoeller&id=I1");
        }
        model.addAttribute("appInfo", new ApplicationInfo());
        logger.debug("Exiting logout");
        return "login";
    }

    /**
     * @param referer the referer string
     * @return true if it is one we would want to go to
     */
    private boolean useReferer(final String referer) {
        return referer != null && referer.contains(servletPath)
                && !referer.contains("login");
    }
}
