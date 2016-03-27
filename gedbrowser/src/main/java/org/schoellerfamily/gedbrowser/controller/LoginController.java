package org.schoellerfamily.gedbrowser.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    /** Key to find the login referer in the session attributes. */
    private static final String SESSION_REFERER_KEY = "SESSION_REFERER";

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
        final String referer = loginDestinationUrl(request);
        model.addAttribute("referer", referer);
        request.getSession().setAttribute(SESSION_REFERER_KEY, referer);
        model.addAttribute("appInfo", new ApplicationInfo());
        logger.debug("Exiting login");
        return "login";
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
        final String referer = loginDestinationUrl(request);
        model.addAttribute("referer", referer);
        request.getSession().setAttribute(SESSION_REFERER_KEY, referer);
        model.addAttribute("appInfo", new ApplicationInfo());
        logger.debug("Exiting logout");
        return "login";
    }

    /**
     * Try to figure out where to go after login. We have to do a
     * few tricks in order to carry that around.
     *
     * @param request the request object
     * @return the URL
     */
    private String loginDestinationUrl(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final String requestReferer = request.getHeader("referer");
        final String sessionReferer = (String) session
                .getAttribute(SESSION_REFERER_KEY);
        if (useReferer(requestReferer)) {
            return requestReferer;
        } else if (useReferer(sessionReferer)) {
            return sessionReferer;
        } else {
            return servletPath + "/person?db=schoeller&id=I1";
        }
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
