package org.schoellerfamily.gedbrowser.security.service;

import java.util.List;

import org.schoellerfamily.gedbrowser.security.model.Authority;

/**
 * @author Dick Schoelelr
 */
public interface AuthorityService {
    /**
     * Find a list of authority objects by ID.
     *
     * @param id the ID
     * @return the list
     */
    List<Authority> findById(Long id);

    /**
     * Find a list of authority objects by name.
     *
     * @param name the name
     * @return the list
     */
    List<Authority> findByname(String name);
}
