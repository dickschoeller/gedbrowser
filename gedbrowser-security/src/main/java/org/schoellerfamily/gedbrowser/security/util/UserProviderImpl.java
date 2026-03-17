package org.schoellerfamily.gedbrowser.security.util;

import jakarta.servlet.http.HttpServletRequest;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;



/**
 * Provides user values to calling code.
 *
 * @author dick
 */
@Component
@RequiredArgsConstructor
public final class UserProviderImpl implements UserProvider {
    /**
     * The service value.
     */
    private final UserService service;

    /**
     * Returns the user.
     *
     * @param request the request
     * @return the user
     */
    @Override
    public User getUser(final HttpServletRequest request) {
        return new RequestUserUtil(request, service).getUser();
    }
}
