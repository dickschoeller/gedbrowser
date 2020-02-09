package org.schoellerfamily.gedbrowser.security.util;

import javax.servlet.http.HttpServletRequest;

import org.schoellerfamily.gedbrowser.datamodel.users.User;

/**
 * @author Dick Schoeller
 */
public interface UserProvider {
    User getUser(final HttpServletRequest request);
}
