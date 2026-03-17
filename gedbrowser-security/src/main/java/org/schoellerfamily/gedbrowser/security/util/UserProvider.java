package org.schoellerfamily.gedbrowser.security.util;

import jakarta.servlet.http.HttpServletRequest;

import org.schoellerfamily.gedbrowser.datamodel.users.User;

/**
 * Provides user values to calling code.
 *
 * @author Richard Schoeller
 */
public interface UserProvider {
    /**
     * Get a user object by looking up from information in the servlet request.
     *
     * @param request the servlet request
     * @return the user
     */
    User getUser(HttpServletRequest request);
}
