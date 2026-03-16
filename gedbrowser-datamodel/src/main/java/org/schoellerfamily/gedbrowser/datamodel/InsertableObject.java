package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Defines the contract for insertable object.
 *
 * @author Richard Schoeller
 */
public interface InsertableObject {
    /**
     * Performs insert.
     *
     * @param gob object to insert
     */
    void insert(FinderObject gob);

    /**
     * Performs extra insert.
     *
     * @param gob object to insert
     */
    default void extraInsert(final FinderObject gob) {
        // The default implementation is empty. Some derived
        // classes implement this.
    }
}
