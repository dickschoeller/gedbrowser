package org.schoellerfamily.gedbrowser.security.util;

import javax.servlet.http.HttpServletRequest;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author dick
 *
 */
@Component
@RequiredArgsConstructor
public final class UserProviderImpl implements UserProvider {
    /** */
    private final UserService service;

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(final HttpServletRequest request) {
        return new RequestUserUtil(request, service).getUser();
    }
}
