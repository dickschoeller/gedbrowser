package org.schoellerfamily.gedbrowser.security.model;

import java.io.Serializable;

import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Dick Schoeller
 */
public interface SecurityUser extends User, UserDetails, Serializable {
    /**
     * @param password the new password.
     */
    void setPassword(String password);
}
