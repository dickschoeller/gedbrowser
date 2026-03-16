package org.schoellerfamily.gedbrowser.datamodel;

/**
 * Defines the contract for nameable.
 *
 * @author Richard Schoeller
 */
public interface Nameable {
    /**
     * @return the name object.
     */
    Name getName();

    /**
     * @return the surname string.
     */
    String getSurname();

    /**
     * @return a name string usable in an index display.
     */
    String getIndexName();
}
