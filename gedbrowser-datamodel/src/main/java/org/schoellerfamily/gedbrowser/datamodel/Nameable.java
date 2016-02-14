package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
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
