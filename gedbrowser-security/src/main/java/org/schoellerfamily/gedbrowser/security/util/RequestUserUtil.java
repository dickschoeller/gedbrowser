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
public class RequestUserUtil implements UserFacade {
    private final HttpServletRequest request;
    private final UserService userService;

    public RequestUserUtil(final HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @Override
    public final User getUser() {
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
     * Short cut to check user role
     * @return true if has user
     */
    public final boolean hasUser() {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(UserRoleName.USER);
    }

    /**
     * Short cut to check admin role
     * @return true if has admin
     */
    public final boolean hasAdmin() {
        if (getUser() == null) {
            return false;
        }
        return getUser().hasRole(UserRoleName.ADMIN);
    }
}
