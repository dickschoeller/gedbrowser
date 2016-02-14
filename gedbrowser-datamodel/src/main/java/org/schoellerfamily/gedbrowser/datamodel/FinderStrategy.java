package org.schoellerfamily.gedbrowser.datamodel;

import java.util.Collection;

/**
 * @author Dick Schoeller
 */
public interface FinderStrategy {
    /**
     * @param owner the GedObject in whose context we are searching
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    GedObject find(final GedObject owner, final String str);

    /**
     * @param <T> the return type
     * @param owner the GedObject in whose context we are searching
     * @param str the ID string of the object being sought
     * @param clazz the class of the return type
     * @return the found object or null
     */
    <T extends GedObject> T find(
            final GedObject owner, final String str, Class<T> clazz);

    /**
     * @param owner the GedObject in whose context we are searching
     * @param surname the surname string of the object being sought
     * @return a collection of matching persons.
     */
    Collection<Person> findBySurname(final GedObject owner,
            final String surname);

    /**
     * @param owner the GedObject in whose context we are searching
     * @param beginsWith the beginning substring we are looking for
     * @return a collection of matching surnames.
     */
    Collection<String> findBySurnamesBeginWith(final GedObject owner,
            final String beginsWith);

    /**
     * @param owner the GedObject in whose context we are searching
     * @return the filename associated with this data set.
     */
    String getFilename(final GedObject owner);

    /**
     * @param owner the GedObject in whose context we are searching
     * @return the filename associated with this data set.
     */
    String getDbName(final GedObject owner);

    /**
     * @param owner the GedObject in whose context we are searching
     * @param gob object to insert.
     */
    void insert(final GedObject owner, final GedObject gob);

    /**
     * @param owner the GedObject in whose context we are searching
     * @return a collection of initial letters
     */
    Collection<String> findSurnameInitialLetters(final GedObject owner);
}
