package org.schoellerfamily.gedbrowser.security.model;

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
    ROLE_USER,
    /**
     * The role associated with administrator capabilities.
     */
    ROLE_ADMIN
}
