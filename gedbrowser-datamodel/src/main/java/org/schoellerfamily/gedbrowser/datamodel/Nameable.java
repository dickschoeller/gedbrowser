package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Defines the contract for nameable.
 *
 * @author Richard Schoeller
 */
public interface Nameable {
    /**
     * Gets the name.
     *
     * @return the name object.
     */
    Name getName();

    /**
     * Gets the surname.
     *
     * @return the surname string.
     */
    String getSurname();

    /**
     * Gets the index name.
     *
     * @return a name string usable in an index display.
     */
    String getIndexName();
}
