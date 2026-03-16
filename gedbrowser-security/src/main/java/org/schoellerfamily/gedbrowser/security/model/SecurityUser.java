package org.schoellerfamily.gedbrowser.security.model;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Defines the contract for security user.
 *
 * @author Richard Schoeller
 */
public interface SecurityUser extends User, UserDetails {
    /**
     * @param password the new password.
     */
    void setPassword(String password);
}
