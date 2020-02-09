/**
 * 
 */
package org.schoellerfamily.gedbrowser.security.util;

import javax.servlet.http.HttpServletRequest;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.security.service.UserService;

/**
 * @author dick
 *
 */
public class UserProviderImpl implements UserProvider {
    /** */
    private final UserService service;

    /**
     * Constructor.
     *
     * @param service will use this service to look up users.
     */
    public UserProviderImpl (final UserService service) {
        this.service = service;
    }

    @Override
    public User getUser(HttpServletRequest request) {
        return new RequestUserUtil(request, service).getUser();
    }

}
