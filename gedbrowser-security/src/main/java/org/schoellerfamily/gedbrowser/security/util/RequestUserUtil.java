package org.schoellerfamily.gedbrowser.security.util;

import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserFacade;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.security.service.UserService;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
public final class RequestUserUtil implements UserFacade {
    /** Hold the request. */
    private final Principal principal;

    /** Hold the service. */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param request the request that tells us which user to search for
     * @param userService the suer service to get the user from
     */
    public RequestUserUtil(final HttpServletRequest request, final UserService userService) {
        this(request.getUserPrincipal(), userService);
    }

    @Override
    public User getUser() {
        if (principal == null) {
            return null;
        }
        final String username = principal.getName();
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
        final User user = getUser();
        return user != null && user.hasRole(UserRoleName.USER);
    }

    /**
     * Short cut to check admin role.
     *
     * @return true if has admin
     */
    public boolean hasAdmin() {
        final User user = getUser();
        return user != null && user.hasRole(UserRoleName.ADMIN);
    }
}
