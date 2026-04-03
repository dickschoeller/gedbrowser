package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for login.
 *
 * @author Richard Schoeller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    /** Return string. */
    private static final String LOGIN = "login";

    /** Referer header. */
    private static final String REFERER = "referer";

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
    @SuppressWarnings("java:S3752")
    @RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
    public final String login(final Model model,
            final HttpServletRequest request) {
        log.debug("Entering login");
        final String referer = loginDestinationUrl(request);
        model.addAttribute(REFERER, referer);
        request.getSession().setAttribute(SESSION_REFERER_KEY, referer);
        model.addAttribute("appInfo", appInfo);
        log.debug("Exiting login");
        return LOGIN;
    }

    /**
     * Handle logout request.
     *
     * @param model Spring connection between the data model wrapper.
     * @param request the servlet request
     * @return a string identifying which template to use.
     */
    @SuppressWarnings("java:S3752")
    @RequestMapping(value = "/logout", method = { RequestMethod.POST, RequestMethod.GET })
    public final String logout(final Model model,
            final HttpServletRequest request) {
        log.debug("Entering logout");
        final String referer = loginDestinationUrl(request);
        model.addAttribute(REFERER, referer);
        request.getSession().setAttribute(SESSION_REFERER_KEY, referer);
        model.addAttribute("appInfo", appInfo);
        log.debug("Exiting logout");
        return LOGIN;
    }

    private String loginDestinationUrl(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final String requestReferer = request.getHeader(REFERER);
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

    private boolean useReferer(final String referer) {
        return referer != null && referer.contains(servletPath)
                && !referer.contains(LOGIN);
    }
}
