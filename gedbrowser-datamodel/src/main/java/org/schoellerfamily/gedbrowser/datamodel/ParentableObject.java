package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Defines the contract for parentable object.
 *
 * @author Richard Schoeller
 */
public interface ParentableObject {
    /**
     * Gets the parent.
     *
     * @return the parent object
     */
    GedObject getParent();

    /**
     * Gets the parent filename.
     *
     * @return the filename provided by the parent
     */
    default String getParentFilename() {
        if (getParent() == null) {
            return null;
        }
        return getParent().getFilename();
    }

    /**
     * Gets the parent db name.
     *
     * @return the filename provided by the parent
     */
    default String getParentDbName() {
        if (getParent() == null) {
            return null;
        }
        return getParent().getDbName();
    }
}
