package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public interface ParentableObject {
    /**
     * @return the parent object
     */
    GedObject getParent();

    /**
     * @return the filename provided by the parent
     */
    default String getParentFilename() {
        if (getParent() == null) {
            return null;
        }
        return getParent().getFilename();
    }

    /**
     * @return the filename provided by the parent
     */
    default String getParentDbName() {
        if (getParent() == null) {
            return null;
        }
        return getParent().getDbName();
    }
}
