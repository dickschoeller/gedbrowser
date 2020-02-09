package org.schoellerfamily.gedbrowser.security.util;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserFacade;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.service.UserService;

/**
 * @author Dick Schoeller
 */
public final class RequestUserUtil implements UserFacade {
    /** Hold the request. */
    private final HttpServletRequest request;

    /** Hold the service. */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param request the request that tells us which user to search for
     * @param userService the suer service to get the user from
     */
    public RequestUserUtil(final HttpServletRequest request, final UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @Override
    public User getUser() {
        final Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            return null;
        }
        final String username = userPrincipal.getName();
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return userService.findByUsername(username);
    }

    /**
     * Short cut to check user role.
     *
     * @return true if has user
     */
    public boolean hasUser() {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(UserRoleName.USER);
    }

    /**
     * Short cut to check admin role.
     *
     * @return true if has admin
     */
    public boolean hasAdmin() {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(UserRoleName.ADMIN);
    }
}
