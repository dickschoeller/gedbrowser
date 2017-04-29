package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public interface InsertableObject {
    /**
     * @param gob object to insert
     */
    void insert(FinderObject gob);

    /**
     * @param gob object to insert
     */
    default void extraInsert(final FinderObject gob) {
        // The default implementation is empty. Some derived
        // classes implement this.
    }
}
