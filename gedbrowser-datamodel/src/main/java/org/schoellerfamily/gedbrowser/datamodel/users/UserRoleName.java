package org.schoellerfamily.gedbrowser.datamodel.users;

/**
 * Roles. Any access without roles is read-only and limited data
 * visibility.
 *
 * @author Dick Schoeller
 */
public enum UserRoleName {
    /**
     * The role associated with logged in user capabilities.
     */
    USER,
    /**
     * The role associated with administrator capabilities.
     */
    ADMIN
}
