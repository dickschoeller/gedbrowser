package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listen for requests for the login page.
 *
 * @author Dick Schoeller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    /** Key to find the login referer in the session attributes. */
    private static final String SESSION_REFERER_KEY = "SESSION_REFERER";

    /** Provides information about the application for display. */
    private final ApplicationInfo appInfo;

    /** Base path in URL. */
    @Value("${server.servlet.context-path:/gedbrowser}")
    private final String servletPath;

    /**
     * Handle login request.
     *
     * @param model Spring connection between the data model wrapper.
     * @param request the servlet request
     * @return a string identifying which template to use.
     */
    @RequestMapping("/login")
    public final String login(final Model model,
            final HttpServletRequest request) {
        log.debug("Entering login");
        final String referer = loginDestinationUrl(request);
        model.addAttribute("referer", referer);
        request.getSession().setAttribute(SESSION_REFERER_KEY, referer);
        model.addAttribute("appInfo", appInfo);
        log.debug("Exiting login");
        return "login";
    }

    /**
     * Handle logout request.
     *
     * @param model Spring connection between the data model wrapper.
     * @param request the servlet request
     * @return a string identifying which template to use.
     */
    @RequestMapping("/logout")
    public final String logout(final Model model,
            final HttpServletRequest request) {
        log.debug("Entering logout");
        final String referer = loginDestinationUrl(request);
        model.addAttribute("referer", referer);
        request.getSession().setAttribute(SESSION_REFERER_KEY, referer);
        model.addAttribute("appInfo", appInfo);
        log.debug("Exiting logout");
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
     * Decide if we want to use this referer.
     *
     * @param referer the referer string
     * @return true if it is one we would want to go to
     */
    private boolean useReferer(final String referer) {
        return referer != null && referer.contains(servletPath)
                && !referer.contains("login");
    }
}
