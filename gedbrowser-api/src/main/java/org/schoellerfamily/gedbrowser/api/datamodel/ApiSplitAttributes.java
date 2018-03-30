package org.schoellerfamily.gedbrowser.api.datamodel;

/**
 * @author Dick Schoeller
 */
public interface ApiSplitAttributes {

    /**
     * Special handling of adding attributes to any object that maintains
     * multiple lists of attributes.
     *
     * @param attribute the attribute to add
     */
    void addAttribute(ApiAttribute attribute);
}
